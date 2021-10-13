package com.macro.mall.admin.dao;

import com.macro.mall.model.SmsCouponProductRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品和优惠券关系自定义Dao
 * @version 1.0
 * @Author lj
 * @date 2021/10/13 11:17 上午
 */
public interface SmsCouponProductRelationDao {
    /**
     * 批量创建
     */
    int insertList(@Param("list") List<SmsCouponProductRelation> relations);
}
