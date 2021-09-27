package com.macro.mall.admin.service.impl;

import com.macro.mall.admin.service.UmsMemberLevelService;
import com.macro.mall.mapper.UmsMemberLevelMapper;
import com.macro.mall.mapper.UmsMemberMapper;
import com.macro.mall.model.UmsMemberLevel;
import com.macro.mall.model.UmsMemberLevelExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会员等级管理service实现类
 * @version 1.0
 * @Author lj
 * @date 2021/9/27 2:46 下午
 * @desc
 */
@Service
public class UmsMemeberLevelServiceImpl implements UmsMemberLevelService {

    @Autowired private UmsMemberLevelMapper memberLevelMapper;

    @Override
    public List<UmsMemberLevel> list(Integer defaultStatus) {
        UmsMemberLevelExample example = new UmsMemberLevelExample();
        example.createCriteria().andDefaultStatusEqualTo(defaultStatus);
        return memberLevelMapper.selectByExample(example);
    }
}
