package com.macro.mall.portal.domain;

import com.macro.mall.model.PmsProduct;
import com.macro.mall.model.PmsProductFullReduction;
import com.macro.mall.model.PmsProductLadder;
import com.macro.mall.model.PmsSkuStock;
import lombok.Data;

import java.util.List;

/**
 * 促销商品信息，包括sku、打折优惠、满减优惠
 * @version 1.0
 * @Author lj
 * @date 2021/9/28 5:48 下午
 * @desc
 */
@Data
public class PromotionProduct extends PmsProduct {
    private List<PmsSkuStock> skuStockList;
    private List<PmsProductLadder> productLadderList;
    private List<PmsProductFullReduction> productFullReductionList;
}
