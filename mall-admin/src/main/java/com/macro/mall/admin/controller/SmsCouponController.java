package com.macro.mall.admin.controller;

import com.macro.mall.admin.dto.SmsCouponParam;
import com.macro.mall.admin.service.SmsCouponService;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.SmsCoupon;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/13 10:47 上午
 * @desc
 */
@Api(tags = "SmsCouponCOntroller", description = "商品优惠券管理")
@RequestMapping("/coupon")
@RestController
public class SmsCouponController {

    @Autowired private SmsCouponService couponService;

    @ApiOperation("添加优惠券")
    @PostMapping("/create")
    public CommonResult create(@RequestBody SmsCouponParam couponParam) {
        int count = couponService.create(couponParam);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("新建优惠券失败");
    }

    @ApiOperation("删除优惠券")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        int count = couponService.delete(id);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("删除分类失败");
    }

    @ApiOperation("获取单个优惠券详情")
    @GetMapping("/{id}")
    public CommonResult getItem(@PathVariable Long id) {
        SmsCouponParam coupon = couponService.getItem(id);
        return CommonResult.success(coupon);
    }
    @ApiOperation("根据优惠券名称和类别分页获取优惠券列表")
    @GetMapping("/list")
    public CommonResult<CommonPage<SmsCoupon>> list(@RequestParam(required = false) String name,
                             @RequestParam(required = false) Integer type,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize) {
        List<SmsCoupon> coupons = couponService.list(name, type, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(coupons));
    }

    @ApiOperation("修改优惠券")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody SmsCouponParam couponParam) {
        int count = couponService.update(id, couponParam);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("更新优惠券失败");
    }
}
