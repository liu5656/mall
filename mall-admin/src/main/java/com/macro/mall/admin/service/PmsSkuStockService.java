package com.macro.mall.admin.service;

import com.macro.mall.model.PmsSkuStock;

import java.util.List;

/**
 * 商品SKU库存管理ervice
 * @version 1.0
 * @Author lj
 * @date 2021/10/11 10:20 上午
 */
public interface PmsSkuStockService {
    /**
     * 根据产品id和skuCode模糊查询
     */
    List<PmsSkuStock> getList(Long pid, String keyword);

    /**
     * 批量更新商品库存信息
     */
    int update(Long pid, List<PmsSkuStock> skuStocks);
}
