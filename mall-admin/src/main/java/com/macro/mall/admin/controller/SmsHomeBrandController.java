package com.macro.mall.admin.controller;

import com.macro.mall.admin.service.SmsHomeBrandService;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.SmsHomeBrand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/14 9:33 上午
 * @desc
 */
@Api(tags = "SmsHOmeBrandController", description = "首页品牌管理")
@RestController
@RequestMapping("/home/brand")
public class SmsHomeBrandController {

    @Autowired private SmsHomeBrandService brandService;

    @ApiOperation("添加首页品牌推荐")
    @PostMapping("/create")
    public CommonResult create(@RequestBody List<SmsHomeBrand> brands) {
        int count = brandService.create(brands);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.success("添加首页品牌推荐失败");
    }

    @ApiOperation("批量删除首页品牌")
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam List<Long> ids) {
        int count = brandService.delete(ids);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量删除品牌失败");
    }

    @ApiOperation("修改首页品牌排序")
    @PostMapping("/update/sort/{id}")
    public CommonResult update(@PathVariable Long id, Integer sort) {
        int count = brandService.update(id, sort);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改首页品牌排序失败");
    }
    @ApiOperation("批量修改首页品牌的推荐")
    @PostMapping("/update/recommend")
    public CommonResult updateRecommend(@RequestParam List<Long> ids, Integer recommendStatus) {
        int count = brandService.update(ids, recommendStatus);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量修改首页商品推荐失败");
    }

    @ApiOperation("分页查询首页品牌")
    @GetMapping("/list")
    public CommonResult<CommonPage<SmsHomeBrand>> list(@RequestParam(required = false) String keyword,
                                                       @RequestParam(required = false) Integer recommendStatus,
                                                       @RequestParam(defaultValue = "1") Integer pageNum,
                                                       @RequestParam(defaultValue = "5") Integer pageSize) {
        List<SmsHomeBrand> brands = brandService.list(keyword, recommendStatus, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(brands));
    }

}
