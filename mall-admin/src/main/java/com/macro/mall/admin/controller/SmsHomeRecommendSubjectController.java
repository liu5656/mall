package com.macro.mall.admin.controller;

import com.macro.mall.admin.service.SmsHomeRecommendSubjectService;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.SmsHomeRecommendSubject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/14 3:48 下午
 * @desc
 */
@Api(tags = "SmsHomeRecommendSubjectController", description = "首页推荐专题管理")
@RestController
@RequestMapping("/home/recommendSubject")
public class SmsHomeRecommendSubjectController {

    @Autowired private SmsHomeRecommendSubjectService service;

    @ApiOperation("增减")
    @PostMapping("/create")
    public CommonResult create(@RequestBody List<SmsHomeRecommendSubject> subjects) {
        int count = service.create(subjects);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("首页推荐专题增加失败");
    }

    @ApiOperation("删除")
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam List<Long> ids) {
        int count = service.delete(ids);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("首页推荐专题删除失败");
    }

    @ApiOperation("分页查找")
    @GetMapping("/list")
    public CommonResult<CommonPage<SmsHomeRecommendSubject>> list(@RequestParam(required = false) String subjectName,
                             @RequestParam(required = false) Integer recommendStatus,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize) {
        List<SmsHomeRecommendSubject> subjects = service.list(subjectName, recommendStatus, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(subjects));
    }

    @ApiOperation("编辑推荐顺序")
    @PostMapping("/update/sort/{id}")
    public CommonResult updateSort(@PathVariable Long id, @RequestParam Integer sort) {
        int count = service.updateSort(id, sort);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("编辑推荐顺序失败");
    }
    @ApiOperation("编辑专题推荐标志")
    @PostMapping("/update/recommendStatus")
    public CommonResult updateRecommendStatus(@RequestParam List<Long> ids, @RequestParam Integer recommendStatus) {
        int count = service.updateRecommendStatus(ids, recommendStatus);
        if (0 < count) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("编辑专题推荐标志失败");
    }
}
