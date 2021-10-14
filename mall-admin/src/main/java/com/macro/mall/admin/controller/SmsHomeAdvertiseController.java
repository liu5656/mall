package com.macro.mall.admin.controller;

import com.macro.mall.admin.service.SmsHomeAdvertiseService;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.SmsHomeAdvertise;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/14 11:08 上午
 * @desc
 */
@Api(tags = "SmsHomeAdvertiseController", description = "首页广告管理")
@RestController
@RequestMapping("/home/advertise")
public class SmsHomeAdvertiseController {

    @Autowired private SmsHomeAdvertiseService advertiseService;

    @ApiOperation("添加首页广告")
    @PostMapping("/create")
    public CommonResult create(@RequestBody SmsHomeAdvertise advertise) {
        int count = advertiseService.create(advertise);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("添加首页广告失败");
    }

    @ApiOperation("删除广告")
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam List<Long> ids) {
        int count = advertiseService.delete(ids);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("删除广告失败");
    }

    @ApiOperation("编辑上线状态")
    @PostMapping("/update/status/{id}")
    public CommonResult updateStatus(@PathVariable Long id, Integer status) {
        int count = advertiseService.updateStatus(id, status);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("编辑上线状态失败");
    }
    @ApiOperation("编辑广告")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody SmsHomeAdvertise advertise) {
        int count = advertiseService.update(id, advertise);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("编辑广告失败");
    }

    @ApiOperation("获取广告详情")
    @GetMapping("/{id}")
    public CommonResult getItem(@PathVariable Long id) {
        SmsHomeAdvertise advertise = advertiseService.getItem(id);
        return CommonResult.success(advertise);
    }

    @ApiOperation("分页查询广告")
    @GetMapping("/list")
    public CommonResult<CommonPage<SmsHomeAdvertise>> list(@RequestParam(required = false) String name,
                                                           @RequestParam(required = false) Integer type,
                                                           @RequestParam(required = false) String endTime,
                                                           @RequestParam(defaultValue = "1") Integer pageNum,
                                                           @RequestParam(defaultValue = "5") Integer pageSize) {
        List<SmsHomeAdvertise> advertises = advertiseService.getList(name,type,endTime,pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(advertises));
    }


}
