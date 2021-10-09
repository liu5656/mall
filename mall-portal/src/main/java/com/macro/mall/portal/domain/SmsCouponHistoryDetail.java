package com.macro.mall.portal.domain;

import com.macro.mall.model.SmsCoupon;
import com.macro.mall.model.SmsCouponHistory;
import com.macro.mall.model.SmsCouponProductCategoryRelation;
import com.macro.mall.model.SmsCouponProductRelation;
import lombok.Data;

import java.util.List;

/**
 * 优惠券领取历史详情，包括优惠券信息和关联详情
 * @version 1.0
 * @Author lj
 * @date 2021/9/30 9:37 上午
 * @desc
 */
@Data
public class SmsCouponHistoryDetail extends SmsCouponHistory {
    private SmsCoupon coupon;                                                   // 相关优惠券信息
    private List<SmsCouponProductRelation> productRelations;                    // 优惠券关联商品
    private List<SmsCouponProductCategoryRelation> productCategoryRelations;    // 优惠券关联商品分类
}
