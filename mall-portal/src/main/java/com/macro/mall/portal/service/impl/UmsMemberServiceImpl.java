package com.macro.mall.portal.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.macro.mall.portal.domain.MemberDetails;
import com.macro.mall.portal.service.UmsMemberCacheService;
import com.macro.mall.portal.service.UmsMemberService;
import com.macro.mall.common.exception.Asserts;
import com.macro.mall.mapper.UmsMemberLevelMapper;
import com.macro.mall.mapper.UmsMemberMapper;
import com.macro.mall.model.UmsMember;
import com.macro.mall.model.UmsMemberExample;
import com.macro.mall.model.UmsMemberLevel;
import com.macro.mall.model.UmsMemberLevelExample;
import com.macro.mall.security.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/9/27 4:04 下午
 * @desc
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {

    private static Logger log = LoggerFactory.getLogger(UmsMemberServiceImpl.class);

    @Autowired private UmsMemberMapper memberMapper;
    @Autowired private JwtTokenUtil tokenUtil;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UmsMemberLevelMapper levelMapper;
    @Autowired private UmsMemberCacheService memberCacheService;

    @Value("${redis.key.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    @Value("${redis.expire.authCode}")
    private String AUTH_CODE_EXPIRE_SECONDS;

    @Override
    public String generateAuthCode(String mobile) {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        String authCode = code.toString();
        memberCacheService.setAuthCode(mobile, authCode);
        return authCode;
    }

    @Override
    public void register(String username, String password, String mobile, String authCode) {
        if (verifyAuthCode(authCode, mobile) == false) {
            Asserts.fail("验证码错误");
        }
        if (memberExist(username, mobile).size() > 0) {
            Asserts.fail("用户已存在");
        }

        UmsMember member = new UmsMember();
        member.setCreateTime(new Date());
        member.setUsername(username);
        member.setPassword(passwordEncoder.encode(password));
        member.setPhone(mobile);
        member.setStatus(1);
        member.setMemberLevelId(defaultMemberLevelId());
        memberMapper.insert(member);
    }

    @Override
    public UmsMember getMemberByUsername(String username) {
        UmsMember member = memberCacheService.getMember(username);
        if (member != null) {
            return member;
        }
        UmsMemberExample example = new UmsMemberExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsMember> members = memberMapper.selectByExample(example);
        if (members.size() > 0) {
            return members.get(0);
        }
        return null;
    }

    @Override
    public UmsMember getMemberById(Long id) {
        return memberMapper.selectByPrimaryKey(id);
    }

    @Override
    public UmsMember getCurrentMember() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        MemberDetails member = (MemberDetails) auth.getPrincipal();
        return member.getMember();
    }

    @Override
    public UserDetails getUserByUsername(String username) {
        UmsMember member = getMemberByUsername(username);
        if (member != null) {
            return new MemberDetails(member);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails user = getUserByUsername(username);
            if (passwordEncoder.matches(password, user.getPassword()) == false) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            token = tokenUtil.generateToken(user);
        }catch (AuthenticationException e) {
            log.info("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public String refreshToken(String token) {
        return tokenUtil.refreshToken(token);
    }

    @Override
    public void updatePassword(String mobile, String password, String authCode) {
        if (verifyAuthCode(authCode, mobile) == false) {
            Asserts.fail("验证码不正确");
        }
        List<UmsMember> members = memberExist(null, mobile);
        if (CollUtil.isEmpty(members)) {
            Asserts.fail("用户不存在");
        }
        UmsMember member = members.get(0);
        member.setPassword(passwordEncoder.encode(password));
        memberMapper.updateByPrimaryKeySelective(member);
        memberCacheService.deleteMember(member.getId());
    }

    @Override
    public void updateIntegral(Long id, Integer integral) {
        UmsMember member = new UmsMember();
        member.setId(id);
        member.setIntegration(integral);
        memberMapper.updateByPrimaryKeySelective(member);
        memberCacheService.deleteMember(id);
    }

    /**
     * 验证码校验
     */
    private boolean verifyAuthCode(String code, String mobile) {
        if (StringUtils.isEmpty(code)) {
            return false;
        }
        String original = memberCacheService.getAuthCode(mobile);
        return original.equals(code);
    }
    /**
     * 查询会员是否已存在
     */
    private List<UmsMember> memberExist(String username, String mobile) {
        UmsMemberExample example = new UmsMemberExample();
        if (StringUtils.isEmpty(username) == false) {
            example.createCriteria().andUsernameEqualTo(username);
        }
        if (StringUtils.isEmpty(mobile) == false) {
            example.or(example.createCriteria().andPhoneEqualTo(mobile));
        }
        List<UmsMember> exists = memberMapper.selectByExample(example);
        return exists;
    }
    /**
     * 获取默认会员等级id
     */
    private Long defaultMemberLevelId() {
        UmsMemberLevelExample example = new UmsMemberLevelExample();
        example.createCriteria().andDefaultStatusEqualTo(1);
        List<UmsMemberLevel> defaults = levelMapper.selectByExample(example);
        return defaults.get(0).getId();
    }
}
