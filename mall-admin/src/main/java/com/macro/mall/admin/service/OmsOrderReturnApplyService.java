package com.macro.mall.admin.service;

import com.macro.mall.admin.dto.OmsReturnApplyQueryParam;
import com.macro.mall.admin.dto.OmsReturnApplyResult;
import com.macro.mall.admin.dto.OmsUpdateStatusParam;
import com.macro.mall.model.OmsOrderReturnApply;

import java.util.List;

/**
 * 订单退货申请管理Service
 * @version 1.0
 * @Author lj
 * @date 2021/10/19 11:35 上午
 */
public interface OmsOrderReturnApplyService {


    /**
     * 批量删除申请
     */
    int delete(List<Long> ids);

    /**
     * 修改申请状态
     */
    int updateStatus(Long id, OmsUpdateStatusParam param);

    /**
     * 获取指定申请详情
     */
    OmsReturnApplyResult getItem(Long id);
    /**
     * 分页查询管理
     */
    List<OmsOrderReturnApply> list(OmsReturnApplyQueryParam param, Integer pageNum, Integer pageSize);
}
