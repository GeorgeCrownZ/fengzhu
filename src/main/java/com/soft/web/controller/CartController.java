package com.soft.web.controller;

import com.soft.web.common.AjaxResult;
import com.soft.web.common.PageDomain;
import com.soft.web.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author md
 * @since 2021-03-30
 */
@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/list")
    public Object list(Long uid) {
        return cartService.selectListByUserId(uid);
    }

    @PostMapping("/remove")
    public AjaxResult remove(String ids, Long uid) {
        return cartService.removeCart(ids, uid);
    }

    @PostMapping("/cartok")
    public AjaxResult cartok(String ids, Long uid) {
        return cartService.cartok(ids, uid);
    }
}

