package com.macro.mall.portal.domain;

import com.macro.mall.model.PmsProduct;
import com.macro.mall.model.PmsProductAttribute;
import com.macro.mall.model.PmsSkuStock;
import lombok.Data;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/9/28 5:40 下午
 * @desc
 */
@Data
public class CartProduct extends PmsProduct {
    private List<PmsProductAttribute> productAttributeList;         // 商品属性列表
    private List<PmsSkuStock> skuStockList;                         // 商品SKU库存列表
}
