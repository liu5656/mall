package com.macro.mall.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.macro.mall.admin.service.SmsHomeRecommendProductService;
import com.macro.mall.mapper.SmsHomeRecommendProductMapper;
import com.macro.mall.model.SmsHomeRecommendProduct;
import com.macro.mall.model.SmsHomeRecommendProductExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/14 2:45 下午
 * @desc
 */
@Service
public class SmsHomeRecommendProductServcieImpl implements SmsHomeRecommendProductService {

    @Autowired private SmsHomeRecommendProductMapper mapper;

    @Override
    public int create(List<SmsHomeRecommendProduct> products) {
        products.forEach(item -> {
            item.setSort(0);
            item.setRecommendStatus(1);
            mapper.insertSelective(item);
        });
        return products.size();
    }

    @Override
    public int delete(List<Long> ids) {
        SmsHomeRecommendProductExample example = new SmsHomeRecommendProductExample();
        example.createCriteria().andIdIn(ids);
        return mapper.deleteByExample(example);
    }

    @Override
    public List<SmsHomeRecommendProduct> list(String productName, Integer recommendStatus, Integer pageNum, Integer pageSize) {
        SmsHomeRecommendProductExample example = new SmsHomeRecommendProductExample();
        example.setOrderByClause("sort desc");
        SmsHomeRecommendProductExample.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmpty(productName)) {
            criteria.andProductNameLike("%" + productName + "%");
        }
        if (recommendStatus != null) {
            criteria.andRecommendStatusEqualTo(recommendStatus);
        }
        PageHelper.startPage(pageNum, pageSize);
        return mapper.selectByExample(example);
    }

    @Override
    public int updateSort(Long id, Integer sort) {
        SmsHomeRecommendProduct product = new SmsHomeRecommendProduct();
        product.setId(id);
        product.setSort(sort);
        return mapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public int updateRecommendstatus(List<Long> ids, Integer recommendStatus) {
        SmsHomeRecommendProductExample example = new SmsHomeRecommendProductExample();
        example.createCriteria().andIdIn(ids);

        SmsHomeRecommendProduct product = new SmsHomeRecommendProduct();
        product.setRecommendStatus(recommendStatus);

        return mapper.updateByExampleSelective(product, example);
    }
}
