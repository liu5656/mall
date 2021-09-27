package com.macro.mall.admin.service;

import com.macro.mall.model.UmsMemberLevel;

import java.util.List;

/**
 * 会员等级管理Service
 * @version 1.0
 * @Author lj
 * @date 2021/9/27 2:43 下午
 */
public interface UmsMemberLevelService {
    /**
     * 获取所有会员等级
     */
    List<UmsMemberLevel> list(Integer defaultStatus);
}
