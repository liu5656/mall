package com.macro.mall.admin.controller;

import com.macro.mall.admin.service.UmsResourceService;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.UmsResource;
import com.macro.mall.security.component.DynamicSecurityMetadataSource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/9/26 4:35 下午
 * @desc
 */
@Api(tags = "UmsResourceController", description = "后台资源管理")
@RestController
@RequestMapping("/resource")
public class UmsResourceController {
    @Autowired private UmsResourceService resourceService;
    @Autowired private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    @ApiOperation("添加后台资源")
    @PostMapping("/create")
    public CommonResult create(@RequestBody UmsResource umsResource) {
        int count = resourceService.create(umsResource);
        dynamicSecurityMetadataSource.clearDataSource();
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改后台资源")
    @PostMapping("/update/{resourceId}")
    public CommonResult update(@PathVariable("resourceId") Long id, @RequestBody UmsResource resource) {
        int count = resourceService.update(id, resource);
        dynamicSecurityMetadataSource.clearDataSource();
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("根据id获取资源详情")
    @GetMapping("/{id}")
    public CommonResult getItem(@PathVariable Long id) {
        UmsResource resource = resourceService.getItem(id);
        return CommonResult.success(resource);
    }

    @ApiOperation("更加ID删除后台资源")
    @DeleteMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        int count = resourceService.delete(id);
        dynamicSecurityMetadataSource.clearDataSource();
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("根据ID删除后台资源失败");
    }

    @ApiOperation("分页模糊查询后台资源")
    @GetMapping("/list")
    public CommonResult<CommonPage<UmsResource>> list(@RequestParam(required = false) String urlKeyword,
                                                      @RequestParam(required = false) Long categoryId,
                                                      @RequestParam(required = false) String nameKeyword,
                                                      @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                      @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List<UmsResource> resources = resourceService.list(categoryId, nameKeyword, urlKeyword, pageSize, pageNo);
        return CommonResult.success(CommonPage.restPage(resources));
    }

    @ApiOperation("查询所有资源")
    @GetMapping("/listAll")
    public CommonResult<List<UmsResource>> listAll() {
        List<UmsResource> resources = resourceService.listAll();
        return CommonResult.success(resources);
    }



}
