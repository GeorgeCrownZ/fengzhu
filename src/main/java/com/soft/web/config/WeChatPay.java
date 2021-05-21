package com.soft.web.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "remote", ignoreUnknownFields = false)
@PropertySource("classpath:config/paySettings.properties")
public class WeChatPay {

    @Value("${wechat.AM_APPID}")
    private String AM_APPID;
    @Value("${wechat.AM_MCH_ID}")
    private String AM_MCH_ID;
    @Value("${wechat.AM_MCH_KEY}")
    private String AM_MCH_KEY;
    @Value("${wechat.AM_Secret}")
    private String AM_Secret;
    @Value("${wechat.requestPath}")
    private String requestPath;
    @Value("${wechat.ACCESS_TOKEN_URL}")
    private String ACCESS_TOKEN_URL;
    @Value("${wechat.USER_INFO_URL}")
    private String USER_INFO_URL;
    @Value("${wechat.DOWN_LOAD_PATH}")
    private String DOWN_LOAD_PATH;
    @Value("${qrcode.UP_LOAD_PATH}")
    private String QRCODE_UP_LOAD_PATH;
    @Value("${user.HEAD_DOWN_LOAD_PATH}")
    private String USER_HEAD_DOWN_LOAD_PATH;
}
