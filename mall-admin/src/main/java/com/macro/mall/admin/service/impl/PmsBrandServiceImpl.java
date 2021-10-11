package com.macro.mall.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.macro.mall.admin.dto.PmsBrandParam;
import com.macro.mall.admin.service.PmsBrandService;
import com.macro.mall.mapper.PmsBrandMapper;
import com.macro.mall.mapper.PmsProductMapper;
import com.macro.mall.model.PmsBrand;
import com.macro.mall.model.PmsBrandExample;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.model.PmsProductExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 商品品牌管理Service实现
 * @version 1.0
 * @Author lj
 * @date 2021/10/11 5:07 下午
 * @desc
 */
@Service
public class PmsBrandServiceImpl implements PmsBrandService {

    @Autowired private PmsBrandMapper brandMapper;
    @Autowired private PmsProductMapper productMapper;

    @Override
    public int create(PmsBrandParam param) {
        PmsBrand brand = new PmsBrand();
        BeanUtil.copyProperties(param, brand);
        if (StringUtils.isEmpty(brand.getFirstLetter())) {
            brand.setFirstLetter(brand.getName().substring(0,1));
        }
        return brandMapper.insertSelective(brand);
    }

    @Override
    public int delete(Long id) {
        return brandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteList(List<Long> ids) {
        PmsBrandExample example = new PmsBrandExample();
        example.createCriteria().andIdIn(ids);
        return brandMapper.deleteByExample(example);
    }

    @Override
    public PmsBrand getItem(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<PmsBrand> getAll() {
        return brandMapper.selectByExample(new PmsBrandExample());
    }

    @Override
    public List<PmsBrand> getList(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PmsBrandExample example = new PmsBrandExample();
        example.setOrderByClause("sort desc");
        PmsBrandExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isEmpty(keyword) == false) {
            criteria.andNameLike("%" + keyword + "%");
        }
        return brandMapper.selectByExample(example);
    }

    @Override
    public int update(Long id, PmsBrandParam param) {
        PmsBrand brand = new PmsBrand();
        BeanUtil.copyProperties(param, brand);
        brand.setId(id);
        if (StringUtils.isEmpty(brand.getFirstLetter())) {
            brand.setFirstLetter(brand.getName().substring(0,1));
        }
        // 更新商品中的品牌信息
        PmsProduct product = new PmsProduct();
        product.setBrandName(brand.getName());
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andBrandIdEqualTo(id);
        productMapper.updateByExample(product, example);
        return brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public int updateShowStatus(List<Long> ids, Integer status) {
        PmsBrand brand = new PmsBrand();
        brand.setShowStatus(status);

        PmsBrandExample example = new PmsBrandExample();
        example.createCriteria().andIdIn(ids);

        return brandMapper.updateByExampleSelective(brand, example);
    }

    @Override
    public int updateFactoryStatus(List<Long> ids, Integer status) {
        PmsBrand brand = new PmsBrand();
        brand.setFactoryStatus(status);

        PmsBrandExample example = new PmsBrandExample();
        example.createCriteria().andIdIn(ids);

        return brandMapper.updateByExampleSelective(brand, example);
    }
}
