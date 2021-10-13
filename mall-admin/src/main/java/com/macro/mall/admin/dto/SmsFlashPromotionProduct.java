package com.macro.mall.admin.dto;

import com.macro.mall.model.PmsProduct;
import com.macro.mall.model.SmsFlashPromotionProductRelation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 限时购和商品信息封装
 * @version 1.0
 * @Author lj
 * @date 2021/10/13 4:15 下午
 * @desc
 */
@Data
public class SmsFlashPromotionProduct extends SmsFlashPromotionProductRelation {
    @ApiModelProperty("关联商品")
    private PmsProduct product;
}
