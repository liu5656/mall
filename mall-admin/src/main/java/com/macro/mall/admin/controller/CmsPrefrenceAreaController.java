package com.macro.mall.admin.controller;

import com.macro.mall.admin.service.CmsPreferenceAreaService;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.CmsPrefrenceArea;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/13 9:58 上午
 * @desc
 */
@Api(tags = "CmsPreferenceAreaController", description = "商品优选管理")
@RestController
@RequestMapping("/prefrenceArea")
public class CmsPrefrenceAreaController {

    @Autowired private CmsPreferenceAreaService preferenceAreaService;

    @ApiOperation("获取所有商品优选")
    @GetMapping("/listAll")
    public CommonResult<List<CmsPrefrenceArea>> listAll() {
        List<CmsPrefrenceArea> preferenceAreas = preferenceAreaService.listAll();
        return CommonResult.success(preferenceAreas);
    }

}
