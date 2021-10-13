package com.macro.mall.admin.service.impl;

import com.macro.mall.admin.service.CmsPreferenceAreaService;
import com.macro.mall.mapper.CmsPrefrenceAreaMapper;
import com.macro.mall.model.CmsPrefrenceArea;
import com.macro.mall.model.CmsPrefrenceAreaExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品优选管理Service实现类
 * @version 1.0
 * @Author lj
 * @date 2021/10/13 9:55 上午
 * @desc
 */
@Service
public class CmsPreferenceAreaServiceImpl implements CmsPreferenceAreaService {

    @Autowired private CmsPrefrenceAreaMapper preferenceAreaMapper;

    @Override
    public List<CmsPrefrenceArea> listAll() {
        return preferenceAreaMapper.selectByExample(new CmsPrefrenceAreaExample());
    }
}
