package com.soft.web.controller;

import com.soft.web.common.PageDomain;
import com.soft.web.service.ProductinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@CrossOrigin
public class SearchController {

    @Autowired
    ProductinfoService productinfoService;

    @PostMapping("/list")
    public Object list(PageDomain pageDomain, String key) {
        return productinfoService.selectGoodsByName(key, pageDomain);
    }
}
