package com.macro.mall.admin.service;

import com.macro.mall.model.SmsHomeAdvertise;

import java.util.List;

/**
 * 首页广告管理Service
 * @version 1.0
 * @Author lj
 * @date 2021/10/14 10:20 上午
 */
public interface SmsHomeAdvertiseService {

    /**
     * 添加广告
     */
    int create(SmsHomeAdvertise advertise);

    /**
     * 删除广告
     */
    int delete(List<Long> ids);

    /**
     * 编辑广告上线标志
     */
    int updateStatus(Long id, Integer status);
    /**
     * 编辑广告
     */
    int update(Long id, SmsHomeAdvertise advertise);

    /**
     * 获取广告
     */
    SmsHomeAdvertise getItem(Long id);
    /**
     * 分页查询广告
     */
    List<SmsHomeAdvertise> getList(String name, Integer type, String endTime, Integer pageNum, Integer pageSize);

}
