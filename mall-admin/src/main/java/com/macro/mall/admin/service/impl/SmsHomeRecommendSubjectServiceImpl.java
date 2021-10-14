package com.macro.mall.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.macro.mall.admin.service.SmsHomeRecommendSubjectService;
import com.macro.mall.mapper.SmsHomeRecommendSubjectMapper;
import com.macro.mall.model.SmsHomeRecommendSubject;
import com.macro.mall.model.SmsHomeRecommendSubjectExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/14 3:41 下午
 * @desc
 */
@Service
public class SmsHomeRecommendSubjectServiceImpl implements SmsHomeRecommendSubjectService {

    @Autowired private SmsHomeRecommendSubjectMapper mapper;

    @Override
    public int create(List<SmsHomeRecommendSubject> subjects) {
        subjects.forEach(item -> {
            item.setRecommendStatus(1);
            item.setSort(0);
            mapper.insert(item);
        });
        return subjects.size();
    }

    @Override
    public int delete(List<Long> ids) {
        SmsHomeRecommendSubjectExample example = new SmsHomeRecommendSubjectExample();
        example.createCriteria().andIdIn(ids);
        return mapper.deleteByExample(example);
    }

    @Override
    public List<SmsHomeRecommendSubject> list(String subjectName, Integer recommendStatus, Integer pageNum, Integer pageSize) {
        SmsHomeRecommendSubjectExample example = new SmsHomeRecommendSubjectExample();
        example.setOrderByClause("sort desc");
        SmsHomeRecommendSubjectExample.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmpty(subjectName)) {
            criteria.andSubjectNameLike("%" + subjectName + "%");
        }
        if (recommendStatus != null) {
            criteria.andRecommendStatusEqualTo(recommendStatus);
        }
        PageHelper.startPage(pageNum, pageSize);
        return mapper.selectByExample(example);
    }

    @Override
    public int updateSort(Long id, Integer sort) {
        SmsHomeRecommendSubject subject = new SmsHomeRecommendSubject();
        subject.setId(id);
        subject.setSort(sort);
        return mapper.updateByPrimaryKeySelective(subject);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        SmsHomeRecommendSubjectExample example = new SmsHomeRecommendSubjectExample();
        example.createCriteria().andIdIn(ids);
        SmsHomeRecommendSubject subject = new SmsHomeRecommendSubject();
        subject.setRecommendStatus(recommendStatus);
        return mapper.updateByExampleSelective(subject, example);
    }
}
