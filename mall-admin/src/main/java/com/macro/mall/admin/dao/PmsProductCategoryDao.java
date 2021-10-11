package com.macro.mall.admin.dao;

import com.macro.mall.admin.dto.PmsProductCategoryWithChildrenItem;

import java.util.List;

/**
 * 商品分类自定义Dao
 * @version 1.0
 * @Author lj
 * @date 2021/10/11 11:47 上午
 */
public interface PmsProductCategoryDao {
    /**
     * 获取商品分类及其子分类
     */
    List<PmsProductCategoryWithChildrenItem> listWithChildren();
}
