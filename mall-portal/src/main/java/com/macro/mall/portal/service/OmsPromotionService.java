package com.macro.mall.portal.service;

import com.macro.mall.model.OmsCartItem;
import com.macro.mall.portal.domain.CartPromotionItem;

import java.util.List;

/**
 * 促销管理service
 * @version 1.0
 * @Author lj
 * @date 2021/9/28 5:58 下午
 */
public interface OmsPromotionService {
    /**
     * 计算购物车中的促销活动信息
     */
    List<CartPromotionItem> calcCartPromotion(List<OmsCartItem> cartItemList);
}
