package com.macro.mall.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.macro.mall.admin.dao.SmsCouponDao;
import com.macro.mall.admin.dao.SmsCouponProductCategoryRelationDao;
import com.macro.mall.admin.dao.SmsCouponProductRelationDao;
import com.macro.mall.admin.dto.SmsCouponParam;
import com.macro.mall.admin.service.SmsCouponService;
import com.macro.mall.mapper.SmsCouponMapper;
import com.macro.mall.mapper.SmsCouponProductCategoryRelationMapper;
import com.macro.mall.mapper.SmsCouponProductRelationMapper;
import com.macro.mall.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/13 10:55 上午
 * @desc
 */
@Service
public class SmsCouponServiceImpl implements SmsCouponService {

    @Autowired private SmsCouponMapper couponMapper;
    @Autowired private SmsCouponDao couponDao;

    @Autowired private SmsCouponProductRelationMapper productRelationMapper;
    @Autowired private SmsCouponProductRelationDao productRelationDao;

    @Autowired private SmsCouponProductCategoryRelationMapper categoryRelationMapper;
    @Autowired private SmsCouponProductCategoryRelationDao categoryRelationDao;

    @Override
    public int create(SmsCouponParam coupon) {
        coupon.setCount(coupon.getPublishCount());
        coupon.setUseCount(0);
        coupon.setReceiveCount(0);
        int result = couponMapper.insertSelective(coupon);
        switch (coupon.getUseType()) {
            case 1:     // 优惠券和商品分类关系
                List<SmsCouponProductCategoryRelation> relations = coupon.getCategoryRelations();
                relations.forEach(item -> item.setCouponId(coupon.getId()));
                categoryRelationDao.insertList(relations);
                break;
            case 2:     // 优惠券和商品关系
                List<SmsCouponProductRelation> temp = coupon.getProductRelations();
                temp.forEach(item -> item.setCouponId(coupon.getId()));
                productRelationDao.insertList(temp);
                break;
        }
        return result;
    }

    @Override
    public int delete(Long id) {
        int count = couponMapper.deleteByPrimaryKey(id);
        deleteCouponProductRelation(id);
        deleteCouponProductCategoryRelation(id);
        return count;
    }

    @Override
    public SmsCouponParam getItem(Long id) {
        return couponDao.getItem(id);
    }

    @Override
    public List<SmsCoupon> list(String name, Integer type, Integer pageNum, Integer pageSize) {
        SmsCouponExample example = new SmsCouponExample();
        SmsCouponExample.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        PageHelper.startPage(pageNum, pageSize);
        return couponMapper.selectByExample(example);
    }

    @Override
    public int update(Long id, SmsCouponParam coupon) {
        coupon.setId(id);
        int count = couponMapper.updateByPrimaryKeySelective(coupon);
        switch (coupon.getUseType()) {
            case 1:
                deleteCouponProductCategoryRelation(id);
                coupon.getCategoryRelations().forEach(item -> item.setCouponId(id));
                categoryRelationDao.insertList(coupon.getCategoryRelations());
                break;
            case 2:
                deleteCouponProductRelation(id);
                coupon.getProductRelations().forEach(item -> item.setCouponId(id));
                productRelationDao.insertList(coupon.getProductRelations());
                break;
        }
        return count;
    }

    /**
     * 删除优惠券和商品的关联
     */
    private void deleteCouponProductRelation(Long id) {
        SmsCouponProductRelationExample example = new SmsCouponProductRelationExample();
        example.createCriteria().andCouponIdEqualTo(id);
        productRelationMapper.deleteByExample(example);
    }

    /**
     * 删除优惠券和商品分类的关联
     */
    private void deleteCouponProductCategoryRelation(Long id) {
        SmsCouponProductCategoryRelationExample example = new SmsCouponProductCategoryRelationExample();
        example.createCriteria().andCouponIdEqualTo(id);
        categoryRelationMapper.deleteByExample(example);
    }
}
