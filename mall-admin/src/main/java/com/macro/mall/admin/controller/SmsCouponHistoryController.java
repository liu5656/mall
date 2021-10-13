package com.macro.mall.admin.controller;

import com.macro.mall.admin.service.SmsCouponHistoryService;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.SmsCouponHistory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/13 10:15 上午
 * @desc
 */
@Api(tags = "SmsCOuponHistoryCotroller", description = "优惠券领取记录管理")
@RestController
@RequestMapping("/couponHistory")
public class SmsCouponHistoryController {

    @Autowired private SmsCouponHistoryService couponHistoryService;

    @ApiOperation("根据优惠券id、使用状态、订单号分页查询优惠券记录")
    @GetMapping("/list")
    public CommonResult<CommonPage<SmsCouponHistory>> list(@RequestParam(required = false) Long couponId,
                                                    @RequestParam(required = false) Integer useStatus,
                                                    @RequestParam(required = false) String orderSn,
                                                    @RequestParam(defaultValue = "5") Integer pageSize,
                                                    @RequestParam(defaultValue = "1") Integer pageNum) {
        List<SmsCouponHistory> coupons = couponHistoryService.list(couponId, useStatus, orderSn, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(coupons));
    }

}
