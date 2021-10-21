package com.macro.mall.portal.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.CmsSubject;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.model.PmsProductCategory;
import com.macro.mall.portal.domain.HomeContentResult;
import com.macro.mall.portal.service.HomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/21 2:35 下午
 * @desc
 */
@Api(tags = "HomeController", description = "首页内容管理")
@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired private HomeService homeService;

    @ApiOperation("获取首页内容")
    @GetMapping("/content")
    public CommonResult getConttent() {
        HomeContentResult content = homeService.content();
        return CommonResult.success(content);
    }

    @ApiOperation("分页获取推荐商品")
    @GetMapping("/recommendProductList")
    public CommonResult<CommonPage<PmsProduct>> recommendProductList(@RequestParam(defaultValue = "1") Integer pageNum,
                                                                     @RequestParam(defaultValue = "5") Integer pageSize) {
        List<PmsProduct> products = homeService.recommendProductList(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(products));
    }

    @ApiOperation("获取首页商品分类")
    @GetMapping("/productCateList/{parentId}")
    public CommonResult<List<PmsProductCategory>> categoryList(@RequestParam Long parentId) {
        List<PmsProductCategory> categories = homeService.getProductCategoryList(parentId);
        return CommonResult.success(categories);
    }

    @ApiOperation("根据分类获取专题")
    @GetMapping("/subjectList")
    public CommonResult<CommonPage<CmsSubject>> subjectList(@RequestParam(required = false) Long cateId,
                                                      @RequestParam(defaultValue = "1") Integer pageNum,
                                                      @RequestParam(defaultValue = "5") Integer pageSize) {
        List<CmsSubject> subjects = homeService.getSubjectList(cateId, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(subjects));
    }

    @ApiOperation("获取推荐商品")
    @GetMapping("/hotProductList")
    public CommonResult<CommonPage<PmsProduct>> hotProductList(@RequestParam(defaultValue = "1") Integer pageNum,
                                                         @RequestParam(defaultValue = "5") Integer pageSize) {
        List<PmsProduct> products = homeService.hotProductList(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(products));
    }

    @ApiOperation("获取推荐新品")
    @GetMapping("/newProductList")
    public CommonResult<CommonPage<PmsProduct>> newProductList(@RequestParam(defaultValue = "1") Integer pageNum,
                                                         @RequestParam(defaultValue = "5") Integer pageSize) {
        List<PmsProduct> products = homeService.newProductList(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(products));
    }

}
