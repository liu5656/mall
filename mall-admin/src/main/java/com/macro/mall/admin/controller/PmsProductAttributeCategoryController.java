package com.macro.mall.admin.controller;

import com.macro.mall.admin.dto.PmsProductAttributeCategoryItem;
import com.macro.mall.admin.service.PmsProductAttributeCategoryService;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.PmsProductAttributeCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/9 4:55 下午
 * @desc
 */
@Api(tags = "PmsProductAttributeCategoryController", description = "商品属性分类管理")
@RequestMapping("/productAttribute/category")
@RestController
public class PmsProductAttributeCategoryController {
    @Autowired private PmsProductAttributeCategoryService categoryService;

    @ApiOperation("添加商品属性分类")
    @PostMapping("/create")
    public CommonResult create(@RequestParam String name) {
        int count = categoryService.create(name);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("添加商品属性分类失败");
    }

    @ApiOperation("删除商品属性分类")
    @DeleteMapping("/delete/{categoryId}")
    public CommonResult delete(@PathVariable("categoryId") Long id) {
        int count = categoryService.delete(id);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("删除商品属性分类失败");
    }

    @ApiOperation("修改商品属性分类")
    @PostMapping("/update/{categoryId}")
    public CommonResult update(@PathVariable("categoryId") Long id, @RequestParam String name) {
        int count = categoryService.update(id, name);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改商品属性分类失败");
    }

    @ApiOperation("获取商品属性分类")
    @GetMapping("/{id}")
    public CommonResult getItem(@PathVariable("id") Long id) {
        PmsProductAttributeCategory category = categoryService.getItem(id);
        return CommonResult.success(category);
    }
    @ApiOperation("获取所有商品分类及其属性")
    @GetMapping("/list/withAttr")
    public CommonResult getListWithAttr() {
        List<PmsProductAttributeCategoryItem> items = categoryService.getListWithAttr();
        return CommonResult.success(items);
    }
    @ApiOperation("分页获取商品属性分类")
    @GetMapping("/list")
    public CommonResult<CommonPage<PmsProductAttributeCategory>> getList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                         @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List<PmsProductAttributeCategory> categories = categoryService.getList(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(categories));
    }
}
