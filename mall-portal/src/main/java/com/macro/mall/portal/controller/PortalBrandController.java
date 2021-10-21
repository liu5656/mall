package com.macro.mall.portal.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.PmsBrand;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.portal.service.PortalBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/21 3:45 下午
 * @desc
 */
@Api(tags = "PortalBrandController", description = "首页品牌管理")
@RestController
@RequestMapping("/brand")
public class PortalBrandController {

    @Autowired private PortalBrandService brandService;

    @ApiOperation("分页获取推荐品牌")
    @GetMapping("/recommendList")
    public CommonResult<List<PmsBrand>> recommendList(@RequestParam(defaultValue = "1") Integer pageNum,
                                                      @RequestParam(defaultValue = "5") Integer pageSize) {
        List<PmsBrand> brands = brandService.recommendList(pageNum, pageSize);
        return CommonResult.success(brands);
    }

    @ApiOperation("获取品牌详情")
    @GetMapping("/detail/{brandId}")
    public CommonResult detail(@PathVariable Long brandId) {
        PmsBrand brand = brandService.detail(brandId);
        return CommonResult.success(brand);
    }

    @ApiOperation("分页获取品牌相关商品")
    @GetMapping("/productList")
    public CommonResult<CommonPage<PmsProduct>> productList(@RequestParam Long brandId,
                                                            @RequestParam(defaultValue = "1") Integer pageNum,
                                                            @RequestParam(defaultValue = "5") Integer pageSize) {
        CommonPage<PmsProduct> products = brandService.productList(brandId, pageNum, pageSize);
        return CommonResult.success(products);
    }

}
