package com.macro.mall.portal.domain;

import com.macro.mall.model.OmsCartItem;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/9/29 9:16 上午
 * @desc
 */
@Data
public class CartPromotionItem extends OmsCartItem {

    private String promotionMessage;            // 促销活动信息
    private BigDecimal reduceAmount;            // 促销活动减去的金额，针对每个商品
    private Integer realStock;                  // 商品的真实库存（剩余库存 - 锁定库存）
    private Integer integral;                   // 购买商品赠送积分
    private Integer growth;                     // 购买商品赠送成长值

}
