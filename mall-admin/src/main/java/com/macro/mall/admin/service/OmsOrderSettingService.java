package com.macro.mall.admin.service;

import com.macro.mall.model.OmsOrderSetting;

/**
 * 订单设置管理Service
 * @version 1.0
 * @Author lj
 * @date 2021/10/19 4:34 下午
 */
public interface OmsOrderSettingService {

    /**
     * 获取指定订单设置
     */
    OmsOrderSetting getItem(Long id);

    /**
     * 修改指定订单设置
     */
    int update(Long id, OmsOrderSetting setting);
}
