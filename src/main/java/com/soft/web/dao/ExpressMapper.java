package com.soft.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soft.web.entity.Express;
import com.soft.web.vo.ExpressGoodsVO;
import com.soft.web.vo.ReceiveVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ExpressMapper extends BaseMapper<Express> {

    public List<ExpressGoodsVO> selectConfirmById(@Param("id")Long id);

    public List<ExpressGoodsVO> selectConfirmByIdLimit(@Param("id")Long id);

    public ReceiveVO selectReceiveByOid(@Param("id")Long id);
}
