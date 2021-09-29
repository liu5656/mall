package com.macro.mall.portal.service.impl;

import com.macro.mall.mapper.UmsMemberReceiveAddressMapper;
import com.macro.mall.model.UmsMember;
import com.macro.mall.model.UmsMemberReceiveAddress;
import com.macro.mall.model.UmsMemberReceiveAddressExample;
import com.macro.mall.portal.service.UmsMemberService;
import com.macro.mall.portal.service.UmsReceiveAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户收货地址管理实现类
 * @version 1.0
 * @Author lj
 * @date 2021/9/28 3:58 下午
 * @desc
 */
@Service
public class UmsReceiveAddressServiceImpl implements UmsReceiveAddressService {

    @Autowired private UmsMemberReceiveAddressMapper addressMapper;
    @Autowired private UmsMemberService memberService;

    @Override
    public int add(UmsMemberReceiveAddress address) {
        UmsMember member = memberService.getCurrentMember();
        address.setMemberId(member.getId());
        return addressMapper.insert(address);
    }

    @Override
    public int delete(Long id) {
        UmsMember member = memberService.getCurrentMember();
        UmsMemberReceiveAddressExample example = new UmsMemberReceiveAddressExample();
        example.createCriteria().andMemberIdEqualTo(member.getId()).andIdEqualTo(id);
        return addressMapper.deleteByExample(example);
    }

    @Override
    public int update(Long id, UmsMemberReceiveAddress address) {
        UmsMember member = memberService.getCurrentMember();
        if (address.getDefaultStatus() == 1) {
            UmsMemberReceiveAddressExample example = new UmsMemberReceiveAddressExample();
            example.createCriteria().andMemberIdEqualTo(member.getId()).andDefaultStatusEqualTo(1);

            UmsMemberReceiveAddress old = new UmsMemberReceiveAddress();
            old.setDefaultStatus(0);
            addressMapper.updateByExampleSelective(old, example);
        }

        UmsMemberReceiveAddressExample example = new UmsMemberReceiveAddressExample();
        example.createCriteria().andMemberIdEqualTo(member.getId()).andIdEqualTo(id);
        return addressMapper.updateByExampleSelective(address, example);
    }

    @Override
    public List<UmsMemberReceiveAddress> list() {
        UmsMember member = memberService.getCurrentMember();
        UmsMemberReceiveAddressExample example = new UmsMemberReceiveAddressExample();
        example.createCriteria().andMemberIdEqualTo(member.getId());
        return addressMapper.selectByExample(example);
    }

    @Override
    public UmsMemberReceiveAddress getItem(Long id) {
        UmsMember member = memberService.getCurrentMember();
        UmsMemberReceiveAddressExample example = new UmsMemberReceiveAddressExample();
        example.createCriteria().andMemberIdEqualTo(member.getId()).andIdEqualTo(id);
        List<UmsMemberReceiveAddress> addresses = addressMapper.selectByExample(example);
        if (addresses.size() > 0) {
            return addresses.get(0);
        }
        return null;
    }
}
