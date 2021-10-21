package com.macro.mall.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.macro.mall.admin.dto.SmsFlashPromotionSessionDetail;
import com.macro.mall.admin.service.SmsFlashPromotionProductRelationService;
import com.macro.mall.admin.service.SmsFlashPromotionSessionService;
import com.macro.mall.mapper.SmsFlashPromotionSessionMapper;
import com.macro.mall.model.SmsFlashPromotionSession;
import com.macro.mall.model.SmsFlashPromotionSessionExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 限时购场次管理Service实现类
 * @version 1.0
 * @Author lj
 * @date 2021/10/19 5:05 下午
 * @desc
 */
@Service
public class SmsFlashPromotionSessionServiceImpl implements SmsFlashPromotionSessionService {

    @Autowired private SmsFlashPromotionSessionMapper sessionMapper;
    @Autowired private SmsFlashPromotionProductRelationService relationService;

    @Override
    public int create(SmsFlashPromotionSession session) {
        session.setCreateTime(new Date());
        return sessionMapper.insertSelective(session);
    }

    @Override
    public int delete(Long id) {
        return sessionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        SmsFlashPromotionSession session = new SmsFlashPromotionSession();
        session.setId(id);
        session.setStatus(status);
        return sessionMapper.updateByPrimaryKeySelective(session);
    }
    @Override
    public int update(Long id, SmsFlashPromotionSession session) {
        session.setId(id);
        return sessionMapper.updateByPrimaryKeySelective(session);
    }

    @Override
    public SmsFlashPromotionSession getItem(Long id) {
        return sessionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SmsFlashPromotionSessionDetail> list(Long flashPromotionId) {
        SmsFlashPromotionSessionExample example = new SmsFlashPromotionSessionExample();
        example.createCriteria().andStatusEqualTo(1);
        List<SmsFlashPromotionSessionDetail> result = sessionMapper.selectByExample(example).stream().map(item -> {
            SmsFlashPromotionSessionDetail detail = new SmsFlashPromotionSessionDetail();
            BeanUtil.copyProperties(item, detail);
            detail.setProductCount(relationService.getCount(flashPromotionId, item.getId()));
            return detail;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<SmsFlashPromotionSession> listall() {
        return sessionMapper.selectByExample(new SmsFlashPromotionSessionExample());
    }
}
