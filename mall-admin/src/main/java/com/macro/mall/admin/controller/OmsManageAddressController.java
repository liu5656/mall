package com.macro.mall.admin.controller;

import com.macro.mall.admin.service.OmsManageAddressService;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.OmsCompanyAddress;
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
 * @date 2021/10/14 4:40 下午
 * @desc
 */
@Api(tags = "OmsMessageAddressController", description = "收货地址管理")
@RestController
@RequestMapping("/companyAddress")
public class OmsManageAddressController {

    @Autowired private OmsManageAddressService service;

    @ApiOperation("获取所有收货地址")
    @GetMapping("/list")
    public CommonResult<List<OmsCompanyAddress>> listAll() {
        List<OmsCompanyAddress> addresses = service.listAll();
        return CommonResult.success(addresses);
    }

}
