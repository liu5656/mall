package com.macro.mall.admin.controller;

import com.macro.mall.admin.dto.OmsReturnApplyQueryParam;
import com.macro.mall.admin.dto.OmsReturnApplyResult;
import com.macro.mall.admin.dto.OmsUpdateStatusParam;
import com.macro.mall.admin.service.OmsOrderReturnApplyService;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.OmsOrderReturnApply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/19 2:04 下午
 * @desc
 */
@Api(tags = "OmsOrderReturnAplyController", description = "订单退货申请管理")
@RestController
@RequestMapping("/returnApply")
public class OmsOrderReturnApplyController {

    @Autowired private OmsOrderReturnApplyService applyService;

    @ApiOperation("分页查询退货申请")
    @GetMapping("/list")
    public CommonResult<CommonPage<OmsOrderReturnApply>> list(OmsReturnApplyQueryParam queryParam,
                                                              @RequestParam(defaultValue = "1") Integer pageNum,
                                                              @RequestParam(defaultValue = "5") Integer pageSize) {
        List<OmsOrderReturnApply> applies = applyService.list(queryParam, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(applies));
    }

    @ApiOperation("批量删除退货申请")
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam List<Long> ids) {
        int count = applyService.delete(ids);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量删除退货申请失败");
    }

    @ApiOperation("获取退货申请详情")
    @GetMapping("/{id}")
    public CommonResult getItem(@PathVariable Long id) {
        OmsReturnApplyResult detail = applyService.getItem(id);
        return CommonResult.success(detail);
    }

    @ApiOperation("修改退货申请状态")
    @PostMapping("/update/status/{id}")
    public CommonResult updateStatus(@PathVariable Long id, @RequestBody OmsUpdateStatusParam statusParam) {
        int count = applyService.updateStatus(id, statusParam);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("更改退货申请状态失败");
    }

}
