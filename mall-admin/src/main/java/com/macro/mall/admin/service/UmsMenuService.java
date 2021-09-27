package com.macro.mall.admin.service;

import com.macro.mall.admin.dto.UmsMenuNode;
import com.macro.mall.model.UmsMenu;

import java.util.List;

/**
 * 后台菜单管理Service
 * @version 1.0
 * @Author lj
 * @date 2021/9/26 5:39 下午
 */
public interface UmsMenuService {
    /**
     * 创建后台菜单
     */
    int create(UmsMenu menu);

    /**
     * 修改后台菜单
     */
    int update(Long id, UmsMenu menu);

    /**
     * 根据ID回去菜单详情
     */
    UmsMenu getItem(Long id);

    /**
     * 根据ID删除菜单
     */
    int delete(Long id);

    /**
     * 分页查询后台菜单
     */
    List<UmsMenu> list(Long parentId, Integer pageSize, Integer pageNum);

    /**
     * 树形结构返回所有菜单列表
     */
    List<UmsMenuNode> treeList();

    /**
     * 修改菜单显示状态
     */
    int updateHidden(Long id, Integer hidden);



}
