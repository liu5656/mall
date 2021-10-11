package com.macro.mall.admin.controller;

import com.macro.mall.admin.dto.PmsBrandParam;
import com.macro.mall.admin.service.PmsBrandService;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.PmsBrand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/11 5:25 下午
 * @desc
 */
@Api(tags = "PmsBrandController", description = "商品品牌管理")
@RequestMapping("/brand")
@RestController
public class PmsBrandController {
    @Autowired private PmsBrandService brandService;

    @ApiOperation("添加品牌")
    @PostMapping("/create")
    public CommonResult create(@Validated @RequestBody PmsBrandParam brand) {
        int count = brandService.create(brand);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("品牌添加失败");
    }

    @ApiOperation("删除品牌")
    @DeleteMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        int count = brandService.delete(id);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("删除品牌失败");
    }
    @ApiOperation("批量删除品牌")
    @DeleteMapping("/deleteBatch")
    public CommonResult deleteBatch(@RequestParam List<Long> ids) {
        int count = brandService.deleteList(ids);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量删除品牌失败");
    }


    @ApiOperation("根据id查询品牌")
    @GetMapping("/{id}")
    public CommonResult getItem(@PathVariable Long id) {
        PmsBrand brand = brandService.getItem(id);
        return CommonResult.success(brand);
    }

    @ApiOperation("根据品牌名称分页获取")
    @GetMapping("/list")
    public CommonResult<CommonPage<PmsBrand>> getList(@RequestParam(value = "keyword", required = false) String keyword,
                                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                      @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List<PmsBrand> brands = brandService.getList(keyword,pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(brands));
    }

    @ApiOperation("获取所有品牌")
    @GetMapping("/listAll")
    public CommonResult<List<PmsBrand>> listAll() {
        return CommonResult.success(brandService.getAll());
    }

    @ApiOperation("更新品牌")
    @PutMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id, @Validated @RequestBody PmsBrandParam brand) {
        int count = brandService.update(id, brand);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("更新品牌失败");
    }
    @ApiOperation("批量更新显示标志")
    @PutMapping("/update/showStatus")
    public CommonResult updateShowStatus(@RequestParam List<Long> ids, @RequestParam Integer showStatus) {
        int count = brandService.updateFactoryStatus(ids, showStatus);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量更新显示标志失败");
    }

    @ApiOperation("批量更新工厂制造标志")
    @PutMapping("/update/factoryStatus")
    public CommonResult updateFactoryStatus(@RequestParam List<Long> ids, @RequestParam Integer factoryStatus) {
        int count = brandService.updateShowStatus(ids, factoryStatus);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量更新工厂制造标志失败");
    }

}
