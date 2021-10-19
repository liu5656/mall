package com.macro.mall.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.macro.mall.admin.dao.OmsOrderDao;
import com.macro.mall.admin.dao.OmsOrderOperateHistoryDao;
import com.macro.mall.admin.dto.*;
import com.macro.mall.admin.service.OmsOrderService;
import com.macro.mall.mapper.OmsOrderMapper;
import com.macro.mall.mapper.OmsOrderOperateHistoryMapper;
import com.macro.mall.model.OmsOrder;
import com.macro.mall.model.OmsOrderExample;
import com.macro.mall.model.OmsOrderOperateHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/14 5:36 下午
 * @desc
 */
@Service
public class OmsOrderServiceImpl implements OmsOrderService {

    @Autowired private OmsOrderMapper orderMapper;
    @Autowired private OmsOrderDao orderDao;
    @Autowired private OmsOrderOperateHistoryMapper historyMapper;
    @Autowired private OmsOrderOperateHistoryDao historyDao;

    //TODO dao相关

    @Override
    public int delete(List<Long> ids) {
        OmsOrder order = new OmsOrder();
        order.setDeleteStatus(1);

        OmsOrderExample example = new OmsOrderExample();
        example.createCriteria().andDeleteStatusEqualTo(0).andIdIn(ids);
        return orderMapper.updateByExampleSelective(order, example);
    }

    @Override
    public int delivery(List<OmsOrderDeliveryParam> params) {
        int count = orderDao.delivery(params);
        // 添加操作记录
        List<Long> ids = params.stream().map(OmsOrderDeliveryParam::getOrderId).collect(Collectors.toList());
        insertOperateHistory(ids,2, "后台管理员","完成发货");
        return count;
    }

    @Override
    public int close(List<Long> ids, String note) {
        OmsOrder order = new OmsOrder();
        order.setStatus(4);
        order.setNote(note);
        OmsOrderExample example = new OmsOrderExample();
        example.createCriteria().andDeleteStatusEqualTo(0).andIdIn(ids);
        int count = orderMapper.updateByExampleSelective(order, example);
        insertOperateHistory(ids, 4, "后台管理员", "订单关闭：" + note);
        return count;
    }

    @Override
    public int updateReceiverInfo(OmsReceiverInfoParam receiver) {
        OmsOrder order = new OmsOrder();
        BeanUtil.copyProperties(receiver, order);
        order.setId(receiver.getOrderId());
        order.setModifyTime(new Date());
        int count = orderMapper.updateByPrimaryKeySelective(order);
        // 插入操作记录
        List<Long> orderIds = new ArrayList<>();
        orderIds.add(receiver.getOrderId());
        insertOperateHistory(orderIds, receiver.getStatus(), "后台管理员", "修改收货地址");
        return count;
    }

    @Override
    public int updateMoneyInfo(OmsMoneyInfoParam money) {
        OmsOrder order = new OmsOrder();
        BeanUtil.copyProperties(money, order);
        order.setId(money.getOrderId());
        order.setCreateTime(new Date());
        int count = orderMapper.updateByPrimaryKeySelective(order);
        List<Long> orderIds = new ArrayList<>();
        orderIds.add(money.getOrderId());
        insertOperateHistory(orderIds, money.getStatus(), "后台管理员","修改费用信息");
        return count;
    }

    @Override
    public int updateNote(Long id, String note, Integer status) {
        OmsOrder order = new OmsOrder();
        order.setId(id);
        order.setNote(note);
        order.setModifyTime(new Date());
        int count = orderMapper.updateByPrimaryKeySelective(order);
        List<Long> orderIds = new ArrayList<>();
        orderIds.add(id);
        insertOperateHistory(orderIds, status, "后台管理员", "修改备注信息：" + note );
        return count;
    }

    @Override
    public OmsOrderDetail getItem(Long id) {
        return orderDao.getDetail(id);
    }

    @Override
    public List<OmsOrder> list(OmsOrderQueryParam param, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return orderDao.getList(param);
    }

    /**
     * 批量插入操作记录
     */
    void insertOperateHistory(List<Long> orderIds, Integer status, String operateMan, String note) {
        List<OmsOrderOperateHistory> histories = orderIds.stream().map( orderId -> {
            OmsOrderOperateHistory history = new OmsOrderOperateHistory();
            history.setOrderId(orderId);
            history.setCreateTime(new Date());
            history.setOrderStatus(2);
            history.setOperateMan(operateMan);
            history.setNote(note);
            return history;
        }).collect(Collectors.toList());
        historyDao.insertList(histories);
    }
}
