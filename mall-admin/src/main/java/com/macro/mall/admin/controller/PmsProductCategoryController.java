package com.macro.mall.admin.controller;

import com.macro.mall.admin.dto.PmsProductCategoryParam;
import com.macro.mall.admin.dto.PmsProductCategoryWithChildrenItem;
import com.macro.mall.admin.service.PmsProductCategoryService;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.PmsProductCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/11 3:09 下午
 * @desc
 */
@Api(tags = "PmsProductCategoryController", description = "商品分类管理")
@RestController
@RequestMapping("/productCategory")
public class PmsProductCategoryController {
    @Autowired private PmsProductCategoryService categoryService;

    @ApiOperation("添加商品分类")
    @PostMapping("/create")
    public CommonResult create(@Validated @RequestBody PmsProductCategoryParam param) {
        int count = categoryService.create(param);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("添加商品分类失败");
    }

    @ApiOperation("删除商品分类")
    @DeleteMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        int count = categoryService.delete(id);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("删除商品分类失败");
    }

    @ApiOperation("通过分类id查询分类")
    @GetMapping("/{id}")
    public CommonResult getItem(@PathVariable Long id) {
        PmsProductCategory category = categoryService.getItem(id);
        return CommonResult.success(category);
    }

    @ApiOperation("分页查询商品")
    @GetMapping("/list/{parentId}")
    public CommonResult<CommonPage<PmsProductCategory>> getList(@PathVariable Long parentId,
                                                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<PmsProductCategory> categories = categoryService.getList(parentId, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(categories));
    }
    @ApiOperation("查询所有一级分类及其子分类")
    @GetMapping("/list/withChildren")
    public CommonResult<List<PmsProductCategoryWithChildrenItem>> listWithChildren() {
        List<PmsProductCategoryWithChildrenItem> items = categoryService.listWithChildren();
        return CommonResult.success(items);
    }

    @ApiOperation("更新分类信息")
    @PutMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id, @Validated @RequestBody PmsProductCategoryParam param) {
        int count = categoryService.update(id, param);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("更行分类信息失败");
    }
    @ApiOperation("修改导航栏显示状态")
    @PutMapping("/update/navStatus")
    public CommonResult update(@RequestParam List<Long> ids, @RequestParam Integer navStatus) {
        int count = categoryService.updateNavStatus(ids, navStatus);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("更新分类导航来显示状态失败");
    }

    @ApiOperation("修改分类显示状态")
    @PostMapping("/update/showStatus")
    public CommonResult updateShowStatus(@RequestParam List<Long> ids, @RequestParam Integer showStatus) {
        int count = categoryService.updateShowStatus(ids, showStatus);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("更新分类显示状态失败");
    }
}
