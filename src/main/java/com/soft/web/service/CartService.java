package com.soft.web.service;

import com.alibaba.fastjson.JSON;
import com.soft.web.common.AjaxResult;
import com.soft.web.common.MyListUtils;
import com.soft.web.common.PageDomain;
import com.soft.web.dao.*;
import com.soft.web.entity.*;
import com.soft.web.utils.Convert;
import com.soft.web.utils.OrderUtils;
import com.soft.web.vo.AddCartVO;
import com.soft.web.vo.CartVO;
import com.soft.web.vo.GoodsVO;
import com.soft.web.vo.ShopCartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author md
 * @since 2021-03-30
 */
@Service
public class CartService {

    @Autowired
    CartMapper cartMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ProductinfoMapper productinfoMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderDetailMapper orderDetailMapper;
    @Autowired
    UserAddressMapper userAddressMapper;

    public Object selectListByUserId(Long userId) {
        if(userId == null) {
            return AjaxResult.error("请先登录");
        }

        HashMap hashMap = new HashMap();
        hashMap.put("userId", userId);
        List<AddCartVO> cartVOS = cartMapper.selectListByUserId(hashMap);
        for (AddCartVO cartVO : cartVOS) {
            if(cartVO.getSpecialofferprice() != null && cartVO.getSpecialoffer() == 1) {
                cartVO.setPrice(cartVO.getSpecialofferprice());
            }
        }
        return MyListUtils.getDataTable(cartVOS);
    }

    public AjaxResult removeCart(String ids, Long userId) {
        if(userId == null) {
            return AjaxResult.error("请先登录");
        }

        Long[] sids = Convert.toLongArray(ids);
        List<Long> longList = (Arrays.asList(sids));
        int result = cartMapper.removeCart(sids);
        if(result > 0) {
            return AjaxResult.success("删除成功");
        }
        return AjaxResult.error("删除失败");
    }

    public AjaxResult addCart(Long productId, Long userId) {
        if(userId==null) {
            return AjaxResult.error("请先登录");
        }

        User user = userMapper.selectUserById(userId);
        GoodsVO goodsVO = productinfoMapper.selectGoodsById(productId);

        if(userId==null || user==null){
            return AjaxResult.error("请先登录");
        }
        if(productId!=null) {
            CartVO cartVO = new CartVO();
            cartVO.setUserId(userId);
            cartVO.setProductId(productId);
            List<Cart> carts = cartMapper.selectCartByProId(cartVO);

            int result = 0;
            if(carts.size() > 0) {
                result = cartMapper.updateCartCount(productId);
            } else {
                Cart cart = new Cart();
                cart.setProductId(productId);
                cart.setUserId(userId);
                cart.setCreateTime(new Date());
                result = cartMapper.addProductCart(cart);
            }

            if(result > 0) {
                Integer sums = cartMapper.selectCartCounts(userId);
                return AjaxResult.success("添加购物车成功", sums);
            }
            return AjaxResult.error("添加购物车失败", -1);
        } else {
            Integer sums = cartMapper.selectCartCounts(userId);
            return AjaxResult.success(sums);
        }
    }

    /**
     *
     * @param ids
     * @param userId
     * @return
     */
    @Transactional
    public AjaxResult cartok(String ids, Long userId) {
        try{
            if(userId == null) {
                return AjaxResult.error("请先登录");
            }
            //  价格合计
            BigDecimal priceSums = new BigDecimal(0);
            //  商品数量合计
            Integer countSums = 0;
            //  重量总计
            BigDecimal measurement = new BigDecimal(0);

            List<ShopCartVO> list = JSON.parseArray(ids,ShopCartVO.class);

            if(ids == null || list.size() == 0) {
                return AjaxResult.error("请选择商品提交");
            }

            String oid = OrderUtils.createBS();

            for (ShopCartVO shopCartVO : list) {
                //  计算商品数量并更新购物车中的订单id
                countSums += shopCartVO.getNum();
                Order order = new Order();
                order.setId(shopCartVO.getGid());
                order.setOrderId(oid);
                orderMapper.updateCartById(order);

                Productinfo product = productinfoMapper.selectProductById(shopCartVO.getId());
                String weight = product.getMeasurement();
                if(weight.endsWith("千克") || weight.endsWith("kg")){
                    BigDecimal weightDecimal = new BigDecimal(weight.substring(0, weight.length() - 2));
                    measurement = measurement.add(weightDecimal);
                } else if(weight.endsWith("克") || weight.endsWith("g")){
                    BigDecimal weightDecimal = new BigDecimal(weight.substring(0, weight.length()-1)).divide(new BigDecimal(1000));
                    measurement = measurement.add(weightDecimal);
                } else {
                    BigDecimal weightDecimal = new BigDecimal(weight.substring(0, weight.length())).divide(new BigDecimal(1000));
                    measurement = measurement.add(weightDecimal);
                }
                if(product!=null && product.getSpecialoffer()==1) {
                    BigDecimal specialofferprice = product.getSpecialofferprice();
                    BigDecimal multiply = specialofferprice.multiply(new BigDecimal(shopCartVO.getNum()));
                    priceSums = priceSums.add(multiply);
                } else {
                    BigDecimal price = product.getPrice();
                    BigDecimal multiply = price.multiply(new BigDecimal(shopCartVO.getNum()));
                    priceSums = priceSums.add(multiply);
                }
            }

            //  生成订单
            User user = userMapper.selectByUserId(userId);
            Order order = new Order();
            order.setUserId(userId);
            order.setOrderId(oid);
            order.setUserAccount(user.getUserAccount());
            order.setUserNick(user.getUserNick());
            order.setTrueName(user.getTrueName());
            order.setTotalWeight(measurement);
            order.setMobileNumber(user.getMobileNumber());
            order.setProductCount(countSums);
            order.setPayStatus(0);
            order.setIsBackpay(0);
            order.setOrderStatus(1);
            order.setProductAmount(priceSums);

            UserAddress userAddress = userAddressMapper.selectByUserId(userId);
            if(userAddress!=null) {
                order.setAddress(userAddress.getReceiverAddress());
                order.setReceiveName(userAddress.getReceiveName());
                order.setReceiveTelphone(userAddress.getReceiverMobile());
            }
            int orderResult = orderMapper.insert(order);

            //  添加订单后返回id
            Long orderId = order.getId();

            //  生成订单明细
            for (ShopCartVO shopCartVO : list) {
                GoodsVO goodsVO = productinfoMapper.selectGoodsById(shopCartVO.getId());
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setProductId(goodsVO.getId());
                orderDetail.setProductName(goodsVO.getProductName());
                orderDetail.setProductIcon(goodsVO.getPic());
                orderDetail.setProductPrice(goodsVO.getPrice());
                orderDetail.setProductQuantity(shopCartVO.getNum());
                orderDetail.setMeasurement(goodsVO.getMeasurement());
                if(goodsVO.getSpecialoffer() == 1 && goodsVO.getSpecialofferprice() != null) {
                    orderDetail.setSpecialofferprice(goodsVO.getSpecialofferprice());
                } else {
                    orderDetail.setSpecialofferprice(goodsVO.getPrice());
                }
                orderDetail.setOid(orderId);
                orderDetail.setOrderId(oid);
                orderDetailMapper.insert(orderDetail);
            }

            if(orderResult > 0) {
                return AjaxResult.success("提交成功", orderId);
            }
            return AjaxResult.error("提交失败");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("提交失败");
        }
    }

    public AjaxResult subcart(Long productId, Long userId) {
        if(userId == null) {
            return AjaxResult.error("请先登录");
        }
        
        CartVO cartVO = new CartVO();
        cartVO.setUserId(userId);
        cartVO.setProductId(productId);
        List<Cart> carts = cartMapper.selectCartByProId(cartVO);

        if(carts.size() == 0) {
            return AjaxResult.error("删除失败，请选择正确的商品");
        } else {
            for (Cart cart : carts) {
                cart.setUserId(userId);
                cart.setProductId(productId);
                Cart cartCount = cartMapper.selectProductCount(cart);

                int count = cartCount.getProductCount();

                System.out.println(count);

                if(count == 1) {
                    cartMapper.deleteById(cart.getId());
                    Integer sums = cartMapper.selectCartCounts(userId);
                    return AjaxResult.success("success", sums);
                }else {
                    cartMapper.updateProductCount(cart);
                    Integer sums = cartMapper.selectCartCounts(userId);
                    return AjaxResult.success("success", sums);
                }
            }
        }
        return AjaxResult.error();
    }
}
