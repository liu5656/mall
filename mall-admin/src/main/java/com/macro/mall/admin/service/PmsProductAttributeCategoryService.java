package com.macro.mall.admin.service;

import com.macro.mall.admin.dto.PmsProductAttributeCategoryItem;
import com.macro.mall.model.PmsProductAttributeCategory;

import java.util.List;

/**
 * 商品属性分类管理Service
 * @version 1.0
 * @Author lj
 * @date 2021/10/9 4:44 下午
 */
public interface PmsProductAttributeCategoryService {

    /**
     * 创建属性分类
     */
    int create(String name);

    /**
     * 修改属性分类
     */
    int update(Long id, String name);

    /**
     * 删除属性分类
     */
    int delete(Long id);

    /**
     * 获取属性分类详情
     */
    PmsProductAttributeCategory getItem(Long id);

    /**
     * 分页查询属性分类
     */
    List<PmsProductAttributeCategory> getList(Integer pageNum, Integer pageSize);

    /**
     * 获取包含属性的分类
     */
    List<PmsProductAttributeCategoryItem> getListWithAttr();

}
