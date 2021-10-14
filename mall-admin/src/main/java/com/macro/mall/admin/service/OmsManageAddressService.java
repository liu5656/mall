package com.macro.mall.admin.service;

import com.macro.mall.model.OmsCompanyAddress;

import java.util.List;

/**
 * 收货地址管理Service
 * @version 1.0
 * @Author lj
 * @date 2021/10/14 4:14 下午
 */
public interface OmsManageAddressService {

    /**
     * 获取所有收货地址
     */
    List<OmsCompanyAddress> listAll();

}
