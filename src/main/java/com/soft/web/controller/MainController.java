package com.soft.web.controller;


import com.soft.web.common.AjaxResult;
import com.soft.web.entity.Global;
import com.soft.web.service.*;
import com.soft.web.vo.CateGoryVO;
import com.soft.web.vo.GoodsVO;
import com.soft.web.vo.RollPicVO;
import com.soft.web.vo.TodayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author md
 * @since 2021-03-30
 */
@CrossOrigin
@RestController
@RequestMapping("/main")
public class MainController {

    @Autowired
    ProductinfoService productinfoService;
    @Autowired
    RollpicService rollpicService;
    @Autowired
    ProductCategoryService productCategoryService;
    @Autowired
    CartService cartService;
    @Autowired
    GlobalSettingsService globalService;
    @Autowired
    UserService userService;

    @GetMapping("/getglobal")
    public Global getglobal() {
        return globalService.getGlobal();
    }

    @GetMapping("/checkUser")
    public AjaxResult checkUser(Long uid) {
        return userService.checkUser(uid);
    }

    @GetMapping("/today")
    public List<TodayVO> today() {
        return productinfoService.selectSpecialoffer();
    }

    @GetMapping("/rollpic")
    public List<RollPicVO> rollpic() {
        return rollpicService.selectRollPics();
    }

    @GetMapping("/category")
    public List<CateGoryVO> category() {
        return productCategoryService.selectTopCategory();
    }

    @GetMapping("/goods")
    public List<GoodsVO> goods() {
        return productinfoService.selectGoods();
    }

    @PostMapping("/addcart")
    public AjaxResult addcart(Long pid, Long uid) {
        return cartService.addCart(pid, uid);
    }

    @PostMapping("/subcart")
    public AjaxResult subcart(Long pid, Long uid) {
        return cartService.subcart(pid, uid);
    }
}

