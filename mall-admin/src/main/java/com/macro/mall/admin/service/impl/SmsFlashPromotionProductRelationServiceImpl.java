package com.macro.mall.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.admin.dao.SmsFlashPromotionProductRelationDao;
import com.macro.mall.admin.dto.SmsFlashPromotionProduct;
import com.macro.mall.admin.service.SmsFlashPromotionProductRelationService;
import com.macro.mall.mapper.SmsFlashPromotionProductRelationMapper;
import com.macro.mall.model.SmsFlashPromotionProductRelation;
import com.macro.mall.model.SmsFlashPromotionProductRelationExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 限时购和商品关联管理service实现
 * @version 1.0
 * @Author lj
 * @date 2021/10/13 4:56 下午
 * @desc
 */
@Service
public class SmsFlashPromotionProductRelationServiceImpl implements SmsFlashPromotionProductRelationService {

    @Autowired private SmsFlashPromotionProductRelationDao relationDao;
    @Autowired private SmsFlashPromotionProductRelationMapper relationMapper;

    @Override
    public int insertList(List<SmsFlashPromotionProductRelation> relations) {
        relations.forEach(item -> relationMapper.insertSelective(item));
        return relations.size();
    }

    @Override
    public int delete(Long id) {
        return relationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public SmsFlashPromotionProductRelation getItem(Long id) {
        return relationMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SmsFlashPromotionProduct> getList(Long promotionId, Long sessionId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return relationDao.getList(promotionId, sessionId);
    }

    @Override
    public long getCount(Long flashPromotionId, Long flashPromotionSessionId) {
        SmsFlashPromotionProductRelationExample example = new SmsFlashPromotionProductRelationExample();
        example.createCriteria().andFlashPromotionIdEqualTo(flashPromotionId).andFlashPromotionSessionIdEqualTo(flashPromotionSessionId);
        return relationMapper.countByExample(example);
    }

    @Override
    public int update(Long id, SmsFlashPromotionProductRelation relation) {
        relation.setId(id);
        return relationMapper.updateByPrimaryKey(relation);
    }
}
