package com.macro.mall.admin.dto;

import com.macro.mall.model.PmsProduct;
import com.macro.mall.model.SmsCoupon;
import com.macro.mall.model.SmsCouponProductCategoryRelation;
import com.macro.mall.model.SmsCouponProductRelation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 优惠券信息封装，包含绑定商品或分类
 * @version 1.0
 * @Author lj
 * @date 2021/10/13 10:49 上午
 * @desc
 */
@Data
public class SmsCouponParam extends SmsCoupon {
    @ApiModelProperty("优惠券绑定的商品")
    private List<SmsCouponProductRelation> productRelations;

    @ApiModelProperty("优惠券绑定分类")
    private List<SmsCouponProductCategoryRelation> categoryRelations;
}
