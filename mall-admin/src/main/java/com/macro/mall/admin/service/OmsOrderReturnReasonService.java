package com.macro.mall.admin.service;

import com.macro.mall.model.OmsOrderReturnReason;

import java.util.List;

/**
 * 订单退货原因service
 * @version 1.0
 * @Author lj
 * @date 2021/10/19 3:41 下午
 */
public interface OmsOrderReturnReasonService {

    /**
     * 添加退货原因
     */
    int create(OmsOrderReturnReason returnReason);

    /**
     * 批量删除退货原因
     */
    int delete(List<Long> ids);

    /**
     * 修改退货原因
     */
    int update(Long id, OmsOrderReturnReason returnReason);
    /**
     * 批量修改退货原因及装填
     */
    int updateStatus(List<Long> ids, Integer status);

    /**
     * 分页获取退货原因
     */
    List<OmsOrderReturnReason> list(Integer pageNum, Integer pageSize);
    /**
     * 获取单个退货原因详情信息
     */
    OmsOrderReturnReason getItem(Long id);




}
