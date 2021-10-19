package com.macro.mall.admin.controller;

import com.macro.mall.admin.service.OmsOrderSettingService;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.OmsOrderSetting;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/19 4:37 下午
 * @desc
 */
@Api(tags = "OmsOrderSettingController", description = "订单设置管理")
@RestController
@RequestMapping("/orderSetting")
public class OmsOrderSettingController {

    @Autowired private OmsOrderSettingService settingService;

    @ApiOperation("获取指定订单设置")
    @GetMapping("/{id}")
    public CommonResult getItem(@PathVariable Long id) {
        OmsOrderSetting record = settingService.getItem(id);
        return CommonResult.success(record);
    }

    @ApiOperation("修改指定订单设置")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody OmsOrderSetting setting) {
        int count = settingService.update(id, setting);;
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改指定订单设置失败");
    }

}
