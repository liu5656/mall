package com.macro.mall.admin.service;

import com.macro.mall.model.UmsMenu;
import com.macro.mall.model.UmsResource;
import com.macro.mall.model.UmsRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 后台角色管理
 * @version 1.0
 * @Author lj
 * @date 2021/9/22 2:05 下午
 */
public interface UmsRoleService {
    /**
     * 添加角色
     */
    int create(UmsRole role);

    /**
     * 删除角色
     */
    int delete(List<Long> ids);

    /**
     * 获取角色
     */
    List<UmsRole> list();
    List<UmsRole> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 修改角色信息
     */
    int update(Long id, UmsRole role);

    /**
     * 根据管理员ID获取对应菜单
     */
    List<UmsMenu> getMenuList(Long adminId);
    /**
     * 获取角色对应菜单
     */
    List<UmsMenu> listMenu(Long roleId);

    /**
     * 获取角色相关资源
     */
    List<UmsResource> listResource(Long roleId);

    /**
     * 给角色分配菜单
     */
    @Transactional
    int allocMenu(Long roleId, List<Long> menuIds);
    /**
     * 给角色分配资源
     */
    @Transactional
    int allocResource(Long roleId, List<Long> resourceIds);

}
