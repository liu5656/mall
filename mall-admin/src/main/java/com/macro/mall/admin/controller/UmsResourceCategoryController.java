package com.macro.mall.admin.controller;

import com.macro.mall.admin.service.UmsResourceCategoryService;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.UmsResourceCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/9/27 3:13 下午
 * @desc
 */
@Api(tags = "UmsResourceCategoryController", description = "后台资源分类管理")
@RestController
@RequestMapping("/resourceCategory")
public class UmsResourceCategoryController {

    @Autowired private UmsResourceCategoryService categoryService;

    @ApiOperation("后台查询所有资源分类")
    @GetMapping("/listAll")
    public CommonResult<List<UmsResourceCategory>> listAll() {
        return CommonResult.success(categoryService.listAll());
    }

    @ApiOperation("后台添加新的资源分类")
    @PostMapping("/create")
    public CommonResult create(@RequestBody UmsResourceCategory category) {
        int count = categoryService.create(category);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("添加资源分类失败");
    }

    @ApiOperation("修改后台资源分类")
    @PostMapping("/update/{categoryId}")
    public CommonResult update(@PathVariable("categoryId") Long id, @RequestBody UmsResourceCategory category) {
        int count = categoryService.update(id, category);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("更新资源分类失败");
    }

    @ApiOperation("删除资源分类")
    @DeleteMapping("/delete/{categoryId}")
    public CommonResult delete(@PathVariable("categoryId") Long id) {
        int count = categoryService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("删除资源分类失败");
    }
}
