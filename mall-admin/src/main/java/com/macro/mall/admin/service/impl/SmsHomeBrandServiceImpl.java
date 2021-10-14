package com.macro.mall.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.macro.mall.admin.service.SmsHomeBrandService;
import com.macro.mall.mapper.SmsHomeBrandMapper;
import com.macro.mall.model.SmsHomeBrand;
import com.macro.mall.model.SmsHomeBrandExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页品牌管理service实现
 * @version 1.0
 * @Author lj
 * @date 2021/10/14 9:18 上午
 * @desc
 */
@Service
public class SmsHomeBrandServiceImpl implements SmsHomeBrandService {

    @Autowired private SmsHomeBrandMapper brandMapper;

    @Override
    public int create(List<SmsHomeBrand> brands) {
        //warning 循环插入对象
        brands.forEach(item -> brandMapper.insertSelective(item));
        return brands.size();
    }

    @Override
    public int delete(List<Long> ids) {
        SmsHomeBrandExample example = new SmsHomeBrandExample();
        example.createCriteria().andIdIn(ids);
        return brandMapper.deleteByExample(example);
    }

    @Override
    public int update(Long id, Integer sort) {
        SmsHomeBrand brand = new SmsHomeBrand();
        brand.setSort(sort);
        brand.setId(id);
        return brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public int update(List<Long> ids, Integer status) {
        SmsHomeBrand brand = new SmsHomeBrand();
        brand.setRecommendStatus(status);
        SmsHomeBrandExample example = new SmsHomeBrandExample();
        example.createCriteria().andIdIn(ids);
        return brandMapper.updateByExampleSelective(brand, example);
    }

    @Override
    public List<SmsHomeBrand> list(String brandName, Integer recommendStatus, Integer pageNum, Integer pageSize) {
        SmsHomeBrandExample example = new SmsHomeBrandExample();
        example.setOrderByClause("sort desc");
        SmsHomeBrandExample.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmpty(brandName)) {
            criteria.andBrandNameLike("%" + brandName + "%");
        }
        if (recommendStatus != null) {
            criteria.andRecommendStatusEqualTo(recommendStatus);
        }
        PageHelper.startPage(pageNum, pageSize);
        return brandMapper.selectByExample(example);
    }
}
