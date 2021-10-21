package com.macro.mall.portal.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.mapper.PmsBrandMapper;
import com.macro.mall.mapper.PmsProductMapper;
import com.macro.mall.model.PmsBrand;
import com.macro.mall.model.PmsBrandExample;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.model.PmsProductExample;
import com.macro.mall.portal.dao.HomeDao;
import com.macro.mall.portal.service.PortalBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页品牌关联Service实现
 * @version 1.0
 * @Author lj
 * @date 2021/10/21 3:38 下午
 * @desc
 */
@Service
public class PortalBrandServiceImpl implements PortalBrandService {

    @Autowired private HomeDao homeDao;
    @Autowired private PmsBrandMapper brandMapper;
    @Autowired private PmsProductMapper productMapper;

    @Override
    public List<PmsBrand> recommendList(Integer pageNum, Integer pageSize) {
        return homeDao.getRecommendBrandList(pageSize * (pageNum - 1), pageSize);
    }

    @Override
    public PmsBrand detail(Long brandId) {
        return brandMapper.selectByPrimaryKey(brandId);
    }

    @Override
    public CommonPage<PmsProduct> productList(Long brandId, Integer pageNum, Integer pageSize) {
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andBrandIdEqualTo(brandId).andDeleteStatusEqualTo(1);
        PageHelper.startPage(pageNum, pageSize);
        return CommonPage.restPage(productMapper.selectByExample(example));
    }
}
