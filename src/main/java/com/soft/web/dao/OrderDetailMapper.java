package com.soft.web.dao;

import com.soft.web.entity.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author md
 * @since 2021-04-06
 */
@Mapper
@Repository
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

}
