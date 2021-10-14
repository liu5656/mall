package com.macro.mall.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.macro.mall.admin.service.SmsHomeNewProductService;
import com.macro.mall.mapper.SmsHomeNewProductMapper;
import com.macro.mall.model.SmsHomeNewProduct;
import com.macro.mall.model.SmsHomeNewProductExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/14 1:52 下午
 * @desc
 */
@Service
public class SmsHomeNewProductServiceImpl implements SmsHomeNewProductService {

    @Autowired private SmsHomeNewProductMapper productMapper;

    @Override
    public int create(List<SmsHomeNewProduct> products) {
        products.forEach(item -> {
            item.setRecommendStatus(0);
            item.setSort(0);
            productMapper.insertSelective(item);
        });
        return products.size();
    }

    @Override
    public int delete(List<Long> ids) {
        SmsHomeNewProductExample example = new SmsHomeNewProductExample();
        example.createCriteria().andIdIn(ids);
        return productMapper.deleteByExample(example);
    }

    @Override
    public List<SmsHomeNewProduct> list(String name, Integer recommendStatus, Integer pageNum, Integer pageSize) {
        SmsHomeNewProductExample example = new SmsHomeNewProductExample();
        example.setOrderByClause("sort desc");
        SmsHomeNewProductExample.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmpty(name)) {
            criteria.andProductNameLike("%" + name + "%");
        }
        if (recommendStatus != null) {
            criteria.andRecommendStatusEqualTo(recommendStatus);
        }
        PageHelper.startPage(pageNum, pageSize);
        return productMapper.selectByExample(example);
    }

    @Override
    public int updateSort(Long id, Integer sort) {
        SmsHomeNewProduct product = new SmsHomeNewProduct();
        product.setId(id);
        product.setSort(sort);
        return productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer status) {
        SmsHomeNewProductExample example = new SmsHomeNewProductExample();
        example.createCriteria().andIdIn(ids);

        SmsHomeNewProduct product = new SmsHomeNewProduct();
        product.setRecommendStatus(status);

        return productMapper.updateByExampleSelective(product, example);
    }
}
