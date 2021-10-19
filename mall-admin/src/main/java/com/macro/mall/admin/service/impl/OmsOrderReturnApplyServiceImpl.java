package com.macro.mall.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.admin.dao.OmsOrderReturnApplyDao;
import com.macro.mall.admin.dto.OmsReturnApplyQueryParam;
import com.macro.mall.admin.dto.OmsReturnApplyResult;
import com.macro.mall.admin.dto.OmsUpdateStatusParam;
import com.macro.mall.admin.service.OmsOrderReturnApplyService;
import com.macro.mall.mapper.OmsOrderReturnApplyMapper;
import com.macro.mall.model.OmsOrderReturnApply;
import com.macro.mall.model.OmsOrderReturnApplyExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 订单退货申请管理Service实现
 * @version 1.0
 * @Author lj
 * @date 2021/10/19 11:46 上午
 * @desc
 */
@Service
public class OmsOrderReturnApplyServiceImpl implements OmsOrderReturnApplyService {

    @Autowired private OmsOrderReturnApplyDao applyDao;
    @Autowired private OmsOrderReturnApplyMapper applyMapper;

    @Override
    public int delete(List<Long> ids) {
        OmsOrderReturnApplyExample example = new OmsOrderReturnApplyExample();
        example.createCriteria().andStatusEqualTo(3).andIdIn(ids);
        return applyMapper.deleteByExample(example);
    }

    @Override
    public int updateStatus(Long id, OmsUpdateStatusParam param) {
        OmsOrderReturnApply apply = new OmsOrderReturnApply();
        apply.setId(id);
        apply.setStatus(param.getStatus());
        apply.setHandleMan(param.getHandleMan());
        apply.setHandleNote(param.getHandleNote());
        switch (param.getStatus()) {
            case 1:
                apply.setHandleTime(new Date());
                apply.setReturnAmount(param.getReturnAmount());
                apply.setCompanyAddressId(param.getCompanyAddressId());
                break;
            case 2:
                apply.setReceiveTime(new Date());
                break;
            case 3:
                apply.setHandleTime(new Date());
                break;
            default:
                return 0;
        }
        return applyMapper.updateByPrimaryKeySelective(apply);
    }

    @Override
    public OmsReturnApplyResult getItem(Long id) {
        return applyDao.getDetail(id);
    }

    @Override
    public List<OmsOrderReturnApply> list(OmsReturnApplyQueryParam param, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return applyDao.getList(param);
    }
}
