package com.macro.mall.admin.service;

import com.macro.mall.admin.dto.UmsAdminParam;
import com.macro.mall.admin.dto.UmsUpdateAdminPasswordParam;
import com.macro.mall.model.UmsAdmin;
import com.macro.mall.model.UmsResource;
import com.macro.mall.model.UmsRole;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** 后台用户管理Service
 * @version 1.0
 * @Author lj
 * @date 2021/9/18 10:59 上午
 */
public interface UmsAdminService {
    /**
     * 根据用户名获取后台管理员
     */
    UmsAdmin getAdminByUserName(String username);

    /**
     * 注册功能
     */
    UmsAdmin register(UmsAdminParam umsAdminParam);

    /**
     * 登录
     * @param username
     * @param password
     * @return 返回JWT
     */
    String login(String username, String password);

    /**
     * 刷新token
     */
    String refreshToken(String old);

    /**
     * 根据用户id获取用户
     */
    UmsAdmin getItem(Long id);

    /**
     * 根据用户名或昵称分页查询用户
     */
    List<UmsAdmin> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 修改指定用户信息
     */
    int update(Long id, UmsAdmin admin);

    /**
     * 删除指定用户信息
     */
    int delete(Long id);

    /**
     * 修改用户角色关系
     */
    @Transactional
    int updateRole(Long adminId, List<Long> roleIds);

    /**
     * 获取对应用户角色
     */
    List<UmsRole> getRoleList(Long adminId);

    /**
     * 获取哦指定用户的可访问资源
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * 修改密码
     */
    int updatePassword(UmsUpdateAdminPasswordParam passwordParam);

    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);
}
