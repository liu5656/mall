package com.macro.mall.admin.dao;

import com.macro.mall.admin.dto.SmsCouponParam;

/**
 * 商品优惠券自定义管理Dao
 * @version 1.0
 * @Author lj
 * @date 2021/10/13 11:59 上午
 */
public interface SmsCouponDao {
    /**
     * 优惠券详情
     */
    SmsCouponParam getItem(Long id);
}
