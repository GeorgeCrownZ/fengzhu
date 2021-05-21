package com.soft.web.service;

import com.soft.web.common.MyListUtils;
import com.soft.web.common.PageDomain;
import com.soft.web.dao.ProductCategoryMapper;
import com.soft.web.dao.ProductinfoMapper;
import com.soft.web.dao.RollpicMapper;
import com.soft.web.entity.Productinfo;
import com.soft.web.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author md
 * @since 2021-03-30
 */
@Service
public class ProductinfoService {

    @Autowired
    ProductinfoMapper productinfoMapper;
    @Autowired
    RollpicMapper rollpicMapper;
    @Autowired
    ProductCategoryMapper productCategoryMapper;

    //  查询所有的特价商品
    public List<TodayVO> selectSpecialoffer() {
        return productinfoMapper.selectSpecialoffer();
    }

    //  根据id查询商品信息
    public GoodsVO selectGoodsById(Long productId) {
        return productinfoMapper.selectGoodsById(productId);
    }

    //  根据商品分类id获取商品list
    public List<GoodsVO> selectGoodsByCategoryId(Long productCategoryId) {
        return productinfoMapper.selectGoodsByCategoryId(productCategoryId);
    }

    //  商品详情
    public GoodsinfoVO goodsinfo(Long productId) {
        GoodsinfoVO goodsinfoVO = productinfoMapper.selectGoodsByProId(productId);

        if(goodsinfoVO.getSpecialoffer() == 0) {
            goodsinfoVO.setSpecialofferprice(goodsinfoVO.getPrice());
        }

        List<GoodsPicVO> goodsPicVOS = productinfoMapper.selectPicByProId(productId);
        List<GoodsSpecVO> goodsSpecVOS = productinfoMapper.selectSpecByProId(productId);
        List<GoodsParam> goodsParams = productinfoMapper.selectParamByProId(productId);
        List<DetailVO> detailVOS = productinfoMapper.selectDetailByProId(productId);

        goodsinfoVO.setGoodspic(goodsPicVOS);
        goodsinfoVO.setSpec(goodsSpecVOS);
        goodsinfoVO.setGoodsparam(goodsParams);
        goodsinfoVO.setDetail(detailVOS);

        return goodsinfoVO;
    }

    public List<GoodsVO> selectGoods() {
        return productinfoMapper.selectGoods();
    }

    public List<GoodsVO> selectGoods(int cid, PageDomain pageDomain) {
        HashMap hashMap = new HashMap();
        if(pageDomain.getPageNum() == 1) {
            pageDomain.setPageNum(pageDomain.getPageNum()-1);
        }
        hashMap.put("cid", cid);
        hashMap.put("page", pageDomain);
        List<GoodsVO> goodsVOS = productinfoMapper.selectGoodsByCid(hashMap);
        for (GoodsVO goodsVO : goodsVOS) {
            if(goodsVO.getSpecialoffer() == 1 && goodsVO.getSpecialofferprice() != null) {
                goodsVO.setPrice(goodsVO.getSpecialofferprice());
            }
        }
        return goodsVOS;
    }

    public Object selectGoodsByName(String productName, PageDomain pageDomain) {
        HashMap hashMap = new HashMap();
        if(pageDomain.getPageNum() == 1) {
            pageDomain.setPageNum(pageDomain.getPageNum()-1);
        }
        hashMap.put("productName", productName);
        hashMap.put("page", pageDomain);

        List<GoodsVO> goodsVOS = productinfoMapper.selectGoodsByName(hashMap);
        for (GoodsVO goodsVO : goodsVOS) {
            if(goodsVO.getSpecialoffer()!= null && goodsVO.getSpecialoffer() == 1) {
                goodsVO.setPrice(goodsVO.getSpecialofferprice());
            }
        }
        return MyListUtils.getDataTable(goodsVOS);
    }



}
