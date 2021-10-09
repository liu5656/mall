package com.macro.mall.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.macro.mall.admin.dao.PmsProductAttributeDao;
import com.macro.mall.admin.dto.PmsProductAttrInfo;
import com.macro.mall.admin.dto.PmsProductAttributeParam;
import com.macro.mall.admin.service.PmsProductAttributeService;
import com.macro.mall.mapper.PmsProductAttributeCategoryMapper;
import com.macro.mall.mapper.PmsProductAttributeMapper;
import com.macro.mall.model.PmsProductAttribute;
import com.macro.mall.model.PmsProductAttributeCategory;
import com.macro.mall.model.PmsProductAttributeExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Max;
import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/9 2:00 下午
 * @desc
 */
@Service
public class PmsProductAttributeServiceImpl implements PmsProductAttributeService {

    @Autowired private PmsProductAttributeMapper attributeMapper;
    @Autowired private PmsProductAttributeCategoryMapper attributeCategoryMapper;
    @Autowired private PmsProductAttributeDao attributeDao;

    @Override
    public List<PmsProductAttribute> getList(Long cid, Integer type, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductAttributeExample example = new PmsProductAttributeExample();
        example.setOrderByClause("sort desc");
        example.createCriteria().andProductAttributeCategoryIdEqualTo(cid).andTypeEqualTo(type);
        return attributeMapper.selectByExample(example);
    }

    @Override
    public int create(PmsProductAttributeParam param) {
        PmsProductAttribute attribute = new PmsProductAttribute();
        BeanUtil.copyProperties(param, attribute);
        attributeMapper.insertSelective(attribute);

        // 新增商品属性后需要更新商品属性分类数量
        PmsProductAttributeCategory attributeCategory = attributeCategoryMapper.selectByPrimaryKey(param.getProductAttributeCategoryId());
        if (attribute.getType() == 0) {             // 规格
            attributeCategory.setAttributeCount(attributeCategory.getAttributeCount() + 1);
        }else if (1 == attribute.getType()) {       // 参数
            attributeCategory.setParamCount(attributeCategory.getParamCount() + 1);
        }
        return attributeCategoryMapper.updateByPrimaryKeySelective(attributeCategory);
    }

    @Override
    public int update(Long id, PmsProductAttributeParam param) {
        PmsProductAttribute attribute = new PmsProductAttribute();
        BeanUtil.copyProperties(param, attribute);
        attribute.setId(id);
        return attributeMapper.updateByPrimaryKeySelective(attribute);
    }

    @Override
    public PmsProductAttribute getItem(Long id) {
        return attributeMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(List<Long> ids) {
        PmsProductAttribute attribute = attributeMapper.selectByPrimaryKey(ids.get(0));
        PmsProductAttributeCategory category = attributeCategoryMapper.selectByPrimaryKey(attribute.getProductAttributeCategoryId());
        PmsProductAttributeExample example = new PmsProductAttributeExample();
        example.createCriteria().andIdIn(ids);
        int count = attributeMapper.deleteByExample(example);
        // 更新属性分类数量
        int type = attribute.getType();
        if (0 == type) {
            int num = Math.max(category.getAttributeCount() - count, 0);
            category.setAttributeCount(num);
        }else  if (1 == type) {
            int num = Math.max(category.getParamCount() - count, 0);
            category.setParamCount(num);
        }
        return count;
    }

    @Override
    public List<PmsProductAttrInfo> getProductAttributeInfo(Long categoryId) {
        //TODO 不懂为何取回的数据不对。
        return attributeDao.getProductAttrInfo(categoryId);
    }
}
