package com.macro.mall.portal.domain;

import com.macro.mall.model.PmsProduct;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/21 10:16 上午
 * @desc
 */
@Setter
@Getter
public class FlashPromotionProduct extends PmsProduct {
    private BigDecimal flashPromotionPrice;         // 秒杀价格
    private Integer flashPromotionCount;            // 用户秒杀的数量
    private Integer flashPromotionLimit;            // 秒杀限购数量
}
