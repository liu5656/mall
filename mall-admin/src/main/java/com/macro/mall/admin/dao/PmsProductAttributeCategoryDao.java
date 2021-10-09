package com.macro.mall.admin.dao;

import com.macro.mall.admin.dto.PmsProductAttributeCategoryItem;

import java.util.List;

/**
 * 商品属性分类管理自定义Dao
 * @version 1.0
 * @Author lj
 * @date 2021/10/9 4:17 下午
 */
public interface PmsProductAttributeCategoryDao {
    /**
     * 获取包含属性的商品属性分类
     */
    List<PmsProductAttributeCategoryItem> getListWithAttr();
}
