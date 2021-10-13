package com.macro.mall.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.macro.mall.admin.service.SmsCouponHistoryService;
import com.macro.mall.mapper.SmsCouponHistoryMapper;
import com.macro.mall.model.SmsCouponHistory;
import com.macro.mall.model.SmsCouponHistoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 优惠券领取记录管理Service实现
 * @version 1.0
 * @Author lj
 * @date 2021/10/13 10:19 上午
 * @desc
 */
@Service
public class SmsCOuponHistoryServiceImpl implements SmsCouponHistoryService {

    @Autowired private SmsCouponHistoryMapper couponHistoryMapper;

    @Override
    public List<SmsCouponHistory> list(Long couponId, Integer useStatus, String orderSn, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        SmsCouponHistoryExample example = new SmsCouponHistoryExample();
        SmsCouponHistoryExample.Criteria criteria = example.createCriteria();
        if (couponId != null) {
            criteria.andCouponIdEqualTo(couponId);
        }
        if (useStatus != null) {
            criteria.andUseStatusEqualTo(useStatus);
        }
        if (StringUtil.isNotEmpty(orderSn)) {
            criteria.andOrderSnEqualTo(orderSn);
        }
        return couponHistoryMapper.selectByExample(example);
    }
}
