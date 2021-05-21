package com.soft.web.dao;

import com.soft.web.entity.ProductCategory;
import com.soft.web.entity.Productinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soft.web.vo.*;
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
@Repository
@Mapper
public interface ProductinfoMapper extends BaseMapper<Productinfo> {

    public List<TodayVO> selectSpecialoffer();

    public List<GoodsVO> selectGoodsByCategoryId(@Param("productCategoryId")Long productCategoryId);

    public GoodsVO selectGoodsById(@Param("productId")Long productId);

    public List<GoodsVO> selectGoods();

    public List<GoodsVO> selectGoodsByCid(HashMap map);

    public GoodsinfoVO selectGoodsByProId(@Param("productId")Long productId);

    public List<GoodsPicVO> selectPicByProId(@Param("productId")Long productId);

    public List<GoodsSpecVO> selectSpecByProId(@Param("productId")Long productId);

    public List<GoodsParam> selectParamByProId(@Param("productId")Long productId);

    public List<DetailVO> selectDetailByProId(@Param("productId")Long productId);

    public Productinfo selectProductById(@Param("productId")Long productId);

    public List<GoodsVO> selectGoodsByName(HashMap map);
}
