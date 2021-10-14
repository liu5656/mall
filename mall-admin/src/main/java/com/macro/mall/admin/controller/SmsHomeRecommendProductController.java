package com.macro.mall.admin.controller;

import com.macro.mall.admin.service.SmsHomeRecommendProductService;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.SmsHomeRecommendProduct;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/14 2:54 下午
 * @desc
 */
@Api(tags = "SmsHomeRecommendProductController", description = "首页商品推荐管理")
@RestController
@RequestMapping("/home/recommendProduct")
public class SmsHomeRecommendProductController {

    @Autowired private SmsHomeRecommendProductService recommendService;

    @ApiOperation("增加商品推荐")
    @PostMapping("/create")
    public CommonResult create(@RequestBody List<SmsHomeRecommendProduct> homeRecommendProductList) {
        int count = recommendService.create(homeRecommendProductList);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("增加商品推荐失败");
    }

    @ApiOperation("批量删除首页推荐商品")
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam List<Long> ids) {
        int count = recommendService.delete(ids);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量删除推荐商品失败");
    }

    @ApiOperation("编辑商品推荐顺序")
    @PostMapping("/update/sort/{id}")
    public CommonResult updateSort(@PathVariable Long id, @RequestParam Integer sort) {
        int count = recommendService.updateSort(id, sort);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("编辑商品推荐顺序失败");
    }
    @ApiOperation("编辑 商品推荐标志")
    @PostMapping("/update/recommendStatus")
    public CommonResult updateRecommendStatus(@RequestParam List<Long> ids, @RequestParam Integer recommendStatus) {
        int count = recommendService.updateRecommendstatus(ids, recommendStatus);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("编辑商品推荐标志失败");
    }

    @ApiOperation("分页获取首页推荐")
    @GetMapping("/list")
    public CommonResult<CommonPage<SmsHomeRecommendProduct>> list(@RequestParam(required = false) String productName,
                             @RequestParam(required = false) Integer recommendStatus,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize) {
        List<SmsHomeRecommendProduct> products = recommendService.list(productName, recommendStatus, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(products));
    }




}
