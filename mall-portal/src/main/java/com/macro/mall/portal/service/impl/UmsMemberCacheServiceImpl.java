package com.macro.mall.portal.service.impl;

import com.macro.mall.portal.service.UmsMemberCacheService;
import com.macro.mall.common.service.RedisService;
import com.macro.mall.mapper.UmsMemberMapper;
import com.macro.mall.model.UmsMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 会员缓存信息业务实现
 * @version 1.0
 * @Author lj
 * @date 2021/9/27 4:13 下午
 * @desc
 */
@Service
public class UmsMemberCacheServiceImpl implements UmsMemberCacheService {

    @Autowired private RedisService redisService;
    @Autowired private UmsMemberMapper memberMapper;

    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.expire.authCode}")
    private Long REDIS_EXPIRE_AUTH_CODE;
    @Value("${redis.key.member}")
    private String REDIS_KEY_MEMBER;
    @Value("${redis.key.authCode}")
    private String REDIS_KEY_AUTH_CODE;


    @Override
    public void setMember(UmsMember member) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + member.getUsername();
        redisService.set(key, member, REDIS_EXPIRE);
    }

    @Override
    public void setAuthCode(String mobile, String authCode) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_AUTH_CODE + ":" + mobile;
        redisService.set(key, authCode, REDIS_EXPIRE_AUTH_CODE);
    }

    @Override
    public void deleteMember(Long memberId) {
        UmsMember member = memberMapper.selectByPrimaryKey(memberId);
        if (member != null) {
            String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + member.getUsername();
            redisService.del(key);
        }
    }

    @Override
    public UmsMember getMember(String username) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + username;
        return (UmsMember) redisService.get(key);
    }

    @Override
    public String getAuthCode(String mobile) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_AUTH_CODE + ":" + mobile;
        return (String) redisService.get(key);
    }
}
