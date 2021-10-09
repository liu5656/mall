package com.macro.mall.admin.controller;

import com.macro.mall.admin.dto.PmsProductAttrInfo;
import com.macro.mall.admin.dto.PmsProductAttributeParam;
import com.macro.mall.admin.service.PmsProductAttributeService;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.PmsProductAttribute;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/9 11:24 上午
 * @desc
 */
@Api(tags = "PmsProductAttributeController", description = "商品属性管理")
@RequestMapping("/productAttribute")
@RestController
public class PmsProductAttributeController {

    @Autowired private PmsProductAttributeService attributeService;

    @ApiOperation("根据分类查询属性列表或参数列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "type",
            value = "0-属性，1-参数",
            required = false,
            paramType = "query",
            dataType = "integer")})
    @GetMapping("/list/{cid}")
    public CommonResult<CommonPage<PmsProductAttribute>> getList(@PathVariable Long cid,
                                                                 @RequestParam(value = "type") Integer type,
                                                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                 @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize){
        List<PmsProductAttribute> attributes = attributeService.getList(cid, type, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(attributes));
    }

    @ApiOperation("添加商品属性信息")
    @PostMapping("/create")
    public CommonResult create(@RequestBody PmsProductAttributeParam param) {
        int count = attributeService.create(param);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("添加商品信息失败");
    }

    @ApiOperation("修改商品属性信息")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody PmsProductAttributeParam param) {
        int count = attributeService.update(id, param);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("更新商品属性信息失败");
    }

    @ApiOperation("查询单个商品属性")
    @GetMapping("/{id}")
    public CommonResult getItem(@PathVariable Long id) {
        PmsProductAttribute attribute = attributeService.getItem(id);
        return CommonResult.success(attribute);
    }

    @ApiOperation("批量删除商品属性")
    @DeleteMapping("/delete")
    public CommonResult delete(List<Long> ids) {
        int count = attributeService.delete(ids);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取商品属性分类下的属性列表")
    @GetMapping("/attrInfo/{productCategoryId}")
    public CommonResult getAttrInfo(@PathVariable("productCategoryId") Long id) {
        List<PmsProductAttrInfo> attrInfos = attributeService.getProductAttributeInfo(id);
        return CommonResult.success(attrInfos);
    }

}
