package com.macro.mall.portal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.macro.mall.mapper.*;
import com.macro.mall.model.*;
import com.macro.mall.portal.dao.PortalProductDao;
import com.macro.mall.portal.domain.PmsPortalProductDetail;
import com.macro.mall.portal.domain.PmsProductCategoryNode;
import com.macro.mall.portal.service.PmsPortalProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 前端商品管理Service实现类
 * @version 1.0
 * @Author lj
 * @date 2021/10/21 5:12 下午
 * @desc
 */
public class PmsPortalProductServiceImpl implements PmsPortalProductService {

    @Autowired private PmsProductMapper productMapper;
    @Autowired private PmsProductCategoryMapper productCategoryMapper;
    @Autowired private PmsBrandMapper brandMapper;
    @Autowired private PmsProductAttributeMapper attributeMapper;
    @Autowired private PmsProductAttributeValueMapper attributeValueMapper;
    @Autowired private PmsProductLadderMapper ladderMapper;
    @Autowired private PmsProductFullReductionMapper fullReductionMapper;
    @Autowired private PmsSkuStockMapper stockMapper;
    @Autowired private PortalProductDao productDao;

    @Override
    public List<PmsProduct> search(String keyword, Long brandId, Long productCategoryId, Integer pageNum, Integer pageSize, Integer sort) {
        PmsProductExample example = new PmsProductExample();
        PmsProductExample.Criteria criteria = example.createCriteria();
        criteria.andDeleteStatusEqualTo(0);
        if (StringUtil.isNotEmpty(keyword)) {
            criteria.andKeywordsLike("%" + keyword + "%");
        }
        if (brandId != null) {
            criteria.andBrandIdEqualTo(brandId);
        }
        if (productCategoryId != null) {
            criteria.andProductCategoryIdEqualTo(productCategoryId);
        }
        switch (sort) {
            case 1:     // 按新品
                example.setOrderByClause("id desc");
                break;
            case 2:     // 效率从高到低
                example.setOrderByClause("sale desc");
                break;
            case 3:     // 价格从低到高
                example.setOrderByClause("price asc");
                break;
            case 4:     // 价格从高到低
                example.setOrderByClause("price desc");
        }
        PageHelper.startPage(pageNum, pageSize);
        return productMapper.selectByExample(example);
    }

    @Override
    public List<PmsProductCategoryNode> categoryTreeList() {
        PmsProductCategoryExample example = new PmsProductCategoryExample();
        List<PmsProductCategory> categories = productCategoryMapper.selectByExample(example);
        List<PmsProductCategoryNode> result = categories.stream().filter(item -> item.getParentId().equals(0))
                .map(item -> convert(item, categories)).collect(Collectors.toList());
        return result;
    }

    @Override
    public PmsPortalProductDetail detail(Long id) {
        PmsPortalProductDetail result = new PmsPortalProductDetail();
        PmsProduct product = productMapper.selectByPrimaryKey(id);
        result.setProduct(product);

        result.setBrand(brandMapper.selectByPrimaryKey(product.getBrandId()));

        PmsProductAttributeExample attributeExample = new PmsProductAttributeExample();
        attributeExample.createCriteria().andProductAttributeCategoryIdEqualTo(product.getProductAttributeCategoryId());
        result.setProductAttributeList(attributeMapper.selectByExample(attributeExample));

        if (CollUtil.isNotEmpty(result.getProductAttributeList())) {
            List<Long> attributeIds = result.getProductAttributeList().stream().map(PmsProductAttribute::getId).collect(Collectors.toList());
            PmsProductAttributeValueExample valueExample = new PmsProductAttributeValueExample();
            valueExample.createCriteria().andProductIdEqualTo(id).andIdIn(attributeIds);
            result.setProductAttributeValueList(attributeValueMapper.selectByExample(valueExample));
        }

        PmsSkuStockExample stockExample = new PmsSkuStockExample();
        stockExample.createCriteria().andIdEqualTo(id);
        result.setSkuStockList(stockMapper.selectByExample(stockExample));

        switch (product.getPromotionType()) {
            case 3:
                PmsProductLadderExample ladderExample = new PmsProductLadderExample();
                ladderExample.createCriteria().andProductIdEqualTo(id);
                result.setProductLadderList(ladderMapper.selectByExample(ladderExample));
                break;
            case 4:
                PmsProductFullReductionExample reductionExample = new PmsProductFullReductionExample();
                reductionExample.createCriteria().andProductIdEqualTo(id);
                result.setProductFullReductionList(fullReductionMapper.selectByExample(reductionExample));
                break;
        }

        result.setCouponList(productDao.getAvailableCouponList(id, product.getProductCategoryId()));


        return result;
    }

    /**
     * 对象转化为节点
     */
    private PmsProductCategoryNode convert(PmsProductCategory category, List<PmsProductCategory> allList) {
        PmsProductCategoryNode node = new PmsProductCategoryNode();
        BeanUtil.copyProperties(category, node);

        List<PmsProductCategoryNode> children = allList.stream().filter( item -> item.getParentId() == category.getId())
                .map(item -> convert(item, allList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }
}
