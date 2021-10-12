package com.macro.mall.admin.controller;

import com.macro.mall.admin.dto.PmsProductParam;
import com.macro.mall.admin.dto.PmsProductQueryParam;
import com.macro.mall.admin.dto.PmsProductResult;
import com.macro.mall.admin.service.PmsProductService;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.PmsProduct;
import com.sun.tools.corba.se.idl.InterfaceGen;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/11 3:55 下午
 * @desc
 */
@Api(tags = "PmsProductController", description = "商品管理")
@RequestMapping("/product")
@RestController
public class PmsProductController {

    @Autowired private PmsProductService productService;

    @ApiOperation("创建商品")
    @PostMapping("/create")
    public CommonResult create(@RequestBody PmsProductParam productParam) {
        int count = productService.create(productParam);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("创建商品失败");
    }

    @ApiOperation("根据商品名称或货号模糊查询")
    @GetMapping("/simpleList")
    public CommonResult<List<PmsProduct>> getList(String keyword) {
        List<PmsProduct> products = productService.list(keyword);
        return CommonResult.success(products);
    }
    @ApiOperation("查询商品")
    @GetMapping("/list")
    public CommonResult<CommonPage<PmsProduct>> getList(PmsProductQueryParam productQueryParam,
                                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List<PmsProduct> products = productService.list(productQueryParam, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(products));
    }
    @ApiOperation("根据商品id获取商品编辑信息")
    @GetMapping("/updateInfo/{id}")
    public CommonResult getUpdateInfo(@PathVariable Long id) {
        PmsProductResult result = productService.getUpdateInfo(id);
        return CommonResult.success(result);
    }

    @ApiOperation("更新商品")
    @PutMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody PmsProductParam productParam){
        int count = productService.update(id, productParam);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("更新商品失败");
    }
    @ApiOperation("批量修改商品审核状态")
    @PutMapping("/update/verifyStatus")
    public CommonResult updateVerifyStatus(@RequestParam List<Long> ids, @RequestParam Integer verifyStatus, @RequestParam String detail) {
        int count = productService.updateVerifyStatus(ids, verifyStatus, detail);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改商品审核状态失败");
    }

    @ApiOperation("批量商品上下架")
    @PutMapping("/update/publishStatus")
    public CommonResult updatePublishStatus(@RequestParam List<Long> ids, @RequestParam Integer publishStatus) {
        int count = productService.updatePublishStatus(ids, publishStatus);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改商品上下架失败");
    }

    @ApiOperation("批量修改是否推荐商品")
    @PutMapping("/update/recommendStatus")
    public CommonResult updateRecommendStatus(@RequestParam List<Long> ids, @RequestParam Integer recommendStatus) {
        int count = productService.updateRecommendStatus(ids, recommendStatus);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改商品是否推荐失败");
    }
    @ApiOperation("批量设置是否为新品")
    @PutMapping("/update/newStatus")
    public CommonResult updateNewStatus(@RequestParam List<Long> ids, @RequestParam Integer newStatus) {
        int count = productService.updateNewStatus(ids, newStatus);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改商品是否为新品失败");
    }
    @ApiOperation("批量比较删除标志")
    @PutMapping("/update/deleteStatus")
    public CommonResult updateDeleteStatus(@RequestParam List<Long> ids, @RequestParam Integer deleteStatus) {
        int count = productService.updateDelete(ids, deleteStatus);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改商品删除标志失败");
    }
}
