package com.macro.mall.portal.service;

import com.macro.mall.model.UmsMember;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

/**
 * 会员管理service
 * @version 1.0
 * @Author lj
 * @date 2021/9/27 3:39 下午
 */
public interface UmsMemberService {
    /**
     * 生成验证码
     */
    String generateAuthCode(String mobile);
    /**
     * 用户注册
     */
    @Transactional
    void register(String username, String password, String mobile, String authCode);

    /**
     * 根据用户名获取会员
     */
    UmsMember getMemberByUsername(String username);
    /**
     * 根据会员编号获取会员
     */
    UmsMember getMemberById(Long id);
    /**
     * 获取当前登录会员
     */
    UmsMember getCurrentMember();
    /**
     * 获取用户信息
     */
    UserDetails getUserByUsername(String username);
    /**
     * 登录后获取token
     */
    String login(String username, String password);
    /**
     * 刷新token
     */
    String refreshToken(String token);

    /**
     * 修改密码
     */
    @Transactional
    void updatePassword(String mobile, String password, String authCode);
    /**
     * 根据会员id修改会员积分
     */
    void updateIntegral(Long id, Integer integral);
}
