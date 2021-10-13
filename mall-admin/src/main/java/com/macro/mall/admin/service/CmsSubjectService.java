package com.macro.mall.admin.service;

import com.macro.mall.model.CmsSubject;

import java.util.List;

/**
 * 商品专题管理Service
 * @version 1.0
 * @Author lj
 * @date 2021/10/13 9:36 上午
 */
public interface CmsSubjectService {

    /**
     * 查询所有专题
     */
    List<CmsSubject> listAll();

    /**
     * 分页查询专题
     */
    List<CmsSubject> list(String keyword, Integer pageNum, Integer pageSize);

}
