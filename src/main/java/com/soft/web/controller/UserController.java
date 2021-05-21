package com.soft.web.controller;


import com.aliyuncs.exceptions.ClientException;
import com.soft.web.common.AjaxResult;
import com.soft.web.entity.Global;
import com.soft.web.service.UserService;
import com.soft.web.constant.SmsConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 电商用户表 前端控制器
 * </p>
 *
 * @author md
 * @since 2021-03-30
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    SmsConstants smsConstants;

    @GetMapping("/login")
    public AjaxResult loginSms(String phone) throws ClientException {
        smsConstants.init();
        return userService.sendSms(phone, smsConstants.getLogin() ,smsConstants.getSignName());
    }

    @PostMapping("/login")
    public AjaxResult login(String phone, String password, String smscode) {
        AjaxResult ajaxResult = userService.login(phone, password, smscode);
        return ajaxResult;
    }

    @GetMapping("/regist")
    public AjaxResult registSms(String phone) throws ClientException {
        smsConstants.init();
        return userService.sendSms(phone, smsConstants.getRegister() ,smsConstants.getSignName());
    }

    @PostMapping("/regist")
    public AjaxResult regist(String phone, String smscode, String password, Long pid) throws Exception {
        AjaxResult regist = userService.regist(phone, smscode, password, pid);
        return regist;
    }

    @GetMapping("/findpwd")
    public AjaxResult findpwdSms(String phone) throws ClientException {
        smsConstants.init();
        return userService.sendSms(phone, smsConstants.getSetpwd() ,smsConstants.getSignName());
    }

    @PostMapping("/findpwd")
    public AjaxResult findpwd(String phone, String password, String smscode) {
        return userService.findpwd(phone, password, smscode);
    }

    @GetMapping("/wechatregist")
    public AjaxResult WechatRegist(String code) throws Exception {
        return userService.WechatRegist(code);
    }

    @GetMapping("/wechatphone")
    public AjaxResult WechatPhone(String phone) throws ClientException {
        smsConstants.init();
        return userService.sendSms(phone, smsConstants.getRegister() ,smsConstants.getSignName());
    }

    @PostMapping("/wechatbindphone")
    public AjaxResult WechatByPhone(String phone, String smscode, Long uid) {
        return userService.wechatphone(phone, smscode, uid);
    }

    @GetMapping("/userAgreement")
    public Global userAgreement() {
        return userService.userAgreement();
    }
}

