package com.macro.mall.portal.service;

import com.macro.mall.model.UmsMember;

/**
 * 会员信息缓存业务
 * @version 1.0
 * @Author lj
 * @date 2021/9/27 4:09 下午
 */
public interface UmsMemberCacheService {
    /**
     * 添加用户缓存
     */
    void setMember(UmsMember member);

    /**
     * 删除会员用户缓存
     */
    void deleteMember(Long memberId);

    /**
     * 获取用户缓存
     */
    UmsMember getMember(String usernmae);
    /**
     * 获取验证码
     */
    String getAuthCode(String mobile);

    /**
     * 设置验证码
     */
    void setAuthCode(String mmobile, String authCode);


}
