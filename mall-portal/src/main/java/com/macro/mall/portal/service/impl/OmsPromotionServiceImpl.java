package com.macro.mall.portal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.macro.mall.model.OmsCartItem;
import com.macro.mall.model.PmsProductFullReduction;
import com.macro.mall.model.PmsProductLadder;
import com.macro.mall.model.PmsSkuStock;
import com.macro.mall.portal.dao.PortalProductDao;
import com.macro.mall.portal.domain.CartPromotionItem;
import com.macro.mall.portal.domain.PromotionProduct;
import com.macro.mall.portal.service.OmsPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 促销管理service实现类
 * @version 1.0
 * @Author lj
 * @date 2021/9/29 9:26 上午
 * @desc
 */
@Service
public class OmsPromotionServiceImpl implements OmsPromotionService {

    @Autowired private PortalProductDao productDao;

    @Override
    public List<CartPromotionItem> calcCartPromotion(List<OmsCartItem> cartItems) {
        // 根据productId对选中的商品分组，
        Map<Long, List<OmsCartItem>> productCartMap = groupCartItemBySpu(cartItems);
        // 以spu为单位进行优惠计算
        List<PromotionProduct> promotionProducts = getPromotionProductList(cartItems);
        // 根据商品促销类型计算商品促销优惠价格
        List<CartPromotionItem> promotionItems = new ArrayList<>();
        for (Map.Entry<Long, List<OmsCartItem>> entry : productCartMap.entrySet()) {
            Long productId = entry.getKey();
            List<OmsCartItem> items = entry.getValue();
            PromotionProduct promotionProduct = getPromotionProductByProductId(productId, promotionProducts);
            Integer promotionType = promotionProduct.getPromotionType();
            // "促销类型：0->没有促销使用原价;；2->使用会员价；；；5->限时购"
            if (1 == promotionType) {           // 1->使用促销价
                promotionItems.addAll(calculatePromotionPrice(items, promotionProduct));
            }else if (3 == promotionType) {     // 3->使用阶梯价格
                int count = getCartItemCount(items);
                PmsProductLadder ladder = getProductLadder(count, promotionProduct.getProductLadderList());
                if (ladder == null) {
                    handleNoReduce(promotionItems, items, promotionProduct);
                }else{
                    promotionItems.addAll(calculateDiscountPrice(ladder, items, promotionProduct));
                }
            }else if (4 == promotionType) {     // 4->使用满减价格
                BigDecimal totalAmount = getCartItemAmount(items, promotionProducts);
                PmsProductFullReduction fullReduction = getFullReduction(totalAmount, promotionProduct.getProductFullReductionList());
                if (fullReduction == null) {
                    handleNoReduce(promotionItems, items, promotionProduct);
                }else {
                    promotionItems.addAll(calculateFullReducePrice(totalAmount, fullReduction, items, promotionProduct));
                }
            }else{
                handleNoReduce(promotionItems, items, promotionProduct);
            }
        }

        return promotionItems;
    }

    /**
     * 使用促销
     */
    private List<CartPromotionItem> calculatePromotionPrice(List<OmsCartItem> items, PromotionProduct promotionProduct) {
        return items.stream().map( item -> {
            CartPromotionItem promotion = new CartPromotionItem();
            BeanUtil.copyProperties(item, promotion);
            promotion.setPromotionMessage("单品促销");

            PmsSkuStock sku = getOriginalPrice(promotionProduct, item.getProductSkuId());
            BigDecimal original = sku.getPrice();

            promotion.setPrice(original);
            promotion.setReduceAmount(original.subtract(sku.getPromotionPrice()));
            promotion.setRealStock(sku.getStock() - sku.getLockStock());
            promotion.setIntegral(promotionProduct.getGiftPoint());
            promotion.setGrowth(promotionProduct.getGiftGrowth());
            return promotion;
        }).collect(Collectors.toList());
    }

    /**
     * 计算折扣优惠
     */
    private List<CartPromotionItem> calculateDiscountPrice(PmsProductLadder ladder, List<OmsCartItem> items, PromotionProduct promotionProduct) {
        return items.stream().map( item -> {
            CartPromotionItem promotion = new CartPromotionItem();
            BeanUtil.copyProperties(item, promotion);
            promotion.setPromotionMessage(getLadderPromotionMessage(ladder));

            PmsSkuStock sku = getOriginalPrice(promotionProduct, item.getProductSkuId());
            BigDecimal origin = sku.getPrice();

            BigDecimal reduceAmount = origin.subtract(ladder.getDiscount().multiply(origin));
            promotion.setReduceAmount(reduceAmount);
            promotion.setRealStock(sku.getStock() - sku.getLockStock());
            promotion.setIntegral(promotionProduct.getGiftPoint());
            promotion.setGrowth(promotionProduct.getGiftGrowth());
            return promotion;
        }).collect(Collectors.toList());
    }

    /**
     * 计算满减优惠
     */
    private List<CartPromotionItem> calculateFullReducePrice(BigDecimal totalAmount, PmsProductFullReduction fullReduction, List<OmsCartItem> items, PromotionProduct promotionProduct) {
        return items.stream().map( item -> {
            CartPromotionItem promotion = new CartPromotionItem();
            BeanUtil.copyProperties(item, promotion);
            promotion.setPromotionMessage(getFullReductionMessage(fullReduction));
            // (商品原价/总价)*满减金额
            PmsSkuStock sku = getOriginalPrice(promotionProduct, item.getProductSkuId());
            BigDecimal origin = sku.getPrice();
            BigDecimal reduce = origin.divide(totalAmount, RoundingMode.HALF_EVEN).multiply(fullReduction.getReducePrice());

            promotion.setReduceAmount(reduce);
            promotion.setRealStock(sku.getStock() - sku.getLockStock());
            promotion.setIntegral(promotionProduct.getGiftPoint());
            promotion.setGrowth(promotionProduct.getGiftGrowth());
            return promotion;
        }).collect(Collectors.toList());
    }


    /**
     * 查询所有商品的优惠相关信息
     */
    private List<PromotionProduct> getPromotionProductList(List<OmsCartItem> items) {
        List<Long> productIds = items.stream().map(item -> item.getProductId()).collect(Collectors.toList());
        return productDao.getPromotionProductList(productIds);
    }

    /**
     * 以spu为单位对购物车中商品进行分组
     */
    private Map<Long, List<OmsCartItem>> groupCartItemBySpu(List<OmsCartItem> items) {
        Map<Long, List<OmsCartItem>> productMap = new TreeMap<>();
        items.stream().forEach( item -> {
            List<OmsCartItem> cartItems = productMap.get(item.getProductId());
            if (null == cartItems) {
                cartItems = new ArrayList<>();
                cartItems.add(item);
                productMap.put(item.getProductId(), cartItems);
            }else{
                cartItems.add(item);
            }
        });
        return productMap;
    }

    /**
     * 拼接满减促销信息
     */
    private String getFullReductionMessage(PmsProductFullReduction reduction) {
        return "满减优惠：满" + reduction.getFullPrice().toString() + "元，减" + reduction.getReducePrice() + "元";
    }

    /**
     * 获取商品原价
     */
    private PmsSkuStock getOriginalPrice(PromotionProduct product, Long skuId) {
        return product.getSkuStockList().stream().filter( item -> item.getId().equals(skuId)).findFirst().orElse(null);
    }

    /**
     * 对没满足优惠条件的商品处理
     */
    private void handleNoReduce(List<CartPromotionItem> promotionItems, List<OmsCartItem> cartItems, PromotionProduct product) {
        cartItems.forEach( item -> {
            CartPromotionItem promotionItem = new CartPromotionItem();
            BeanUtil.copyProperties(item, promotionItem);
            promotionItem.setPromotionMessage("暂无优惠");
            promotionItem.setReduceAmount(new BigDecimal(0));
            PmsSkuStock sku = getOriginalPrice(product, item.getProductSkuId());
            if (null != sku) {
                promotionItem.setRealStock(sku.getStock() - sku.getLockStock());
            }
            promotionItem.setIntegral(product.getGiftPoint());
            promotionItem.setGrowth(product.getGiftGrowth());
            promotionItems.add(promotionItem);
        });
    }

    /**
     * 从高到低根据满多少金额来获取满减对象
     */
    private PmsProductFullReduction getFullReduction(BigDecimal totalAmount,List<PmsProductFullReduction> fullReductions) {
        fullReductions.sort( (o1, o2) -> o1.getFullPrice().subtract(o2.getFullPrice()).intValue());
        for (PmsProductFullReduction reduction : fullReductions) {
            if (totalAmount.subtract(reduction.getFullPrice()).intValue() >= 0) {
                return reduction;
            }
        }
        return null;
    }

    /**
     * 打折优惠字符串
     */
    private String getLadderPromotionMessage(PmsProductLadder ladder) {
        return "打折优惠：满" + ladder.getCount() + "件，打" + ladder.getDiscount().multiply(new BigDecimal(10)).intValue() + "折";
    }

    /**
     * 根据购买商品数量获取打折信息
     */
    private PmsProductLadder getProductLadder(int count, List<PmsProductLadder> ladders) {
        ladders.sort( (o1, o2) -> o1.getCount() - o2.getCount());
        for (PmsProductLadder ladder : ladders) {
            if (count - ladder.getCount() >= 0) {
                return ladder;
            }
        }
        return null;
    }

    /**
     * 获取购物车中指定商品数量
     */
    private int getCartItemCount(List<OmsCartItem> items) {
        int count = 0;
        for (OmsCartItem item : items) {
            count += item.getQuantity();
        }
        return count;
    }

    /**
     * 根据商品id获取商品的促销信息
     * @return
     */
    private PromotionProduct getPromotionProductByProductId(Long id, List<PromotionProduct> promotionProducts) {
        return promotionProducts.stream().filter( item -> item.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * 获取购物车中指定商品的总价
     */
    private BigDecimal getCartItemAmount(List<OmsCartItem> cartItems, List<PromotionProduct> promotionProducts) {
        BigDecimal amount = new BigDecimal(0);
        for (OmsCartItem item : cartItems) {
            PromotionProduct promotionProduct = getPromotionProductByProductId(item.getProductId(), promotionProducts);
            PmsSkuStock sku = getOriginalPrice(promotionProduct, item.getProductSkuId());
            amount = amount.add(sku.getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        return amount;
    }





}
