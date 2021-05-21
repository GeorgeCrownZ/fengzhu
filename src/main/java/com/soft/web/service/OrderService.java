package com.soft.web.service;

import com.alibaba.fastjson.JSON;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.soft.web.common.AjaxResult;
import com.soft.web.common.MyListUtils;
import com.soft.web.config.ReturnData;
import com.soft.web.config.WeChatConfig;
import com.soft.web.constant.SmsConstants;
import com.soft.web.dao.*;
import com.soft.web.entity.Comments;
import com.soft.web.entity.Order;
import com.soft.web.entity.User;
import com.soft.web.entity.UserAddress;
import com.soft.web.utils.DownloadImageUtil;
import com.soft.web.utils.EmojiFilter;
import com.soft.web.utils.HttpGetUtil;
import com.soft.web.utils.TimeUtils;
import com.soft.web.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserAddressMapper userAddressMapper;
    @Autowired
    SmsConstants smsConstants;
    @Autowired
    CartMapper cartMapper;
    @Autowired
    DownloadImageUtil downloadImageUtil;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentsMapper commentsMapper;

    public List<GoodsVO> selectConfirmByIdLimit(Long id) {
        return orderMapper.selectConfirmByIdLimit(id);
    }

    public Object selectConfirmById(Long id, Long userId) {
        if(userId == null) {
            return AjaxResult.error("请先登录");
        }

        //每3公斤12元，满99减12，多余部分多算3公斤
        List<GoodsVO> goodsVOList = orderMapper.selectConfirmById(id);
        //  总价
        BigDecimal price = BigDecimal.ZERO;
        //  重量
        BigDecimal weight = BigDecimal.ZERO;
        //  邮费
        BigDecimal freight = new BigDecimal(12);
        for (GoodsVO goodsVO : goodsVOList) {
            String measurement = goodsVO.getMeasurement();
            if(measurement.endsWith("千克") || measurement.endsWith("kg")){
                weight = weight.add(new BigDecimal(measurement.substring(0,measurement.length()-2)));
            } else if(measurement.endsWith("克") || measurement.endsWith("g")) {
                weight = weight.add(new BigDecimal(measurement.substring(0,measurement.length()-1)).divide(new BigDecimal(1000)));
            } else {
                weight = weight.add(new BigDecimal(measurement.substring(0,measurement.length())).divide(new BigDecimal(1000)));
            }
            price = price.add(goodsVO.getSums());
        }
        if(weight.compareTo(new BigDecimal(3)) > -1){
            freight = freight.subtract(new BigDecimal(12));
            if(weight.divide(new BigDecimal(3),0,BigDecimal.ROUND_UP).compareTo(new BigDecimal(1)) == 0) {
                freight = freight.add(weight.divide(new BigDecimal(3),0, RoundingMode.UP).multiply(new BigDecimal(12)));
            } else {
                freight = freight.add(weight.divide(new BigDecimal(3),0, RoundingMode.UP)).multiply(new BigDecimal(12));
            }
        }

        if(price.compareTo(new BigDecimal(99)) == 1) {
            freight = freight.subtract(new BigDecimal(12));
        }

        Order order = new Order();
        order.setExpressFee(freight);
        order.setId(id);
        orderMapper.updateById(order);
        System.out.println("运费===================================>"+freight);

        AddressAbouts addressAbouts = orderMapper.selectAddressAboutsById(id);
        if(addressAbouts == null) {
            return MyListUtils.getDataTable(selectConfirmByIdLimit(id), goodsVOList.size(), price, freight, null, null);
        }
        return MyListUtils.getDataTable(selectConfirmByIdLimit(id), goodsVOList.size(), price, freight,
                addressAbouts.getSystemComments(), addressAbouts.getReceiveName(), addressAbouts.getReceiveTelphone(), addressAbouts.getAddress(), addressAbouts.getComments());
    }

    public Object address(Long userId) {
        if(userId == null) {
            return AjaxResult.error("请先登录");
        }

        return MyListUtils.getDataTable(userAddressMapper.selectAddressesByUserId(userId));
    }

    @Transactional
    public AjaxResult updateAddress(AddressVO addressVO, Long userId) {
        try{
            if(userId == null) {
                return AjaxResult.error("请先登录");
            }

            UserAddress user = userAddressMapper.selectDefaultById(userId);

            if(user != null) {
                user.setUserId(userId);
                user.setIsDefault(0);
                userAddressMapper.updateById(user);
            }
            UserAddress userAddress = new UserAddress();
            System.out.println("------------------------------------");
            System.out.println(addressVO);
            System.out.println("------------------------------------");

            userAddress.setUserId(userId);
            userAddress.setIsDefault(addressVO.getIsdefault());
            System.out.println("isDefault================================>" + addressVO.getIsdefault());
            userAddress.setReceiveName(addressVO.getName());
            userAddress.setReceiverMobile(addressVO.getMobile());
            userAddress.setReceiverAddress(addressVO.getAddress());
            userAddressMapper.insert(userAddress);

            addressVO.setId(addressVO.getId());

            int result = orderMapper.updateAddress(addressVO);
            if(result > 0) {
                return AjaxResult.success("修改收货地址成功", addressVO.getId());
            }
            return AjaxResult.error("修改收货地址失败");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("修改收货地址失败");
        }
    }

    public Object selectComments(Long id, Long userId) {
        if(userId == null) {
            return AjaxResult.error("请先登录");
        }

        return orderMapper.selectComments(id);
    }

    public AjaxResult updateComments(Long id, String comments, Long userId) {
        if(userId == null) {
            return AjaxResult.error("请先登录");
        }

        Order order = new Order();
        order.setId(id);
        order.setComments(comments);

        int result = orderMapper.updateComments(order);
        if(result > 0) {
            return AjaxResult.success("修改备注成功", id);
        }
        return AjaxResult.error("修改备注失败");
    }

    public Object selectDetail(Long id, Long userId) {
        if(userId == null) {
            return AjaxResult.error("请先登录");
        }

        return MyListUtils.getDataTable(orderMapper.selectConfirmById(id));
    }

    @Transactional
    //
    public ReturnData WechatPay(HttpServletRequest request, Long id, String code, Long userId) {
        try {
            //  判断用户是否为登录状态
            if(userId == null) {
                ReturnData returnData = ReturnData.error();
                returnData.put("type", false);
                returnData.put("msg", "请先登录");
                returnData.put("errorCode", "000");
            }

            smsConstants.init();

            Order orderAbouts = orderMapper.selectById(id);

            Date createTime = orderAbouts.getCreateTime();
            if(!TimeUtils.time(createTime, 30)) {
                Order order = new Order();
                order.setOrderStatus(999);
                order.setId(id);
                orderMapper.updateById(order);
                ReturnData returnData = ReturnData.error();
                returnData.put("type", false);
                returnData.put("msg", "订单已超时");
                returnData.put("errorCode", "000");
            }

            Map params1 = new HashMap();
            params1.put("secret", smsConstants.getAM_Secret());
            params1.put("appid", smsConstants.getAM_APPID());
            params1.put("grant_type", "authorization_code");
            params1.put("code", code);

            //  通过get请求向微信发送请求，获取用户的openId
            String result = HttpGetUtil.httpRequestToString("https://api.weixin.qq.com/sns/oauth2/access_token", params1);
            com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) JSON.parse(result);

            //  获得openid
            String openid = jsonObject.get("openid").toString();
            //  获得token
            String access_token = jsonObject.get("access_token").toString();

            User user1 = userMapper.selectUserByOpenId(openid);
            if(user1 != null) {
                User user = new User();
                user.setOpenId("");
                user.setUnionId("");
                user.setId(user1.getId());
                userMapper.updateById(user);
            }

            //  通过openid和token获取用户信息
            //  https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
            HashMap hashMap = new HashMap();
            hashMap.put("access_token", access_token);
            hashMap.put("openid", openid);
            hashMap.put("lang", "zh_CN");
            String fresult = HttpGetUtil.httpRequestToString("https://api.weixin.qq.com/sns/userinfo", hashMap);
            com.alibaba.fastjson.JSONObject object = (com.alibaba.fastjson.JSONObject) JSON.parse(fresult);

            String nickname = EmojiFilter.filterEmoji(object.get("nickname").toString());
            String sex = object.get("sex").toString();
            if(Integer.valueOf(sex) == 0) {
                sex = "女";
            } else {
                sex = "男";
            }
            String headimgurl = object.get("headimgurl").toString();
            String unionid = object.get("unionid").toString();

            User user = new User();
            user.setId(userId);
            user.setUserNick(nickname);
            user.setSex(sex);
            user.setOpenId(openid);
            user.setUnionId(unionid);

            downloadImageUtil.download(headimgurl, openid + ".jpg");
            user.setHeadPic("/upload/wechat/" + openid + ".jpg");

            userMapper.updateById(user);

            BigDecimal byOid = orderMapper.selectSumsByOid(id);
            int price = byOid.multiply(new BigDecimal(100)).add(orderAbouts.getExpressFee().multiply(new BigDecimal(100))).intValue();
            WeChatConfig config = new WeChatConfig(smsConstants.getAM_APPID(), smsConstants.getAM_MCH_ID(), smsConstants.getAM_MCH_KEY(), "");

            Map<String, String> data = new HashMap();

            data.put("body", "订单号："+orderAbouts.getOrderId());//支付时候显示的名称
            data.put("out_trade_no", orderAbouts.getOrderId());//数据库内的订单号
            data.put("device_info", "WEB");
            data.put("fee_type", "CNY");//货币种类

            String total_fee = Integer.toString(price);//金额

            data.put("total_fee", total_fee);//单位为分  只能为整数
            data.put("spbill_create_ip", request.getRemoteHost());
            data.put("trade_type", "JSAPI");
            data.put("openid", openid);//openid

            Order status = orderMapper.selectStatusById(id);
            if((status.getIsBackpay()==null || status.getIsBackpay()==0) && status.getPayStatus()==0 && status.getOrderStatus()==1) {
                data.put("notify_url", smsConstants.getRequestPath() + "/api/order/wechatPayBack");//支付完成后回调地址 接口
            } else {
                System.out.println("订单状态错误");
                ReturnData returnData = ReturnData.error();
                returnData.put("type", false);
                returnData.put("msg", "服务器错误，请稍后重试");
                returnData.put("errorCode", "000");
                return returnData;
            }

            WXPay wxpay = new WXPay(config);
            Map<String, String> resp = wxpay.unifiedOrder(data);
            System.out.println("resp================>"+ resp.get("result_code") + "+++" +resp.get("return_msg"));

            if ("SUCCESS".equals(resp.get("result_code")) && "OK".equals(resp.get("return_msg"))) {
                String timestamp = System.currentTimeMillis() / 1000L + "";
                SortedMap<String, String> finalpackage = new TreeMap<String, String>();
                String packages = "prepay_id=" + resp.get("prepay_id");
                finalpackage.put("appId", resp.get("appid"));
                finalpackage.put("timeStamp", timestamp);
                finalpackage.put("nonceStr", resp.get("nonce_str"));
                finalpackage.put("package", packages);
                finalpackage.put("signType", "MD5");
                String signature = WXPayUtil.generateSignature(finalpackage, config.getKey());
                finalpackage.put("paySign", signature);

                ReturnData returnData = ReturnData.ok();
                returnData.put("code", 200);
                returnData.put("data", finalpackage);
                System.out.println(returnData.get("data"));
                return returnData;

            } else {
                System.out.println("进入失败");
                ReturnData returnData = ReturnData.ok();
                returnData.put("code", 200);
                returnData.put("msg", "支付失败");
                returnData.put("type", true);
                System.out.println(returnData.get("msg"));
                return returnData;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("进入异常");
            e.printStackTrace();
            ReturnData returnData = ReturnData.error();
            returnData.put("type", false);
            returnData.put("msg", "服务器错误，请稍后重试");
            returnData.put("errorCode", "000");
            return returnData;
        }
    }

    @Transactional
    public String payBack(HttpServletRequest request) {
        System.out.println("进入回调函数");
        try {
            // 支付结果通知的xml格式数据
            StringBuilder notifyData = new StringBuilder();
            String inputLine;
            while ((inputLine = request.getReader().readLine()) != null) {
                notifyData.append(inputLine);
            }
            request.getReader().close();

            // 转换成map
            Map<String, String> notifyMap = WXPayUtil.xmlToMap(notifyData.toString());
            final String out_trade_no = notifyMap.get("out_trade_no");
            final String transaction_id = notifyMap.get("transaction_id");
            final BigDecimal total_fee = BigDecimal.valueOf(Integer.valueOf(notifyMap.get("total_fee"))).divide(new BigDecimal(100));

            WeChatConfig config = new WeChatConfig(smsConstants.getAM_APPID(), smsConstants.getAM_MCH_ID(), smsConstants.getAM_MCH_KEY(), "");
            WXPay wxpay = new WXPay(config);
            String resXml;
            if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
                System.out.println("支付成功进入wxpay.isPayResultNotifySignatureValid()");
                Order order = new Order();
                order.setOrderId(out_trade_no);
                order.setPayNo(transaction_id);
                order.setPayStatus(1);
                order.setTradeStatus(2);
                order.setPayTime(new Date());
                order.setPayAmount(total_fee);
                orderMapper.updateOrderByOrderId(order);
                cartMapper.deleteByOrderId(out_trade_no);

                resXml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml> ";

            } else {
                // 签名错误，如果数据里没有sign字段，也认为是签名错误
                resXml = "<xml>return_code><![CDATA[FAIL]]></return_code>return_msg><![CDATA[报文为空]]></return_msg></xml>";
            }
            return resXml;

        } catch (Exception e) {
            e.printStackTrace();
            return "<xml>return_code><![CDATA[FAIL]]></return_code>return_msg><![CDATA[服务器错误]]></return_msg></xml>";
        }
    }

    @Transactional
    public AjaxResult editaddr(EditAddressVO editAddrVO) {
        try{
            if(editAddrVO.getUid() == null) {
                return AjaxResult.error("请先登录");
            }

            if(editAddrVO.getAddress() == null || editAddrVO.getMobile() == null || editAddrVO.getName() == null) {
                return AjaxResult.error("地址、手机号码、收货人姓名不能为空");
            }
            int orderResult = orderMapper.updateOrderAddress(editAddrVO);
            int addressResult = userAddressMapper.updateAddress(editAddrVO);
            if(orderResult > 0 && addressResult > 0) {
                return AjaxResult.success("修改成功",editAddrVO.getId());
            }
            return AjaxResult.error("修改失败");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("修改失败");
        }

    }

    public AjaxResult timeout(Long id) {
        Order order = new Order();
        order.setId(id);
        order.setOrderStatus(999);
        int result = orderMapper.updateById(order);
        if(result > 0) {
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    public Object getComments() {
        List<Comments> comments = commentsMapper.selectAll();
        return MyListUtils.getDataTable(comments);
    }
}
