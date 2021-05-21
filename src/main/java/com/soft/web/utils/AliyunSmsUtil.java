package com.soft.web.utils;

import java.util.HashMap;
import java.util.Map;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class AliyunSmsUtil {

    /**
     * 产品名称:云通信短信API产品
     */
    private static final String PRODUCT = "Dysmsapi";

    /**
     * 产品域名
     */
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";

    /**
     * todo 开发者自己的AK(在阿里云访问控制台寻找)
     */
    private static final String ACCESS_KEY_ID = "LTAI5tBmqaaep27ZiJiFnomS";

    /**
     * todo accessKeySecret(在阿里云访问控制台寻找)
     */
    private static final String ACCESS_KEY_SECRET = "sUAefNdlvS23Ur61OB5IX3aBU6sw45";

    /**
     * todo 必填:短信签名
     */
    //private static final String SIGN_NAME = "丰猪科技";

    /**
     * todo 必填:REGION_ID
     */
    private static final String REGION_ID = "cn-hangzhou";

    /**
     * 发送方法
     * @param phone 电话号码
     * @param templateCode 模板编号
     * @param templateParam 模板中的参数
     *
     * @return 返回值
     * @throws ClientException 异常
     */
    public static SendSmsResponse sendSms(String phone, String templateCode, String templateParam, String signName) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile(REGION_ID, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        DefaultProfile.addEndpoint(REGION_ID, REGION_ID, PRODUCT, DOMAIN);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phone);
        request.setSignName(signName); // 短信签名
        request.setTemplateCode(templateCode);
        request.setTemplateParam(templateParam);

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("10000");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        return sendSmsResponse;
    }

    public static String send(String phones, String templateCode, String singName) throws ClientException {
        Map<String,String> paramMap = new HashMap<String,String>();
        //map.put("name",name);
        //  随机获取6位随机数
        String code = createRandomVcode();
        paramMap.put("code", code);
        String templateParam= com.alibaba.fastjson.JSONObject.toJSONString(paramMap);

        //模板编号，不同场景，编号不同（如注册，找回密码，密码登录等）
        SendSmsResponse response = sendSms(phones,templateCode,templateParam, singName);
        return code;
    }

    /**
     * 生成六位随机数
     * @return
     */
    public static String createRandomVcode() {
        //验证码
        StringBuilder vcode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            vcode.append((int) (Math.random() * 9));
        }
        return vcode.toString();
    }

    public static void main(String[] args) throws ClientException {
        send("13341499179", "SMS_205251215", "丰猪科技");
    }
}