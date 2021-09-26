package com.macro.mall.admin.controller;

import com.macro.mall.admin.service.UmsRoleService;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.UmsMenu;
import com.macro.mall.model.UmsResource;
import com.macro.mall.model.UmsRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/9/26 2:59 下午
 * @desc
 */
@RestController
@Api(tags = "UmsRoleController", description = "后台用户角色管理")
@RequestMapping("/role")
public class UmsRoleController {
    @Autowired private UmsRoleService roleService;

    @ApiOperation("添加角色")
    @PostMapping("/create")
    public CommonResult create(@RequestBody UmsRole role) {
        int count = roleService.create(role);
        if (count > 0) {
            return CommonResult.success("角色创建成功");
        }
        return CommonResult.failed("角色创建失败");
    }

    @ApiOperation("修改角色")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable("id") Long id, @RequestBody UmsRole role) {
        int count = roleService.update(id, role);
        if (count > 0) {
            return CommonResult.success("修改角色成功");
        }
        return CommonResult.failed("修改角色失败");
    }

    @ApiOperation("批量删除角色")
    @DeleteMapping("/delete")
    public CommonResult delete(@RequestParam List<Long> ids) {
        int count = roleService.delete(ids);
        if (count > 0) {
            return CommonResult.success("批量删除角色成功");
        }
        return CommonResult.failed("批量删除角色失败");
    }

    @ApiOperation("获取所有角色")
    @GetMapping("/listAll")
    public CommonResult<List<UmsRole>> listAll() {
        List<UmsRole> all = roleService.list();
        return CommonResult.success(all);
    }

    @ApiOperation("根据角色名称分页获取角色")
    @GetMapping("/list")
    public CommonResult<CommonPage<UmsRole>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List<UmsRole> roles = roleService.list(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(roles));
    }

    @ApiOperation("修改角色状态")
    @PostMapping("/updateStatus/{id}")
    public CommonResult updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        UmsRole role = new UmsRole();
        role.setId(id);
        role.setStatus(status);
        int count = roleService.update(id, role);
        if (count > 0) {
            return CommonResult.success("修改角色状态成功");
        }
        return CommonResult.failed("修改角色装填失败");
    }

    @ApiOperation("获取角色相关菜单")
    @GetMapping("/listMenu/{roleId}")
    public CommonResult<List<UmsMenu>> listMenu(@PathVariable("roleId") Long id ) {
        List<UmsMenu> menus = roleService.listMenu(id);
        return CommonResult.success(menus);
    }

    @ApiOperation("获取角色相关资源")
    @GetMapping("/listResource/{roleId}")
    public CommonResult<List<UmsResource>> listResource(@PathVariable("roleId") Long id) {
        List<UmsResource> resources = roleService.listResource(id);
        return CommonResult.success(resources);
    }

    @ApiOperation("给角色分配资源")
    @PostMapping("/allocMenu")
    public CommonResult allocMenu(@RequestParam Long roleId, @RequestParam List<Long> menuIds) {
        int count = roleService.allocMenu(roleId, menuIds);
        return CommonResult.success(count);
    }

    @ApiOperation("给角色分配资源")
    @PostMapping("/allocResource")
    public CommonResult allocResource(@RequestParam Long roleId, @RequestParam List<Long> resourceIds) {
        int count = roleService.allocResource(roleId, resourceIds);
        return CommonResult.success(count);
    }






}
