package com.macro.mall.admin.service;

import com.macro.mall.model.UmsResourceCategory;

import java.util.List;

/**
 * 后台资源分类管理Service
 * @version 1.0
 * @Author lj
 * @date 2021/9/27 3:04 下午
 */
public interface UmsResourceCategoryService {

    /**
     * 获取所有资源分类
     */
    List<UmsResourceCategory> listAll();

    /**
     * 创建资源分类
     */
    int create(UmsResourceCategory category);

    /**
     * 根据分类id更新分类
     */
    int update(Long id, UmsResourceCategory category);

    /**
     * 删除分类
     */
    int delete(Long id);
}
