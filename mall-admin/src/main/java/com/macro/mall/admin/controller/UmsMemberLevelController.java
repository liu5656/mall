package com.macro.mall.admin.controller;

import com.macro.mall.admin.service.UmsMemberLevelService;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.UmsMemberLevel;
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
 * @date 2021/9/27 2:52 下午
 * @desc
 */
@Api(tags = "UmsMemberLevelController", description = "会员等级管理")
@RestController
@RequestMapping("/memberLevel")
public class UmsMemberLevelController {

    @Autowired private UmsMemberLevelService memberLevelService;

    @ApiOperation("查询所有会员等级")
    @GetMapping("/list")
    public CommonResult<List<UmsMemberLevel>> list(@RequestParam("defaultStatus") Integer defaultStatus) {
        List<UmsMemberLevel> levels = memberLevelService.list(defaultStatus);
        return CommonResult.success(levels);
    }

}
