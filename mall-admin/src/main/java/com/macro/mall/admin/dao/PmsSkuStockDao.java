package com.macro.mall.admin.dao;

import com.macro.mall.model.PmsSkuStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品SKUS库存管理自定义Dao
 * @version 1.0
 * @Author lj
 * @date 2021/10/11 9:43 上午
 */
public interface PmsSkuStockDao {
    /**
     * 批量插入操作
     */
    int insertList(@Param("list") List<PmsSkuStock> skuStocks);

    /**
     * 批量插入或替换操作
     */
    int replaceList(@Param("list") List<PmsSkuStock> skuStocks);
}
