package com.soft.web.controller;

import com.soft.web.common.MyListUtils;
import com.soft.web.common.PageDomain;
import com.soft.web.service.ProductCategoryService;
import com.soft.web.service.ProductinfoService;
import com.soft.web.vo.CateGoryVO;
import com.soft.web.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin
public class ProductCategoryController {

    @Autowired
    ProductCategoryService productCategoryService;
    @Autowired
    ProductinfoService productinfoService;

    @GetMapping("/toplist")
    public List<CateGoryVO> toplist() {
        return productCategoryService.selectTopCategory();
    }

    @GetMapping("/secondlist")
    public List<CateGoryVO> secondlist(Long pid) {
        return productCategoryService.selectSecondCategory(pid);
    }

    @GetMapping("/goods")
    public Object goods(int cid, PageDomain pageDomain) {
        List<GoodsVO> goodsVOS = productinfoService.selectGoods(cid, pageDomain);
        return MyListUtils.getDataTable(goodsVOS);
    }
}
