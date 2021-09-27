package com.macro.mall.admin.controller;

import com.macro.mall.admin.dto.UmsMenuNode;
import com.macro.mall.admin.service.UmsMenuService;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.UmsMenu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/9/27 10:22 上午
 * @desc
 */
@Api(tags = "UmsMenuController", description = "后台菜单管理")
@RestController
@RequestMapping("/menu")
public class UmsMenuController {
    @Autowired private UmsMenuService menuService;

    @ApiOperation("添加后台菜单")
    @PostMapping("/create")
    public CommonResult create(@RequestBody UmsMenu menu) {
        menu.setCreateTime(new Date());
        int count = menuService.create(menu);
        if (count > 0) {
            return CommonResult.success("添加菜单成功");
        }
        return CommonResult.failed("添加菜单失败");
    }

    @ApiOperation("删除后台菜单")
    @DeleteMapping("/delete/{menuId}")
    public CommonResult delete(@PathVariable("menuId") Long id) {
        int count = menuService.delete(id);
        if (count > 0) {
            return CommonResult.success("菜单删除成功");
        }
        return CommonResult.failed("菜单删除失败");
    }

    @ApiOperation("根据id获取后台菜单")
    @GetMapping("/{menuId}")
    public CommonResult getItem(@PathVariable("menuId") Long id) {
        UmsMenu menu = menuService.getItem(id);
        return CommonResult.success(menu);
    }

    @ApiOperation("根据父id查询统计菜单")
    @GetMapping("/list/{parentId}")
    public CommonResult<CommonPage<UmsMenu>> list(@PathVariable Long parentId,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List<UmsMenu> menus = menuService.list(parentId, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(menus));
    }

    @ApiOperation("树形结构返回菜单列表")
    @GetMapping("/treeList")
    public CommonResult<List<UmsMenuNode>> treeList() {
        return CommonResult.success(menuService.treeList());
    }

    @ApiOperation("更新后台菜单")
    @PostMapping("/update/{menuId}")
    public CommonResult update(@PathVariable("menuId") Long id, @RequestBody UmsMenu menu) {
        int count = menuService.update(id,menu);
        if (count > 0) {
            return CommonResult.success("更新菜单成功");
        }
        return CommonResult.failed("更新菜单失败");
    }

    @ApiOperation("修改菜单显示状态")
    @PostMapping("/updateHidden/{menuId}")
    public CommonResult updateHidden(@PathVariable("menuId") Long id, Integer hidden) {
        int count = menuService.updateHidden(id, hidden);
        if (count > 0) {
            return CommonResult.success("修改菜单显示状态成功");
        }
        return CommonResult.failed("修改菜单显示状态失败");
    }
}