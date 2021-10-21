package com.macro.mall.portal.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.mapper.*;
import com.macro.mall.model.*;
import com.macro.mall.portal.dao.HomeDao;
import com.macro.mall.portal.domain.FlashPromotionProduct;
import com.macro.mall.portal.domain.HomeContentResult;
import com.macro.mall.portal.domain.HomeFlashPromotion;
import com.macro.mall.portal.service.HomeService;
import com.macro.mall.portal.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 首页内容管理Service实现
 * @version 1.0
 * @Author lj
 * @date 2021/10/21 11:23 上午
 * @desc
 */
@Service
public class HomeServiceImpl implements HomeService {

    @Autowired private SmsHomeAdvertiseMapper advertiseMapper;
    @Autowired private HomeDao homeDao;
    @Autowired private SmsFlashPromotionMapper promotionMapper;
    @Autowired private SmsFlashPromotionSessionMapper promotionSessionMapper;
    @Autowired private PmsProductMapper productMapper;
    @Autowired private PmsProductCategoryMapper productCategoryMapper;
    @Autowired private CmsSubjectMapper subjectMapper;


    @Override
    public HomeContentResult content() {
        HomeContentResult result = new HomeContentResult();
        result.setAdvertiseList(getHomeAdvertiseList());
        result.setBrandList(homeDao.getRecommendBrandList(0,6));
        result.setHomeFlashPromotion(getHomeFlashPromotion());
        result.setNewProductList(homeDao.getNewProductList(0, 4));
        result.setHotProductList(homeDao.getHotProductList(0, 4));
        result.setSubjectList(homeDao.getRecommendSubjectList(0, 4));
        return result;
    }

    @Override
    public List<PmsProduct> recommendProductList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andPublishStatusEqualTo(1).andDeleteStatusEqualTo(0);
        return productMapper.selectByExample(example);
    }

    @Override
    public List<PmsProductCategory> getProductCategoryList(Long parentId) {
        PmsProductCategoryExample example = new PmsProductCategoryExample();
        example.setOrderByClause("sort desc");
        example.createCriteria().andParentIdEqualTo(parentId).andShowStatusEqualTo(1);
        return productCategoryMapper.selectByExample(example);
    }

    @Override
    public List<CmsSubject> getSubjectList(Long cateId, Integer pageNum, Integer pageSize) {
        CmsSubjectExample example = new CmsSubjectExample();
        CmsSubjectExample.Criteria criteria = example.createCriteria();
//        criteria.andShowStatusEqualTo(1);
//        if (cateId != null) {
//            criteria.andCategoryIdEqualTo(cateId);
//        }
        return subjectMapper.selectByExample(example);
    }

    @Override
    public List<PmsProduct> hotProductList(Integer pageNum, Integer pageSize) {
        return homeDao.getHotProductList(pageSize * (pageNum - 1), pageSize);
    }

    @Override
    public List<PmsProduct> newProductList(Integer pageNum, Integer pageSize) {
        return homeDao.getNewProductList(pageSize * (pageNum - 1), pageSize);
    }

    /**
     * 获取首页轮播广告
     */
    private List<SmsHomeAdvertise> getHomeAdvertiseList() {
        SmsHomeAdvertiseExample example = new SmsHomeAdvertiseExample();
        example.setOrderByClause("sort desc");
        example.createCriteria().andTypeEqualTo(1).andStatusEqualTo(1);
        return advertiseMapper.selectByExample(example);
    }

    /**
     * 根据时间获取秒杀活动
     */
    private SmsFlashPromotion getFlashPromotion(Date date) {
        Date currTime = DateUtil.getTime(date);
        SmsFlashPromotionExample example = new SmsFlashPromotionExample();
        example.createCriteria()
                .andStartDateLessThanOrEqualTo(date)
                .andEndDateGreaterThanOrEqualTo(date)
                .andStatusEqualTo(1);
        List<SmsFlashPromotion> promotions = promotionMapper.selectByExample(example);
        if (promotions.size() > 0) {
            return promotions.get(0);
        }
        return null;
    }
    /**
     * 根据时间获取秒杀活动场次
     */
    private SmsFlashPromotionSession getFlashPromotionSession(Date date) {
        SmsFlashPromotionSessionExample example = new SmsFlashPromotionSessionExample();
        example.setOrderByClause("start_time asc");
        example.createCriteria().andStartTimeLessThanOrEqualTo(date);
        List<SmsFlashPromotionSession> sessions = promotionSessionMapper.selectByExample(example);
        if (sessions.size() > 0) {
            return sessions.get(0);
        }
        return null;
    }
    /**
     * 根据时间获取下一个场次秒杀活动场次
     */
    private SmsFlashPromotionSession getNextFlashPromotionSession(Date date) {
        SmsFlashPromotionSessionExample example = new SmsFlashPromotionSessionExample();
        example.setOrderByClause("start_time asc");
        example.createCriteria().andStartTimeGreaterThan(date);
        List<SmsFlashPromotionSession> sessions = promotionSessionMapper.selectByExample(example);
        if (sessions.size() > 0) {
            return sessions.get(0);
        }
        return null;
    }

    /**
     * 根据时间获取首页秒杀活动场次
     */
    private HomeFlashPromotion getHomeFlashPromotion() {
        HomeFlashPromotion result = new HomeFlashPromotion();
        Date now = new Date();
        SmsFlashPromotion promotion = getFlashPromotion(now);
        SmsFlashPromotionSession session = getFlashPromotionSession(now);
        if (promotion != null && session != null) {
            result.setStartTime(session.getStartTime());
            result.setEndTime(session.getEndTime());
            SmsFlashPromotionSession next = getNextFlashPromotionSession(session.getStartTime());
            if (next != null) {
                result.setNextStartTime(next.getStartTime());
                result.setNextEndTime(next.getEndTime());
            }
            List<FlashPromotionProduct> products = homeDao.getFlashProductList(promotion.getId(), session.getId());
            result.setProductList(products);
        }
        return result;
    }



}
