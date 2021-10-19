package com.macro.mall.admin.service;

import com.macro.mall.admin.dto.*;
import com.macro.mall.model.OmsOrder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单管理service
 * @version 1.0
 * @Author lj
 * @date 2021/10/14 4:47 下午
 */
public interface OmsOrderService {

    /**
     * 批量删除订单
     */
    int delete(List<Long> ids);

    /**
     * 批量发货
     */
    @Transactional
    int delivery(List<OmsOrderDeliveryParam> params);
    /**
     * 批量关闭订单
     */
    @Transactional
    int close(List<Long> ids, String note);
    /**
     * 修改订单收货人信息
     */
    @Transactional
    int updateReceiverInfo(OmsReceiverInfoParam receiver);
    /**
     * 修改订单费用
     */
    @Transactional
    int updateMoneyInfo(OmsMoneyInfoParam money);
    /**
     * 修改订单备注
     */
    @Transactional
    int updateNote(Long id, String note, Integer status);

    /**
     * 获取订单详情
     */
    OmsOrderDetail getItem(Long id);
    /**
     * 订单查询
     */
    List<OmsOrder> list(OmsOrderQueryParam param, Integer pageNum, Integer pageSize);
}
