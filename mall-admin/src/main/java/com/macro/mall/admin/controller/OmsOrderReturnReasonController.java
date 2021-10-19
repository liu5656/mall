package com.macro.mall.admin.controller;

import com.macro.mall.admin.service.OmsOrderReturnReasonService;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.OmsOrderReturnReason;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/19 3:58 下午
 * @desc
 */
@Api(tags = "OmsOrderReturnReasonController", description = "退货原因管理")
@RestController
@RequestMapping("/returnReason")
public class OmsOrderReturnReasonController {

    @Autowired private OmsOrderReturnReasonService reasonService;

    @ApiOperation("添加退货原因")
    @PostMapping("/create")
    public CommonResult create(@RequestBody OmsOrderReturnReason returnReason) {
        int count = reasonService.create(returnReason);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("增加退货原因失败");
    }

    @ApiOperation("批量删除退货原因")
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam List<Long> ids) {
        int count = reasonService.delete(ids);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量删除退货原因失败");
    }

    @ApiOperation("修改退货原因")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody OmsOrderReturnReason returnReason) {
        int count = reasonService.update(id, returnReason);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改退货原因失败");
    }

    @ApiOperation("批量修改退货原因状态")
    @PostMapping("/update/status")
    public CommonResult updateStatus(@RequestParam List<Long> ids, Integer status) {
        int count = reasonService.updateStatus(ids, status);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量修改退货原因转台失败");
    }

    @ApiOperation("查询退货原因")
    @GetMapping("/{id}")
    public CommonResult getItem(@PathVariable Long id) {
        OmsOrderReturnReason reason = reasonService.getItem(id);
        return CommonResult.success(reason);
    }
    @ApiOperation("分页查询退货原因")
    @GetMapping("/list")
    public CommonResult<CommonPage<OmsOrderReturnReason>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                                               @RequestParam(defaultValue = "5") Integer pageSize) {
        List<OmsOrderReturnReason> reasons = reasonService.list(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(reasons));
    }
}
