package com.macro.mall.portal.service;

import com.macro.mall.model.OmsCartItem;
import com.macro.mall.model.SmsCoupon;
import com.macro.mall.model.SmsCouponHistory;
import com.macro.mall.portal.domain.CartPromotionItem;
import com.macro.mall.portal.domain.SmsCouponHistoryDetail;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户优惠券管理service
 * @version 1.0
 * @Author lj
 * @date 2021/9/30 9:31 上午
 */
public interface UmsMemberCouponService {
    /**
     * 添加优惠券
     */
    @Transactional
    void add(Long couponId);

    /**
     * 获取优惠券历史列表
     */
    List<SmsCouponHistory> listHistory(Integer status);

    /**
     * 根据购物车信息获取可用优惠券
     */
    List<SmsCouponHistoryDetail> listCart(List<CartPromotionItem> cartItems, Integer type);

    /**
     * 获取当前商品相关优惠券
     */
    List<SmsCoupon> listByProduct(Long productId);

    /**
     * 获取用户优惠券列表
     */
    List<SmsCoupon> list(Integer status);

}
