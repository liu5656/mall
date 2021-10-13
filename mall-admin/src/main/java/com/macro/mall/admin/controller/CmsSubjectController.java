package com.macro.mall.admin.controller;

import com.macro.mall.admin.service.CmsSubjectService;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.CmsSubject;
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
 * @date 2021/10/13 9:40 上午
 * @desc
 */
@Api(tags = "CmsSubjectController", description = "商品专题管理")
@RestController
@RequestMapping("/subject")
public class CmsSubjectController {
    @Autowired private CmsSubjectService subjectService;

    @ApiOperation("获取全部商品专题")
    @GetMapping("/listAll")
    public CommonResult listAll() {
        List<CmsSubject> subjects = subjectService.listAll();
        return CommonResult.success(subjects);
    }

    @ApiOperation("关键字分页查询商品专题")
    @GetMapping("/list")
    public CommonResult<CommonPage<CmsSubject>> list(@RequestParam(required = false) String keyword,
                                                     @RequestParam(defaultValue = "1") Integer pageNum,
                                                     @RequestParam(defaultValue = "5") Integer pageSize) {
        List<CmsSubject> subjects = subjectService.list(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(subjects));
    }

}
