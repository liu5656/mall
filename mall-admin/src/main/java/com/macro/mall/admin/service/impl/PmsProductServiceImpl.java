package com.macro.mall.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.macro.mall.admin.dao.*;
import com.macro.mall.admin.dto.PmsProductParam;
import com.macro.mall.admin.dto.PmsProductQueryParam;
import com.macro.mall.admin.dto.PmsProductResult;
import com.macro.mall.admin.service.PmsProductService;
import com.macro.mall.mapper.*;
import com.macro.mall.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/12 9:23 上午
 * @desc
 */
@Service
public class PmsProductServiceImpl implements PmsProductService {

    private static Logger log = LoggerFactory.getLogger(PmsProductServiceImpl.class);

    @Autowired private PmsProductDao productDao;
    @Autowired private PmsProductMapper productMapper;

    @Autowired private PmsMemberPriceDao memberPriceDao;
    @Autowired private PmsMemberPriceMapper memberPriceMapper;

    @Autowired private PmsProductLadderDao productLadderDao;
    @Autowired private PmsProductLadderMapper productLadderMapper;

    @Autowired private PmsProductFullReductionDao productFullReductionDao;
    @Autowired private PmsProductFullReductionMapper productFullReductionMapper;

    @Autowired private PmsSkuStockDao skuStockDao;
    @Autowired private PmsSkuStockMapper skuStockMapper;

    @Autowired private PmsProductAttributeValueDao productAttributeValueDao;
    @Autowired private PmsProductAttributeValueMapper productAttributeValueMapper;

    @Autowired private CmsSubjectProductRelationDao subjectProductRelationDao;
    @Autowired private CmsSubjectProductRelationMapper subjectProductRelationMapper;

    @Autowired private CmsPreferenceAreaProductRelationDao preferenceAreaProductRelationDao;
    @Autowired private CmsPrefrenceAreaProductRelationMapper preferenceAreaProductRelationMapper;

    @Autowired private PmsProductVerifyRecordDao productVerifyRecordDao;

    @Override
    public int create(PmsProductParam param) {
        PmsProduct product = param;
        product.setId(null);
        int count = productMapper.insertSelective(product);
        //根据促销类型设置价格：会员价格，阶梯价格，满减价格；处理sku库存信息，商品参数或规格，关联专题，关联优选。
        Long id = product.getId();
        insertAndRelation(memberPriceDao, param.getMemberPriceList(), id);
        insertAndRelation(productLadderDao, param.getProductLadderList(), id);
        insertAndRelation(productFullReductionDao, param.getProductFullReductionList(), id);
        insertAndRelation(skuStockDao, handleSkuStockCode(param.getSkuStockList(),id), id);
        insertAndRelation(productAttributeValueDao, param.getProductAttributeValueList(), id);
        insertAndRelation(subjectProductRelationDao,param.getSubjectProductRelationList(), id);
        insertAndRelation(preferenceAreaProductRelationDao, param.getPreferenceAreaProductRelationList(), id);
        return count;
    }

    @Override
    public List<PmsProduct> list(PmsProductQueryParam param, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductExample example = new PmsProductExample();
        PmsProductExample.Criteria criteria = example.createCriteria();
        criteria.andDeleteStatusEqualTo(0);

        if (param.getPublishStatus() != null) {
            criteria.andPublishStatusEqualTo(param.getPublishStatus());
        }
        if (param.getVerifyStatus() != null) {
            criteria.andVerifyStatusEqualTo(param.getVerifyStatus());
        }
        if (param.getBrandId() != null) {
            criteria.andBrandIdEqualTo(param.getBrandId());
        }
        if (param.getProductCategoryId() != null) {
            criteria.andProductCategoryIdEqualTo(param.getProductCategoryId());
        }
        if (StringUtil.isNotEmpty(param.getKeyword())) {
            criteria.andKeywordsLike("%" + param.getKeyword() + "%");
        }
        if (StringUtil.isNotEmpty(param.getProductSn())) {
            criteria.andProductSnEqualTo(param.getProductSn());
        }
        return productMapper.selectByExample(example);
    }

    @Override
    public List<PmsProduct> list(String keyword) {
        PmsProductExample example = new PmsProductExample();
        PmsProductExample.Criteria criteria = example.createCriteria();
        criteria.andDeleteStatusEqualTo(0);
        if (StringUtil.isNotEmpty(keyword)) {
            criteria.andKeywordsLike("%" + keyword + "%");
            example.or().andDeleteStatusEqualTo(0).andProductSnLike("%" + keyword + "%");
        }
        return productMapper.selectByExample(example);
    }

    @Override
    public PmsProductResult getUpdateInfo(Long id) {
        return productDao.getUpdateInfo(id);
    }

    @Override
    public int update(Long id, PmsProductParam param) {
        PmsProduct product = param;
        product.setId(id);
        int count = productMapper.updateByPrimaryKeySelective(product);

        memberPriceMapper.deleteByExample((PmsMemberPriceExample) assemblyExample(PmsMemberPriceExample.class, id));
        insertAndRelation(memberPriceDao, param.getMemberPriceList(), id);

        productLadderMapper.deleteByExample((PmsProductLadderExample) assemblyExample(PmsProductLadderExample.class, id));
        insertAndRelation(productLadderDao, param.getProductLadderList(), id);

        productFullReductionMapper.deleteByExample((PmsProductFullReductionExample) assemblyExample(PmsProductFullReductionExample.class,id));
        insertAndRelation(productFullReductionDao, param.getProductFullReductionList(), id);

        handleUpdateSkuStockList(id, param);

        productAttributeValueMapper.deleteByExample((PmsProductAttributeValueExample) assemblyExample(PmsProductAttributeValueExample.class,id));
        insertAndRelation(productAttributeValueDao, param.getProductAttributeValueList(), id);

        subjectProductRelationMapper.deleteByExample((CmsSubjectProductRelationExample) assemblyExample(CmsSubjectProductRelationExample.class, id));
        insertAndRelation(subjectProductRelationDao, param.getSubjectProductRelationList(), id);

        preferenceAreaProductRelationMapper.deleteByExample((CmsPrefrenceAreaProductRelationExample) assemblyExample(CmsPrefrenceAreaProductRelationExample.class, id));
        insertAndRelation(preferenceAreaProductRelationDao, param.getPreferenceAreaProductRelationList(), id);

        return count;
    }

    @Override
    public int updateVerifyStatus(List<Long> ids, Integer status, String detail) {
        PmsProduct product = new PmsProduct();
        product.setVerifyStatus(status);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        int count = productMapper.updateByExampleSelective(product, example);

        // 修改商品信息后更新记录表
        List<PmsProductVertifyRecord> records = ids.stream().map( id -> {
           PmsProductVertifyRecord record = new PmsProductVertifyRecord();
           record.setProductId(id);
           record.setStatus(status);
           record.setCreateTime(new Date());
           record.setDetail(detail);
           record.setVertifyMan("sir");
           return record;
        }).collect(Collectors.toList());
        productVerifyRecordDao.insertList(records);

        return count;
    }

    @Override
    public int updatePublishStatus(List<Long> ids, Integer status) {
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);

        PmsProduct product = new PmsProduct();
        product.setPublishStatus(status);

        return productMapper.updateByExampleSelective(product, example);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer status) {
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);

        PmsProduct product = new PmsProduct();
        product.setRecommandStatus(status);

        return productMapper.updateByExampleSelective(product, example);
    }

    @Override
    public int updateNewStatus(List<Long> ids, Integer status) {
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);

        PmsProduct product = new PmsProduct();
        product.setNewStatus(status);

        return productMapper.updateByExampleSelective(product, example);
    }

    @Override
    public int updateDelete(List<Long> ids, Integer status) {
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);

        PmsProduct product = new PmsProduct();
        product.setDeleteStatus(status);

        return productMapper.updateByExampleSelective(product, example);
    }

    /**
     * 建立和插入关系表操作
     */
    private void insertAndRelation(Object dao, List datas, Long productId) {
        if (CollUtil.isEmpty(datas)) return;
        try{
            for (Object item : datas) {
                Method setId = item.getClass().getMethod("setId", Long.class);
                setId.invoke(item, (Long)null);
                Method setProductId = item.getClass().getMethod("setProductId", Long.class);
                setProductId.invoke(item, (Long)null);
            }
            Method insert = dao.getClass().getMethod("insertList", List.class);
            insert.invoke(dao, datas);
        }catch(Exception e) {
            log.info("创建产品出错:{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
    /**
     * 更新商品相关操作
     */
    private Object assemblyExample(Class exampleClass, Long productId) {
        try{
            Object example = exampleClass.newInstance();
            Method createMethod = example.getClass().getMethod("createCriteria");
            Object criteria = createMethod.invoke(example);
            Method andProductIdEqualTo = criteria.getClass().getMethod("andProductIdEqualTo", Long.class);
            andProductIdEqualTo.invoke(criteria, (Long)productId);
            return example;
        }catch(Exception e) {
            log.info("配置example失败");
        }
        return null;
    }
    /**
     * 设置SkuCode
     */
    private List<PmsSkuStock> handleSkuStockCode(List<PmsSkuStock> skuStocks, Long productId) {
        if (CollUtil.isEmpty(skuStocks)) return skuStocks;
        for (int i = 0; i < skuStocks.size(); i++) {
            PmsSkuStock item = skuStocks.get(i);
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            StringBuilder code = new StringBuilder();
            code.append(format.format(new Date()));
            code.append(String.format("%04d", productId));
            code.append(String.format("%03d", i + 1));
            item.setSkuCode(code.toString());
        }
        return skuStocks;
    }
    /**
     * 更新SkuStock
     */
    private void handleUpdateSkuStockList(Long id, PmsProductParam productParam) {
        List<PmsSkuStock> skuStocks = productParam.getSkuStockList();
        PmsSkuStockExample example = new PmsSkuStockExample();
        example.createCriteria().andProductIdEqualTo(id);
        if (CollUtil.isEmpty(skuStocks)) {  // 清空
            skuStockMapper.deleteByExample(example);
            return;
        }else{                              // 替换
            List<PmsSkuStock> olds = skuStockMapper.selectByExample(example);
            List<PmsSkuStock> needInsert = skuStocks.stream().filter( item -> item.getId() == null).collect(Collectors.toList());
            List<PmsSkuStock> needUpdates = skuStocks.stream().filter( item -> item.getId() != null ).collect(Collectors.toList());
            List<Long> needUpdateIds = needUpdates.stream().map(PmsSkuStock::getId).collect(Collectors.toList());
            List<PmsSkuStock> needRemove = olds.stream().filter( item -> needUpdateIds.contains(item.getId()) == false).collect(Collectors.toList());

            // add
            if (CollUtil.isNotEmpty(needInsert)) {
                handleSkuStockCode(needInsert, id);
                insertAndRelation(skuStockDao, needInsert, id);
            }
            // delete
            if (CollUtil.isNotEmpty(needRemove)) {
                PmsSkuStockExample stockExample = new PmsSkuStockExample();
                stockExample.createCriteria().andIdIn(needRemove.stream().map(PmsSkuStock::getId).collect(Collectors.toList()));
                skuStockMapper.deleteByExample(stockExample);
            }
            // update
            if (CollUtil.isNotEmpty(needUpdates)) {
                handleSkuStockCode(needUpdates, id);
                for (PmsSkuStock item : needUpdates) {
                    skuStockMapper.updateByPrimaryKeySelective(item);
                }
            }
        }
    }
}
