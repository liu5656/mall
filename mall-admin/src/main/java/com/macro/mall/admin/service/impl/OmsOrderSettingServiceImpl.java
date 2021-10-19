package com.macro.mall.admin.service.impl;

import com.macro.mall.admin.service.OmsOrderSettingService;
import com.macro.mall.mapper.OmsOrderSettingMapper;
import com.macro.mall.model.OmsOrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单设置管理Service实现类
 * @version 1.0
 * @Author lj
 * @date 2021/10/19 4:35 下午
 * @desc
 */
@Service
public class OmsOrderSettingServiceImpl implements OmsOrderSettingService {

    @Autowired private OmsOrderSettingMapper settingMapper;

    @Override
    public OmsOrderSetting getItem(Long id) {
        return settingMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Long id, OmsOrderSetting setting) {
        setting.setId(id);
        return settingMapper.updateByPrimaryKeySelective(setting);
    }
}
