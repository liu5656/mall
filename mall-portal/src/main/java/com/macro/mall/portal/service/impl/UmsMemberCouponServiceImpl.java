package com.macro.mall.portal.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.macro.mall.common.exception.Asserts;
import com.macro.mall.mapper.*;
import com.macro.mall.model.*;
import com.macro.mall.portal.dao.SmsCouponHistoryDao;
import com.macro.mall.portal.domain.CartPromotionItem;
import com.macro.mall.portal.domain.SmsCouponHistoryDetail;
import com.macro.mall.portal.service.UmsMemberCouponService;
import com.macro.mall.portal.service.UmsMemberService;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 会员优惠券管理实现类
 * @version 1.0
 * @Author lj
 * @date 2021/9/30 9:45 上午
 * @desc
 */
@Service
public class UmsMemberCouponServiceImpl implements UmsMemberCouponService {

    @Autowired private UmsMemberService memberService;
    @Autowired private SmsCouponMapper couponMapper;
    @Autowired private SmsCouponHistoryMapper couponHistoryMapper;
    @Autowired private SmsCouponHistoryDao couponHistoryDao;
    @Autowired private SmsCouponProductRelationMapper productRelationMapper;
    @Autowired private SmsCouponProductCategoryRelationMapper productCategoryRelationMapper;
    @Autowired private PmsProductMapper productMapper;

    @Override
    public void add(Long couponId) {
        UmsMember member = memberService.getCurrentMember();
        SmsCoupon coupon = couponMapper.selectByPrimaryKey(couponId);
        if (coupon == null) {
            Asserts.fail("优惠券不存在");
        }
        if (coupon.getCount() <= 0) {
            Asserts.fail("优惠券已经领完了");
        }
        Date now = new Date();
        if (now.before(coupon.getEnableTime())) {
            Asserts.fail("优惠券还没到领取时间");
        }
        // 判断用户的优惠券是否超过限制
        SmsCouponHistoryExample example = new SmsCouponHistoryExample();
        example.createCriteria().andCouponIdEqualTo(couponId).andMemberIdEqualTo(member.getId());
        long count = couponHistoryMapper.countByExample(example);
        if (count >= coupon.getPerLimit()) {
            Asserts.fail("您已经领取过该优惠券");
        }
        addCouponHistory(coupon, member);
    }

    /**
     * 增加领取优惠券记录
     */
    private void addCouponHistory(SmsCoupon coupon, UmsMember member) {
        SmsCouponHistory history = new SmsCouponHistory();
        history.setCouponId(coupon.getId());
        history.setCreateTime(new Date());
        history.setMemberId(member.getId());
        history.setCouponCode(generateCouponCode(member.getId()));
        history.setMemberNickname(member.getNickname());
        history.setGetType(1);                                          // 主动获取
        history.setUseStatus(0);                                        // 未使用
        couponHistoryMapper.insert(history);

        coupon.setCount(coupon.getCount() - 1);
        coupon.setReceiveCount(coupon.getReceiveCount() == null ? 1 : coupon.getReceiveCount() + 1);
        couponMapper.updateByPrimaryKey(coupon);
    }
    /**
     * 16位优惠码生成：时间戳后8位+4位随机数+用户id后4位
     */
    private String generateCouponCode(Long memberId) {
        StringBuilder sb = new StringBuilder();
        Long timestamp = System.currentTimeMillis();
        String timeStampStr = timestamp.toString();
        sb.append(timeStampStr.substring(timeStampStr.length() - 8, timeStampStr.length()));

        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10));
        }
        String memberIdStr = memberId.toString();
        if (memberIdStr.length() < 4) {
            memberIdStr = String.format("%0" + 4 + "d", memberId);
        }
        sb.append(memberIdStr.substring(memberIdStr.length() - 4, memberIdStr.length()));

        return sb.toString();
    }

    /**
     *
     * @param status
     * @return
     */

    @Override
    public List<SmsCouponHistory> listHistory(Integer status) {
        UmsMember member = memberService.getCurrentMember();
        SmsCouponHistoryExample example = new SmsCouponHistoryExample();
        SmsCouponHistoryExample.Criteria criteria = example.createCriteria();
        criteria.andMemberIdEqualTo(member.getId());
        if (status != null) {
            criteria.andUseStatusEqualTo(status);
        }
        return couponHistoryMapper.selectByExample(example);
    }

    @Override
    public List<SmsCouponHistoryDetail> listCart(List<CartPromotionItem> cartItems, Integer type) {
        UmsMember member = memberService.getCurrentMember();
        Date now = new Date();
        List<SmsCouponHistoryDetail> allList = couponHistoryDao.getDetailList(member.getId());
        List<SmsCouponHistoryDetail> enables = new ArrayList<>();
        List<SmsCouponHistoryDetail> disables = new ArrayList<>();
        for (SmsCouponHistoryDetail detail : allList) {                 // 根据优惠券类型来判断优惠券是否可用
            Integer useType = detail.getCoupon().getUseType();
            BigDecimal minPoint = detail.getCoupon().getMinPoint();
            Date endTime = detail.getCoupon().getEndTime();
            BigDecimal total = null;
            if (useType.equals(0)) {                                    // 0->全场通用，优惠券未过期且金额也满足限制的可用，反正不可用。
                total = calculateTotalAmount(cartItems);

            }else if (useType.equals(1)) {                              // 1->指定分类
                List<Long> categoryIds = detail.getProductCategoryRelations().stream()
                        .map( relation -> relation.getProductCategoryId())
                        .collect(Collectors.toList());
                total = calculateTotalByProductCategoryId(cartItems, categoryIds);
            }else if (useType.equals(2)) {                              // 2->指定商品
                List<Long> productIds = detail.getProductRelations().stream().map( item -> item.getProductId()).collect(Collectors.toList());
                total = calculateTotalByProductId(cartItems, productIds);
            }
            if (now.before(endTime) && total.compareTo(minPoint) >= 0) {
                enables.add(detail);
            }else{
                disables.add(detail);
            }
        }
        return 1 == type ? enables : disables;
    }

    @Override
    public List<SmsCoupon> listByProduct(Long productId) {
        List<Long> couponIds = new ArrayList<>();
        // 获取指定商品的优惠券
        SmsCouponProductRelationExample cpr = new SmsCouponProductRelationExample();
        cpr.createCriteria().andProductIdEqualTo(productId);
        List<SmsCouponProductRelation> cprRelations = productRelationMapper.selectByExample(cpr);
        if (CollUtil.isNotEmpty(cprRelations)) {
            couponIds.addAll(cprRelations.stream().map( relation -> relation.getCouponId()).collect(Collectors.toList()));
        }
        // 获取指定商品分类的优惠券
        PmsProduct product = productMapper.selectByPrimaryKey(productId);
        SmsCouponProductCategoryRelationExample cpcr = new SmsCouponProductCategoryRelationExample();
        cpcr.createCriteria().andProductCategoryIdEqualTo(product.getProductCategoryId());
        List<SmsCouponProductCategoryRelation> cpcrRelations = productCategoryRelationMapper.selectByExample(cpcr);
        if (CollUtil.isNotEmpty(cpcrRelations)) {
            couponIds.addAll(cpcrRelations.stream().map( relation -> relation.getCouponId()).collect(Collectors.toList()));
        }
        // 获取商品所有优惠券
        Date now = new Date();
        SmsCouponExample example = new SmsCouponExample();
        SmsCouponExample.Criteria criteria = example.createCriteria();
        criteria.andEndTimeGreaterThan(now)
                .andStartTimeLessThan(now)
                .andUseTypeEqualTo(0);
        example.or(criteria.andEndTimeGreaterThan(now)
                .andStartTimeLessThan(now)
                .andUseTypeNotEqualTo(0)
                .andIdIn(couponIds));
        return couponMapper.selectByExample(example);
    }

    @Override
    public List<SmsCoupon> list(Integer status) {
        UmsMember member = memberService.getCurrentMember();
        return couponHistoryDao.getCouponList(member.getId(), status);
    }
    /**
     * 计算购物车商品总价
     */
    private BigDecimal calculateTotalAmount(List<CartPromotionItem> items) {
        BigDecimal amount = new BigDecimal(0);
        for (CartPromotionItem item : items) {
            BigDecimal realPrice = item.getPrice().subtract(item.getReduceAmount());
            amount = amount.add(realPrice.multiply(new BigDecimal(item.getQuantity())));
        }
        return amount;
    }

    /**
     * 商品分类计算商品总价
     */
    private BigDecimal calculateTotalByProductCategoryId(List<CartPromotionItem> cartItems, List<Long> categoryIds) {
        BigDecimal total = new BigDecimal(0);
        for (CartPromotionItem cartItem : cartItems) {
            if (categoryIds.contains(cartItem.getProductCategoryId())) {
                BigDecimal realPrice = cartItem.getPrice().subtract(cartItem.getReduceAmount());
                total = total.add(realPrice.multiply(new BigDecimal(cartItem.getQuantity())));
            }
        }
        return total;
    }

    /**
     * 指定商品计算总价
     */
    private BigDecimal calculateTotalByProductId(List<CartPromotionItem> cartItems, List<Long> productIds) {
        BigDecimal total = new BigDecimal(0);
        for (CartPromotionItem item : cartItems) {
            if (productIds.contains(item.getProductId())) {
                BigDecimal realPrice = item.getPrice().subtract(item.getReduceAmount());
                total = total.add(realPrice.multiply(new BigDecimal(item.getQuantity())));
            }
        }
        return total;
    }

}
