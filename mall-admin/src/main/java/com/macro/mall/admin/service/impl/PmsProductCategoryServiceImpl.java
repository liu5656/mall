package com.macro.mall.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.macro.mall.admin.dao.PmsProductCategoryAttributeRelationDao;
import com.macro.mall.admin.dao.PmsProductCategoryDao;
import com.macro.mall.admin.dto.PmsProductCategoryParam;
import com.macro.mall.admin.dto.PmsProductCategoryWithChildrenItem;
import com.macro.mall.admin.service.PmsProductCategoryService;
import com.macro.mall.mapper.PmsProductCategoryAttributeRelationMapper;
import com.macro.mall.mapper.PmsProductCategoryMapper;
import com.macro.mall.mapper.PmsProductMapper;
import com.macro.mall.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品分类管理service实现类
 * @version 1.0
 * @Author lj
 * @date 2021/10/11 11:44 上午
 * @desc
 */
@Service
public class PmsProductCategoryServiceImpl implements PmsProductCategoryService {

    @Autowired private PmsProductCategoryMapper categoryMapper;
    @Autowired private PmsProductMapper productMapper;
    @Autowired private PmsProductCategoryAttributeRelationDao relationDao;
    @Autowired private PmsProductCategoryAttributeRelationMapper relationMapper;
    @Autowired private PmsProductCategoryDao categoryDao;

    @Override
    public int create(PmsProductCategoryParam param) {
        PmsProductCategory category = new PmsProductCategory();
        BeanUtil.copyProperties(param, category);
        category.setProductCount(0);
        calculateLevel(category);
        int count = categoryMapper.insertSelective(category);

        List<Long> attributeIds = param.getProductAttributeIdList();
        if (CollUtil.isNotEmpty(attributeIds)) {
            insertProductCategoryAttributeList(attributeIds, category.getId());
        }
        return count;
    }

    @Override
    public int delete(Long id) {
        int count = categoryMapper.deleteByPrimaryKey(id);
        if (0 < count) {
            PmsProductCategoryAttributeRelationExample example = new PmsProductCategoryAttributeRelationExample();
            example.createCriteria().andProductCategoryIdEqualTo(id);
            relationMapper.deleteByExample(example);
        }
        return count;
    }

    @Override
    public PmsProductCategory getItem(Long id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<PmsProductCategory> getList(Long parentId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductCategoryExample example = new PmsProductCategoryExample();
        example.setOrderByClause("sort desc");
        example.createCriteria().andParentIdEqualTo(parentId);
        return categoryMapper.selectByExample(example);
    }

    @Override
    public List<PmsProductCategoryWithChildrenItem> listWithChildren() {
        return categoryDao.listWithChildren();
    }

    @Override
    public int update(Long id, PmsProductCategoryParam param) {
        PmsProductCategory category = new PmsProductCategory();
        category.setId(id);
        BeanUtil.copyProperties(param, category);
        calculateLevel(category);

        // 更新商品中的名称
        PmsProduct product = new PmsProduct();
        product.setProductCategoryName(category.getName());
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andProductCategoryIdEqualTo(category.getId());
        productMapper.updateByExampleSelective(product, example);

        // 更新筛选属性内的信息
        List<Long> attributeIds = param.getProductAttributeIdList();
        PmsProductCategoryAttributeRelationExample relationExample = new PmsProductCategoryAttributeRelationExample();
        relationExample.createCriteria().andProductCategoryIdEqualTo(id);
        relationMapper.deleteByExample(relationExample);
        if (CollUtil.isNotEmpty(attributeIds)) {
            insertProductCategoryAttributeList(attributeIds, id);
        }

        return categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public int updateNavStatus(List<Long> ids, Integer status) {
        PmsProductCategory category = new PmsProductCategory();
        category.setNavStatus(status);
        PmsProductCategoryExample example = new PmsProductCategoryExample();
        example.createCriteria().andIdIn(ids);
        return categoryMapper.updateByExampleSelective(category, example);
    }

    @Override
    public int updateShowStatus(List<Long> ids, Integer status) {
        PmsProductCategory category = new PmsProductCategory();
        category.setShowStatus(status);
        PmsProductCategoryExample example = new PmsProductCategoryExample();
        example.createCriteria().andIdIn(ids);
        return categoryMapper.updateByExampleSelective(category, example);
    }

    /**
     * 根据parentId来设置分类的level
     * @param category
     */
    private void calculateLevel(PmsProductCategory category) {
        Long parentId = category.getParentId();
        if (0 == parentId) {
            category.setLevel(0);
        }else{
            PmsProductCategory parent = categoryMapper.selectByPrimaryKey(parentId);
            if (parent != null) {
                category.setLevel(parent.getLevel() + 1);
            }else {
                category.setLevel(0);
            }
        }
    }

    /**
     * 批量插入商品分类与筛选属性关系表
     */
    private void insertProductCategoryAttributeList(List<Long> attributeIds, Long categoryId) {
        List<PmsProductCategoryAttributeRelation> relations = attributeIds.stream().map( aid -> {
            PmsProductCategoryAttributeRelation relation = new PmsProductCategoryAttributeRelation();
            relation.setProductCategoryId(categoryId);
            relation.setProductAttributeId(aid);
            return relation;
        }).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(relations)) {
            relationDao.insertList(relations);
        }
    }
}
