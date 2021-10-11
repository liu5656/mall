package com.macro.mall.admin.dao;

import com.macro.mall.model.PmsProductCategoryAttributeRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品分类和属性关系自定义Dao
 * @version 1.0
 * @Author lj
 * @date 2021/10/11 12:02 下午
 */
public interface PmsProductCategoryAttributeRelationDao {
    /**
     * 批量创建
     */
    int insertList(@Param("relations")List<PmsProductCategoryAttributeRelation> relations);
}
