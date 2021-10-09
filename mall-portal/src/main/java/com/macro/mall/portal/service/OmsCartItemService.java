package com.macro.mall.portal.service;

import com.macro.mall.model.OmsCartItem;
import com.macro.mall.portal.domain.CartProduct;
import com.macro.mall.portal.domain.CartPromotionItem;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 购物车管理Service
 * @version 1.0
 * @Author lj
 * @date 2021/10/8 2:41 下午
 */
public interface OmsCartItemService {

    /**
     * 查询购物车中是否包含该商品，有就增加数量，没有就添加到购物车
     */
    @Transactional
    int add(OmsCartItem item);

    /**
     * 批量删除购物车中的商品
     */
    int delete(Long memberId, List<Long> ids);
    /**
     * 清空购物车
     */
    int clear(Long memberId);

    /**
     * 根据会员编号获取购物车列表
     */
    List<OmsCartItem> list(Long memberId);
    /**
     * 获取包含促销活动信息的购物车列表
     */
    List<CartPromotionItem> listPromotion(Long memberId, List<Long> cartIds);
    /**
     * 获取购物车中用于选择商品规格的商品信息
     */
    CartProduct getCartProduct(Long productId);

    /**
     * 更新购物车商品数量
     */
    int updateQuantity(Long id, Long memberId, Integer quantity);
    /**
     * 更新购物车中商品规格
     */
    @Transactional
    int updateAttr(OmsCartItem item);

}
