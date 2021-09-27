package com.macro.mall.admin.service.impl;

import com.macro.mall.admin.service.UmsResourceCategoryService;
import com.macro.mall.mapper.UmsResourceCategoryMapper;
import com.macro.mall.model.UmsResourceCategory;
import com.macro.mall.model.UmsResourceCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 后台资源分类管理Service实现
 * @version 1.0
 * @Author lj
 * @date 2021/9/27 3:07 下午
 * @desc
 */
@Service
public class UmsResourceCategoryServiceImpl implements UmsResourceCategoryService {

    @Autowired private UmsResourceCategoryMapper categoryMapper;

    @Override
    public List<UmsResourceCategory> listAll() {
        UmsResourceCategoryExample example = new UmsResourceCategoryExample();
        example.setOrderByClause("sort desc");
        return categoryMapper.selectByExample(example);
    }

    @Override
    public int create(UmsResourceCategory category) {
        category.setCreateTime(new Date());
        return categoryMapper.insert(category);
    }

    @Override
    public int update(Long id, UmsResourceCategory category) {
        category.setId(id);
        return categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public int delete(Long id) {
        return categoryMapper.deleteByPrimaryKey(id);
    }
}
