package com.macro.mall.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.admin.service.OmsOrderReturnReasonService;
import com.macro.mall.mapper.OmsOrderReturnReasonMapper;
import com.macro.mall.model.OmsOrderReturnReason;
import com.macro.mall.model.OmsOrderReturnReasonExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 退货原因管理Service实现类
 * @version 1.0
 * @Author lj
 * @date 2021/10/19 3:51 下午
 * @desc
 */
@Service
public class OmsOrderReturnReasonServiceImpl implements OmsOrderReturnReasonService {

    @Autowired private OmsOrderReturnReasonMapper reasonMapper;

    @Override
    public int create(OmsOrderReturnReason returnReason) {
        returnReason.setCreateTime(new Date());
        return reasonMapper.insertSelective(returnReason);
    }

    @Override
    public int delete(List<Long> ids) {
        OmsOrderReturnReasonExample example = new OmsOrderReturnReasonExample();
        example.createCriteria().andIdIn(ids);
        return reasonMapper.deleteByExample(example);
    }

    @Override
    public int update(Long id, OmsOrderReturnReason returnReason) {
        returnReason.setId(id);
        return reasonMapper.updateByPrimaryKeySelective(returnReason);
    }

    @Override
    public int updateStatus(List<Long> ids, Integer status) {
        OmsOrderReturnReasonExample example = new OmsOrderReturnReasonExample();
        example.createCriteria().andIdIn(ids);

        OmsOrderReturnReason reason = new OmsOrderReturnReason();
        reason.setStatus(status);
        return reasonMapper.updateByExampleSelective(reason, example);
    }

    @Override
    public List<OmsOrderReturnReason> list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        OmsOrderReturnReasonExample example = new OmsOrderReturnReasonExample();
        example.setOrderByClause("sort desc");
        return reasonMapper.selectByExample(example);
    }

    @Override
    public OmsOrderReturnReason getItem(Long id) {
        return reasonMapper.selectByPrimaryKey(id);
    }
}
