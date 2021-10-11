package com.macro.mall.admin.controller;

import com.macro.mall.admin.service.PmsSkuStockService;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.PmsSkuStock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/11 10:38 上午
 * @desc
 */
@Api(tags = "PmsSkuStockController", description = "商品SKU库存管理")
@RequestMapping("/sku")
@RestController
public class PmsSkuStockController {
    @Autowired private PmsSkuStockService skuStockService;

    @ApiOperation("根据商品编号和sku编码模糊搜索sku库存")
    @GetMapping("/{pid}")
    public CommonResult<List<PmsSkuStock>> getList(@PathVariable("pid") Long pid, @RequestParam(required = false) String keyword) {
        List<PmsSkuStock> skuStocks = skuStockService.getList(pid, keyword);
        return CommonResult.success(skuStocks);
    }

    @ApiOperation("批量更新sku库存信息")
    @PostMapping("/update/{pid}")
    public CommonResult update(@PathVariable Long pid, @RequestBody List<PmsSkuStock> skuStockList) {
        int count = skuStockService.update(pid, skuStockList);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

}
