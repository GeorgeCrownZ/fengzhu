package com.soft.web.constant;

import com.soft.web.dao.GlobalMapper;
import com.soft.web.entity.Global;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Data
public class SmsConstants {

    @Autowired
    GlobalMapper globalMapper;

    private String authentication;
    private String login;
    private String loginException;
    private String register;
    private String setpwd;
    private String changeInformation;
    private String signName;
    //  APPID
    private String AM_APPID;
    //  商户号
    private String AM_MCH_ID;
    //  商户key
    private String AM_MCH_KEY;
    //  密钥
    private String AM_Secret;

    private String requestPath;

    private String DOWN_LOAD_PATH = "/tomcat9/webapps/upload/wechat";
    private String HEAD_DOWN_LOAD_PATH = "/tomcat9/webapps/upload/user/headpic";

    //  快递
    private String authenticationKey;
    private String customer;

    @PostConstruct
    public void init() {
        Global global = globalMapper.selectById(1);
        authentication = global.getAuthenticationCode();
        login = global.getLoginCode();
        loginException = global.getLoginExceptionCode();
        register = global.getRegisterCode();
        setpwd = global.getUpdatePasswordCode();
        changeInformation = global.getInformationChangeCode();
        signName = global.getSmsSignature();
        AM_APPID = global.getWechatAppid();
        AM_MCH_ID = global.getWechatMerchantNum();
        AM_MCH_KEY = global.getWechatMerchantKey();
        AM_Secret = global.getWechatToken();
        requestPath = global.getWechatCallbackUrl();
        authenticationKey = global.getAuthenticationKey();
        customer = global.getCustomer();
    }

    /*//  身份验证验证码
    public static final String authentication = "SMS_205251216";
    //  登录确认验证码
    public static final String login = "SMS_205251215";
    //  登录异常验证码
    public static final String loginException = "SMS_205251214";
    //  用户注册验证码
    public static final String register = "SMS_205251213";
    //  修改密码验证码
    public static final String setpwd = "SMS_205251212";
    //  信息变更验证码
    public static final String changeInformation = "SMS_205251211";*/
}
