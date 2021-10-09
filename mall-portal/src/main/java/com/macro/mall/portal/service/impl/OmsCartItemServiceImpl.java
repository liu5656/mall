package com.macro.mall.portal.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.macro.mall.mapper.OmsCartItemMapper;
import com.macro.mall.model.OmsCartItem;
import com.macro.mall.model.OmsCartItemExample;
import com.macro.mall.model.UmsMember;
import com.macro.mall.portal.dao.PortalProductDao;
import com.macro.mall.portal.domain.CartProduct;
import com.macro.mall.portal.domain.CartPromotionItem;
import com.macro.mall.portal.service.OmsCartItemService;
import com.macro.mall.portal.service.OmsPromotionService;
import com.macro.mall.portal.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 购物车管理service实现类
 * @version 1.0
 * @Author lj
 * @date 2021/10/8 2:55 下午
 * @desc
 */
@Service
public class OmsCartItemServiceImpl implements OmsCartItemService {

    @Autowired private OmsCartItemMapper cartItemMapper;
    @Autowired private PortalProductDao productDao;
    @Autowired private OmsPromotionService promotionService;
    @Autowired private UmsMemberService memberService;

    @Override
    public int add(OmsCartItem item) {
        int count = 0;
        UmsMember user = memberService.getCurrentMember();
        item.setMemberId(user.getId());
        item.setMemberNickname(user.getNickname());
        item.setDeleteStatus(0);
        OmsCartItem exist = getCartItem(item);
        if (exist == null) {
            item.setCreateDate(new Date());
            count = cartItemMapper.insert(item);
        }else{
            item.setModifyDate(new Date());
            item.setQuantity(exist.getQuantity() + item.getQuantity());
            count = cartItemMapper.updateByPrimaryKeySelective(item);
        }
        item.setCreateDate(new Date());
        return count;
    }

    @Override
    public int delete(Long memberId, List<Long> ids) {
        //TODO 不一致
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andMemberIdEqualTo(memberId).andIdIn(ids);
        return cartItemMapper.deleteByExample(example);
    }

    @Override
    public int clear(Long memberId) {
        //TODO 不一致
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andMemberIdEqualTo(memberId);
        return cartItemMapper.deleteByExample(example);
    }

    @Override
    public List<OmsCartItem> list(Long memberId) {
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andMemberIdEqualTo(memberId).andDeleteStatusEqualTo(0);
        return cartItemMapper.selectByExample(example);
    }

    @Override
    public List<CartPromotionItem> listPromotion(Long memberId, List<Long> cartIds) {
        List<OmsCartItem> items = list(memberId);
        if (CollUtil.isNotEmpty(items)) {
            items = items.stream().filter( item -> cartIds.contains(item.getId()))
                    .collect(Collectors.toList());
        }
        List<CartPromotionItem> promotionItems = new ArrayList<>();
        if (CollUtil.isNotEmpty(promotionItems)) {
            promotionItems = promotionService.calcCartPromotion(items);
        }
        return promotionItems;
    }

    @Override
    public CartProduct getCartProduct(Long productId) {
        return productDao.getCartProduct(productId);
    }

    @Override
    public int updateQuantity(Long id, Long memberId, Integer quantity) {
        OmsCartItem item = new OmsCartItem();
        item.setQuantity(quantity);
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria()
                .andIdEqualTo(id)
                .andMemberIdEqualTo(memberId)
                .andDeleteStatusEqualTo(0);
        return cartItemMapper.updateByExampleSelective(item, example);
    }

    @Override
    public int updateAttr(OmsCartItem item) {
        OmsCartItem update = new OmsCartItem();
        update.setId(item.getId());
        update.setModifyDate(new Date());
        update.setDeleteStatus(1);
        cartItemMapper.updateByPrimaryKeySelective(update);
        item.setId(null);
        add(item);
        return 1;
    }

    /**
     * 根据会员id，商品id，规格id获取购物车商品
     */
    private OmsCartItem getCartItem(OmsCartItem item) {
        OmsCartItemExample example = new OmsCartItemExample();
        OmsCartItemExample.Criteria criteria = example.createCriteria().andMemberIdEqualTo(item.getMemberId())
                .andProductIdEqualTo(item.getProductId())
                .andDeleteStatusEqualTo(0);
        if (StringUtils.isEmpty(item.getProductSkuId()) == false) {
            criteria.andProductSkuIdEqualTo(item.getProductSkuId());
        }
        List<OmsCartItem> items = cartItemMapper.selectByExample(example);
        if (items.size() > 0) {
            return items.get(0);
        }
        return null;
    }
}
