package com.macro.mall.admin.controller;

import com.macro.mall.admin.dto.SmsFlashPromotionSessionDetail;
import com.macro.mall.admin.service.SmsFlashPromotionSessionService;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.SmsFlashPromotionSession;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/19 5:17 下午
 * @desc
 */
@Api(tags = "SmsFlashPromotionSessionController", description = "限时购场次管理")
@RestController
@RequestMapping("/flashSession")
public class SmsFlashPromotionSessionController {

    @Autowired private SmsFlashPromotionSessionService sessionService;

    @ApiOperation("添加场次")
    @PostMapping("/create")
    public CommonResult create(@RequestBody SmsFlashPromotionSession session) {
        int count = sessionService.create(session);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("添加场次失败");
    }

    @ApiOperation("修改场次")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody SmsFlashPromotionSession promotionSession) {
        int count = sessionService.update(id, promotionSession);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改场次失败");
    }

    @ApiOperation("修改场次启用标志")
    @PostMapping("/update/status/{id}")
    public CommonResult updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        int count = sessionService.updateStatus(id, status);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改场次启用标志失败");
    }

    @ApiOperation("删除场次")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        int count = sessionService.delete(id);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("删除场次失败");
    }

    @ApiOperation("获取场次详情")
    @GetMapping("/{id}")
    public CommonResult getItem(@PathVariable Long id) {
        SmsFlashPromotionSession session = sessionService.getItem(id);
        return CommonResult.success(session);
    }

    @ApiOperation("获取全部场次")
    @GetMapping("/list")
    public CommonResult<List<SmsFlashPromotionSession>> list() {
        List<SmsFlashPromotionSession> sessions = sessionService.listall();
        return CommonResult.success(sessions);
    }

    @ApiOperation("全部可选场次和数量")
    @GetMapping("/selectList")
    public CommonResult<List<SmsFlashPromotionSessionDetail>> selectList(@RequestParam Long flashPromotionId) {
        List<SmsFlashPromotionSessionDetail> result = sessionService.list(flashPromotionId);
        return CommonResult.success(result);
    }

}
