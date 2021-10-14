package com.macro.mall.admin.service;

import com.macro.mall.model.SmsHomeRecommendSubject;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 首页专题推荐专题管理Service
 * @version 1.0
 * @Author lj
 * @date 2021/10/14 3:37 下午
 */
public interface SmsHomeRecommendSubjectService {

    @Transactional
    int create(List<SmsHomeRecommendSubject> subjects);

    int delete(List<Long> ids);

    List<SmsHomeRecommendSubject> list(String subjectName, Integer recommendStatus, Integer pageNum, Integer pageSize);

    int updateSort(Long id, Integer sort);
    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

}
