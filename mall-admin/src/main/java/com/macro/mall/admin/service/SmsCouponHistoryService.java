package com.macro.mall.admin.service;

import com.macro.mall.model.SmsCouponHistory;

import java.util.List;

/**
 * 优惠券领取记录管理service
 * @version 1.0
 * @Author lj
 * @date 2021/10/13 10:16 上午
 */
public interface SmsCouponHistoryService {
    /**
     * 分页查询优惠券领取记录
     * @param couponId  优惠券id
     * @param useStatus 使用状态
     * @param orderSn   使用订单号
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<SmsCouponHistory> list(Long couponId, Integer useStatus, String orderSn, Integer pageNum, Integer pageSize);

}
