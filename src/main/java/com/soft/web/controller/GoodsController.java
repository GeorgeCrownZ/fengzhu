package com.soft.web.controller;

import com.soft.web.service.ProductinfoService;
import com.soft.web.vo.GoodsinfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goods")
@CrossOrigin
public class GoodsController {

    @Autowired
    ProductinfoService productinfoService;

    @GetMapping("/goodsinfo")
    public GoodsinfoVO goodsinfo(Long id) {
        return productinfoService.goodsinfo(id);
    }
}
