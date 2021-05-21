package com.soft.web.controller;

import com.soft.web.service.ExpressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/express")
@CrossOrigin
public class ExpressController {

    @Autowired
    ExpressService expressService;

    @PostMapping("/orderAbouts")
    public Object orderAbouts(Long uid, Long id) {
        return expressService.orderAbouts(uid, id);
    }

    @PostMapping("/expressAbouts")
    public Object expressAbouts(Long uid, Long id) throws Exception {
        return expressService.expressAbouts(uid, id);
    }
}
