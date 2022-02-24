package com.macro.mall.portal.domain;

import com.macro.mall.model.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 前台商品详情
 * @version 1.0
 * @Author lj
 * @date 2021/10/21 4:43 下午
 * @desc
 */
@Setter
@Getter
public class PmsPortalProductDetail {

    @ApiModelProperty("商品信息")
    private PmsProduct product;

    @ApiModelProperty("商品品牌")
    private PmsBrand brand;

    @ApiModelProperty("商品属性和参数")
    private List<PmsProductAttribute> productAttributeList;

    @ApiModelProperty("手动录入的商品属性和参数值")
    private List<PmsProductAttributeValue> productAttributeValueList;

    @ApiModelProperty("商品的sku库存信息")
    private List<PmsSkuStock> skuStockList;

    @ApiModelProperty("商品阶梯价格设置")
    private List<PmsProductLadder> productLadderList;

    @ApiModelProperty("商品满减信息")
    private List<PmsProductFullReduction> productFullReductionList;

    @ApiModelProperty("商品可用优惠券")
    private List<SmsCoupon> couponList;

}
