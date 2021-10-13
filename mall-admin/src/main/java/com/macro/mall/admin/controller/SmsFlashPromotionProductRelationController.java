package com.macro.mall.admin.controller;

import com.macro.mall.admin.dto.SmsFlashPromotionProduct;
import com.macro.mall.admin.service.SmsFlashPromotionProductRelationService;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.SmsFlashPromotionProductRelation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/13 5:25 下午
 * @desc
 */
@Api(tags = "SmsFlashPromotionProductRelationController", description = "限时购和商品关系管理")
@RestController
@RequestMapping("/flashProductRelation")
public class SmsFlashPromotionProductRelationController {

    @Autowired private SmsFlashPromotionProductRelationService relationService;

    @ApiOperation("批量选择商品添加关联")
    @PostMapping("/create")
    public CommonResult create(@RequestBody List<SmsFlashPromotionProductRelation> relations) {
        int count = relationService.insertList(relations);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量选择商品添加关联失败");
    }

    @ApiOperation("修改关联信息")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody SmsFlashPromotionProductRelation relation) {
        int count = relationService.update(id, relation);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("编辑限时购和商品关联失败");
    }

    @ApiOperation("删除关联")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        int count = relationService.delete(id);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("删除关联失败");
    }

    @ApiOperation("获取限时购和商品的关联信息")
    @GetMapping("/{id}")
    public CommonResult getItem(@PathVariable Long id) {
        SmsFlashPromotionProductRelation relation = relationService.getItem(id);
        return CommonResult.success(relation);
    }

    @ApiOperation("分页查询不同场次关联及商品信息")
    @GetMapping("/list")
    public CommonResult<CommonPage<SmsFlashPromotionProduct>> list(@RequestParam Long flashPromotionId,
                                                             @RequestParam Long flashPromotionSessionId,
                                                             @RequestParam(defaultValue = "1") Integer pageNum,
                                                             @RequestParam(defaultValue = "5") Integer pageSize) {
        List<SmsFlashPromotionProduct> items = relationService.getList(flashPromotionId, flashPromotionSessionId, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(items));
    }


}
