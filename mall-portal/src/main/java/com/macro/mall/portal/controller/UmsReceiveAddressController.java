package com.macro.mall.portal.controller;

import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.UmsMemberReceiveAddress;
import com.macro.mall.portal.service.UmsReceiveAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/9/28 4:27 下午
 * @desc
 */
@Api(tags = "UmsReceiveAddressController", description = "用户收货地址管理")
@RequestMapping("/member/address")
@RestController
public class UmsReceiveAddressController {

    @Autowired private UmsReceiveAddressService addressService;

    @ApiOperation("增加收货地址")
    @PostMapping("/add")
    public CommonResult add(@RequestBody UmsMemberReceiveAddress address) {
        int count = addressService.add(address);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("增加地址失败");
    }

    @ApiOperation("删除收货地址")
    @DeleteMapping("/delete/{addressId}")
    public CommonResult delete(@PathVariable("addressId") Long id) {
        int count = addressService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("删除收货地址失败");
    }

    @ApiOperation("获取地址详情")
    @GetMapping("/{addressId}")
    public CommonResult getItem(@PathVariable("addressId") Long id) {
        return CommonResult.success(addressService.getItem(id));
    }

    @ApiOperation("获取所有地址")
    @GetMapping("/listAll")
    public CommonResult<List<UmsMemberReceiveAddress>> listAll() {
        return CommonResult.success(addressService.list());
    }

    @ApiOperation("更新收货地址")
    @PostMapping("/update/{addressId}")
    public CommonResult update(@PathVariable("addressId") Long id, @RequestBody UmsMemberReceiveAddress address) {
        int count = addressService.update(id, address);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("更新地址失败");
    }


}
