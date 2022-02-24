package com.macro.mall.portal.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.portal.service.PmsPortalProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/22 9:17 上午
 * @desc
 */
@Api(tags = "PmsPortalProductController", description = "前端商品管理")
@RestController
@RequestMapping("/product")
public class PmsPortalProductController {

//    @Autowired private PmsPortalProductService portalProductService;
//
//    @ApiOperation("综合搜索")
//    @ApiImplicitParam(name = "sort",
//            value = "排序字段:0->按相关度；1->按新品；2->按销量；3->价格从低到高；4->价格从高到低",
//            defaultValue = "0",
//            allowableValues = "0,1,2,3,4",
//            paramType = "query",
//            dataType = "integer")
//    @GetMapping("/search")
//    public CommonResult<CommonPage<PmsProduct>> search(@)
}
