package com.soft.web.dao;

import com.soft.web.entity.ProductCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soft.web.vo.CateGoryVO;
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
 * @since 2021-03-30
 */
@Repository
@Mapper
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {

    public List<CateGoryVO> selectTopCategory();

    public List<CateGoryVO> selectSecondCategory(@Param("pid")Long pid);
}
