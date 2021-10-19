package com.macro.mall.admin.controller;

import com.macro.mall.admin.dto.*;
import com.macro.mall.admin.service.OmsOrderService;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.OmsOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/14 4:45 下午
 * @desc
 */
@Api(tags = "OmsOrderCOntroller", description = "订单管理")
@RestController
@RequestMapping("/order")
public class OmsOrderController {

    @Autowired private OmsOrderService orderService;

    @ApiOperation("批量删除订单")
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam List<Long> ids) {
        int count = orderService.delete(ids);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量删除订单失败");
    }

    @ApiOperation("批量发货")
    @PostMapping("/update/delivery")
    public CommonResult delivery(@RequestBody List<OmsOrderDeliveryParam> deliveryParamList) {
        int count = orderService.delivery(deliveryParamList);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量发货失败");
    }
    @ApiOperation("批量关闭订单")
    @PostMapping("/update/close")
    public CommonResult close(@RequestParam List<Long> ids, @RequestParam String note) {
        int count = orderService.close(ids, note);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量关闭订单失败");
    }
    @ApiOperation("修改收货人信息")
    @PostMapping("/update/receiverInfo")
    public CommonResult updateReceiver(@RequestBody OmsReceiverInfoParam receiverInfoParam) {
        int count = orderService.updateReceiverInfo(receiverInfoParam);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改收货人信息失败");
    }
    @ApiOperation("修改订单费用信息")
    @PostMapping("/update/moneyInfo")
    public CommonResult updateMoneyInfo(@RequestBody OmsMoneyInfoParam moneyInfoParam) {
        int count = orderService.updateMoneyInfo(moneyInfoParam);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改订单费用信息失败");
    }
    @ApiOperation("编辑订单备注信息")
    @PostMapping("/update/note")
    public CommonResult updateNoteInfo(@RequestParam Long id,
                                       @RequestParam String note,
                                       @RequestParam Integer status) {
        int count = orderService.updateNote(id, note, status);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("编辑订单备注失败");
    }


    @ApiOperation("获取订单详情")
    @GetMapping("/{id}")
    public CommonResult detail(@PathVariable Long id) {
        OmsOrderDetail order = orderService.getItem(id);
        return CommonResult.success(order);
    }
    @ApiOperation("查询订单")
    @GetMapping("/list")
    public CommonResult<CommonPage<OmsOrder>> list(OmsOrderQueryParam queryParam,
                                                   @RequestParam(defaultValue = "1") Integer pageNum,
                                                   @RequestParam(defaultValue = "5") Integer pageSize) {
        List<OmsOrder> orders = orderService.list(queryParam, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(orders));
    }


}
