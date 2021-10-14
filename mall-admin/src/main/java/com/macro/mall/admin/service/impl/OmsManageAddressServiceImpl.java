package com.macro.mall.admin.service.impl;

import com.macro.mall.admin.service.OmsManageAddressService;
import com.macro.mall.mapper.OmsCompanyAddressMapper;
import com.macro.mall.model.OmsCompanyAddress;
import com.macro.mall.model.OmsCompanyAddressExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收货地址管理Service实现类
 * @version 1.0
 * @Author lj
 * @date 2021/10/14 4:16 下午
 * @desc
 */
@Service
public class OmsManageAddressServiceImpl implements OmsManageAddressService {

    @Autowired private OmsCompanyAddressMapper mapper;

    @Override
    public List<OmsCompanyAddress> listAll() {
        return mapper.selectByExample(new OmsCompanyAddressExample());
    }
}
