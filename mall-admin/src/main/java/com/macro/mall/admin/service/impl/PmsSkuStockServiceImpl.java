package com.macro.mall.admin.service.impl;

import com.macro.mall.admin.dao.PmsSkuStockDao;
import com.macro.mall.admin.service.PmsSkuStockService;
import com.macro.mall.mapper.PmsSkuStockMapper;
import com.macro.mall.model.PmsSkuStock;
import com.macro.mall.model.PmsSkuStockExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/11 10:23 上午
 * @desc
 */
@Service
public class PmsSkuStockServiceImpl implements PmsSkuStockService {

    @Autowired private PmsSkuStockDao skuStockDao;
    @Autowired private PmsSkuStockMapper skuStockMapper;

    @Override
    public List<PmsSkuStock> getList(Long pid, String keyword) {
        PmsSkuStockExample example = new PmsSkuStockExample();
        PmsSkuStockExample.Criteria criteria = example.createCriteria().andProductIdEqualTo(pid);
        if (StringUtils.isEmpty(keyword) == false) {
            criteria.andSkuCodeLike("%" + keyword + "%");
        }
        return skuStockMapper.selectByExample(example);
    }

    @Override
    public int update(Long pid, List<PmsSkuStock> skuStocks) {
        return skuStockDao.replaceList(skuStocks);
    }
}
