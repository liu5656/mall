package com.macro.mall.admin.dao;

import com.macro.mall.model.SmsCouponProductCategoryRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 优惠券和商品分类关系管理自定义Dao
 * @version 1.0
 * @Author lj
 * @date 2021/10/13 11:26 上午
 */
public interface SmsCouponProductCategoryRelationDao {
    /**
     * 批量插入
     */
    int insertList(@Param("list")List<SmsCouponProductCategoryRelation> relations);
}
