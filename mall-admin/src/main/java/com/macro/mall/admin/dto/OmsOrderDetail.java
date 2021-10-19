package com.macro.mall.admin.dto;

import com.macro.mall.model.OmsOrder;
import com.macro.mall.model.OmsOrderItem;
import com.macro.mall.model.OmsOrderOperateHistory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 订单详情
 * @version 1.0
 * @Author lj
 * @date 2021/10/14 5:17 下午
 * @desc
 */
@Data
public class OmsOrderDetail extends OmsOrder {

    @ApiModelProperty("订单商品列表")
    private List<OmsOrderItem> orderItemList;

    @ApiModelProperty("订单操作记录表")
    private List<OmsOrderOperateHistory> historyList;
}
