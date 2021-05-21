package com.soft.web.dao;

import com.soft.web.entity.Cart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soft.web.vo.AddCartVO;
import com.soft.web.vo.CartVO;
import com.soft.web.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author md
 * @since 2021-03-30
 */
@Mapper
@Repository
public interface CartMapper extends BaseMapper<Cart> {

    public List<AddCartVO> selectListByUserId(HashMap hashMap);

    public int removeCart(@Param("ids")Long[] ids);

    public int updateCartCount(@Param("productId")Long productId);

    public int addProductCart(Cart cart);

    public List<Cart> selectCartByProId(CartVO cartVO);

    public Integer selectCartCounts(@Param("userId")Long userId);

    public OrderVO selectCartById(@Param("id")Long id);

    public int deleteByOrderId(@Param("orderId")String orderId);

    public Cart selectProductCount(Cart cart);

    public int updateProductCount(Cart cart);

}
