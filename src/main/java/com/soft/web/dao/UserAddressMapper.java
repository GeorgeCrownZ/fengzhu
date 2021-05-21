package com.soft.web.dao;

import com.soft.web.entity.UserAddress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soft.web.vo.AddressVO;
import com.soft.web.vo.EditAddressVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
public interface UserAddressMapper extends BaseMapper<UserAddress> {

    public List<AddressVO> selectAddressesByUserId(@Param("userId")Long userId);

    public UserAddress selectByUserId(@Param("userId")Long userId);

    public UserAddress selectDefaultById(@Param("userId")Long userId);

    public int updateAddress(EditAddressVO editAddrVO);
}
