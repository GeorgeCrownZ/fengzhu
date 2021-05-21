package com.soft.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalWechatVO {

    private Integer wechatAppid;

    private String wechatMerchantNum;

    private String wechatToken;

    private String wechatCallbackUrl;

    private String wechatCertPem;

    private String wechatKeyPem;
}
