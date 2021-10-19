package com.macro.mall.admin.dao;

import com.macro.mall.admin.dto.OmsOrderDeliveryParam;
import com.macro.mall.admin.dto.OmsOrderDetail;
import com.macro.mall.admin.dto.OmsOrderQueryParam;
import com.macro.mall.model.OmsOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自定义订单查询Dao
 * @version 1.0
 * @Author lj
 * @date 2021/10/14 5:38 下午
 */
public interface OmsOrderDao {

    /**
     * 条件查询订单
     */
    List<OmsOrder> getList(@Param("queryParam") OmsOrderQueryParam queryParam);

    /**
     * 批量发货
     */
    int delivery(@Param("list") List<OmsOrderDeliveryParam> deliveryParamList);

    /**
     * 查询订单详情
     */
    OmsOrderDetail getDetail(@Param("id") Long id);
}
