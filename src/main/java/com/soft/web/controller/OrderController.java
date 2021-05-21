package com.soft.web.controller;

import com.soft.web.common.AjaxResult;
import com.soft.web.config.ReturnData;
import com.soft.web.entity.Comments;
import com.soft.web.service.OrderService;
import com.soft.web.vo.AddressVO;
import com.soft.web.vo.EditAddressVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/confirm")
    public Object confirm(Long id, Long uid) {
        return orderService.selectConfirmById(id, uid);
    }

    @PostMapping("/address")
    public Object address(Long uid) {
        return orderService.address(uid);
    }

    @PostMapping("/saveaddr")
    public AjaxResult addrsave(AddressVO addressVO, Long uid) {
        return orderService.updateAddress(addressVO, uid);
    }

    @PostMapping("/remark")
    public Object remark(Long id, Long uid) {
        return orderService.selectComments(id, uid);
    }

    @PostMapping("/saveremark")
    public AjaxResult remark(Long id, String remark, Long uid) {
        return orderService.updateComments(id, remark, uid);
    }

    @PostMapping("/detail")
    public Object detail(Long id, Long uid) {
        return orderService.selectDetail(id, uid);
    }

    @PostMapping("/weixinpay")
    public ReturnData wechatpay(HttpServletRequest request, Long id, String code, Long uid) {
        return orderService.WechatPay(request, id, code, uid);
    }

    /**
     * 回调
     */
    @PostMapping("/wechatPayBack")
    public String payBack(HttpServletRequest request) {
        return orderService.payBack(request);
    }

    @PostMapping("/editaddr")
    public AjaxResult editaddr(EditAddressVO editAddrVO) {
        return orderService.editaddr(editAddrVO);
    }

    @PostMapping("/timeout")
    public AjaxResult timeout(Long id) {
        return orderService.timeout(id);
    }

    @GetMapping("/comments")
    public Object getComments() {
        return orderService.getComments();
    }
}
