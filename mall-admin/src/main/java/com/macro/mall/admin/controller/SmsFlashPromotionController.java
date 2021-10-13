package com.macro.mall.admin.controller;

import com.macro.mall.admin.service.SmsFlashPromotionService;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.SmsFlashPromotion;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/13 3:43 下午
 * @desc
 */
@Api(tags = "SmsFlashPromotionController", description = "限时购活动管理")
@RestController
@RequestMapping("/flash")
public class SmsFlashPromotionController {

    @Autowired private SmsFlashPromotionService promotionService;

    @ApiOperation("增加活动")
    @PostMapping("/create")
    public CommonResult create(@RequestBody SmsFlashPromotion promotion) {
        int count = promotionService.create(promotion);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("新增限时购活动失败");
    }

    @ApiOperation("删除活动")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        int count = promotionService.delete(id);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("删除限时购活动失败");
    }

    @ApiOperation("获取活动详情")
    @GetMapping("/{id}")
    public CommonResult getItem(@PathVariable Long id) {
        SmsFlashPromotion promotion = promotionService.getItem(id);
        return CommonResult.success(promotion);
    }

    @ApiOperation("关键词分页查询")
    @GetMapping("/list")
    public CommonResult<CommonPage<SmsFlashPromotion>> list(@RequestParam(required = false) String keyword,
                                                            @RequestParam(defaultValue = "1") Integer pageNum,
                                                            @RequestParam(defaultValue = "5") Integer pageSize) {
        List<SmsFlashPromotion> promotions = promotionService.list(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(promotions));
    }

    @ApiOperation("编辑限时购活动")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody SmsFlashPromotion flashPromotion) {
        int count = promotionService.update(id, flashPromotion);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("编辑限时购活动失败");
    }
    @ApiOperation("编辑上线标志")
    @PostMapping("/update/status/{id}")
    public CommonResult updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        int count = promotionService.updateStatus(id, status);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("编辑上线标志失败");
    }


}
