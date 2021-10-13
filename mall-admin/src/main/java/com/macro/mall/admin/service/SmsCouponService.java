package com.macro.mall.admin.service;

import com.macro.mall.admin.dto.SmsCouponParam;
import com.macro.mall.model.SmsCoupon;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品优惠券管理Service
 * @version 1.0
 * @Author lj
 * @date 2021/10/13 10:48 上午
 */
public interface SmsCouponService {
    /**
     * 添加优惠券
     */
    @Transactional
    int create(SmsCouponParam coupon);

    /**
     * 删除优惠券
     */
    int delete(Long id);

    /**
     * 获取优惠券详情
     */
    SmsCouponParam getItem(Long id);
    /**
     * 分页获取优惠券
     */
    List<SmsCoupon> list(String name, Integer type, Integer pageNum, Integer pageSize);

    /**
     * 更新优惠券
     */
    @Transactional
    int update(Long id, SmsCouponParam coupon);
}
