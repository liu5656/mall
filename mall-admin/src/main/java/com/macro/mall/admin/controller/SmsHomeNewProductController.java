package com.macro.mall.admin.controller;

import com.macro.mall.admin.service.SmsHomeNewProductService;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.SmsHomeNewProduct;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/14 2:05 下午
 * @desc
 */
@Api(tags = "SmsHomeNewProductController", description = "首页新品管理")
@RestController
@RequestMapping("/home/newProduct")
public class SmsHomeNewProductController {

    @Autowired private SmsHomeNewProductService productService;

    @ApiOperation("增加首页新品")
    @PostMapping("/create")
    public CommonResult create(@RequestBody List<SmsHomeNewProduct> products) {
        int count = productService.create(products);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("增加首页新品失败");
    }

    @ApiOperation("批量删除")
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam List<Long> ids) {
        int count = productService.delete(ids);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量删除失败");
    }

    @ApiOperation("分页查询")
    @GetMapping("/list")
    public CommonResult<CommonPage<SmsHomeNewProduct>> list(@RequestParam(required = false) String productName,
                                                            @RequestParam(required = false) Integer recommendStatus,
                                                            @RequestParam(defaultValue = "1") Integer pageNum,
                                                            @RequestParam(defaultValue = "5") Integer pageSize) {
        List<SmsHomeNewProduct> products = productService.list(productName, recommendStatus, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(products));
    }

    @ApiOperation("编辑新品排序")
    @PostMapping("/update/sort/{id}")
    public CommonResult updateSort(@PathVariable Long id, @RequestParam Integer sort) {
        int count = productService.updateSort(id, sort);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("编辑新品排序失败");
    }
    @ApiOperation("编辑新品推荐标志")
    @PostMapping("/update/recommendStatus")
    public CommonResult updateRecommendStatus(@RequestParam List<Long> ids, @RequestParam Integer recommendStatus) {
        int count = productService.updateRecommendStatus(ids, recommendStatus);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("编辑新品推荐标志失败");
    }

}
