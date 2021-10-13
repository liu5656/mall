package com.macro.mall.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.macro.mall.admin.service.SmsFlashPromotionService;
import com.macro.mall.mapper.SmsFlashPromotionMapper;
import com.macro.mall.model.SmsFlashPromotion;
import com.macro.mall.model.SmsFlashPromotionExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 限时购活动管理service实现类
 * @version 1.0
 * @Author lj
 * @date 2021/10/13 3:36 下午
 * @desc
 */
@Service
public class SmsFlashPromotionServiceImpl implements SmsFlashPromotionService {

    @Autowired private SmsFlashPromotionMapper flashPromotionMapper;

    @Override
    public int create(SmsFlashPromotion promotion) {
        promotion.setCreateTime(new Date());
        int count = flashPromotionMapper.insertSelective(promotion);
        return count;
    }

    @Override
    public int delete(Long id) {
        return flashPromotionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public SmsFlashPromotion getItem(Long id) {
        return flashPromotionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SmsFlashPromotion> list(String keyword, Integer pageNum, Integer pageSize) {
        SmsFlashPromotionExample example = new SmsFlashPromotionExample();
        if (StringUtil.isNotEmpty(keyword)) {
            example.createCriteria().andTitleLike("%" + keyword + "%");
        }
        PageHelper.startPage(pageNum, pageSize);
        return flashPromotionMapper.selectByExample(example);
    }

    @Override
    public int update(Long id, SmsFlashPromotion promotion) {
        promotion.setId(id);
        return flashPromotionMapper.updateByPrimaryKeySelective(promotion);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        SmsFlashPromotion promotion = new SmsFlashPromotion();
        promotion.setId(id);
        promotion.setStatus(status);
        return flashPromotionMapper.updateByPrimaryKeySelective(promotion);
    }
}
