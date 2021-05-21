package com.soft.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soft.web.entity.Order;
import com.soft.web.entity.User;
import com.soft.web.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Mapper
@Repository
public interface OrderMapper extends BaseMapper<Order> {

    public Integer addOrder(Order order);

    public Order selectByUserId(@Param("userId")Long userId);

    public List<GoodsVO> selectConfirmByIdLimit(@Param("id")Long id);

    public List<GoodsVO> selectConfirmById(@Param("id")Long id);

    public int updateAddress(AddressVO addressVO);

    public User selectUserIdById(@Param("id")Long id);

    public RemarkVO selectComments(@Param("id")Long id);

    public int updateComments(Order order);

    public BigDecimal selectSumsByOid(@Param("oid")Long oid);

    public Order selectStatusById(@Param("id")Long id);

    public int updateOrderByOrderId(Order order);

    public int updateCartById(Order order);

    public AddressAbouts selectAddressAboutsById(@Param("id")Long id);

    public List<MyOrdersVO> selectOrderByType(Order order);

    public List<MyOrdersVO> selectOrderByTypeLimit(Order order);

    public List<MyOrderDetailsVO> selectOrderDetailByType(@Param("id")Long id);

    public List<MyOrderDetailsVO> selectOrderDetailByTypeLimit(@Param("id")Long id);

    public MyProductinfo selectLableByType(@Param("id")Long id);

    public int updateOrderAddress(EditAddressVO editAddrVO);

    public Order selectCommentsById(@Param("id")Long id);
}
