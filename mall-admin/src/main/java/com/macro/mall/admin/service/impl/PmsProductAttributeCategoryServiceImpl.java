package com.macro.mall.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.admin.dao.PmsProductAttributeCategoryDao;
import com.macro.mall.admin.dto.PmsProductAttributeCategoryItem;
import com.macro.mall.admin.service.PmsProductAttributeCategoryService;
import com.macro.mall.mapper.PmsProductAttributeCategoryMapper;
import com.macro.mall.model.PmsProductAttributeCategory;
import com.macro.mall.model.PmsProductAttributeCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/9 4:49 下午
 * @desc
 */
@Service
public class PmsProductAttributeCategoryServiceImpl implements PmsProductAttributeCategoryService {

    @Autowired private PmsProductAttributeCategoryDao categoryDao;
    @Autowired private PmsProductAttributeCategoryMapper categoryMapper;

    @Override
    public int create(String name) {
        PmsProductAttributeCategory category = new PmsProductAttributeCategory();
        category.setName(name);
        return categoryMapper.insertSelective(category);
    }

    @Override
    public int update(Long id, String name) {
        PmsProductAttributeCategory category = new PmsProductAttributeCategory();
        category.setName(name);
        category.setId(id);
        return categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public int delete(Long id) {
        return categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PmsProductAttributeCategory getItem(Long id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<PmsProductAttributeCategory> getList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return categoryMapper.selectByExample(new PmsProductAttributeCategoryExample());
    }

    @Override
    public List<PmsProductAttributeCategoryItem> getListWithAttr() {
        return categoryDao.getListWithAttr();
    }
}
