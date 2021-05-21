package com.soft.web.service;

import com.google.gson.Gson;
import com.soft.web.common.AjaxResult;
import com.soft.web.common.MyListUtils;
import com.soft.web.constant.CompanyConstant;
import com.soft.web.constant.SmsConstants;
import com.soft.web.dao.OrderMapper;
import com.soft.web.dao.UserMapper;
import com.soft.web.entity.Express;
import com.soft.web.entity.Order;
import com.soft.web.expressapi.core.IBaseClient;
import com.soft.web.dao.ExpressMapper;
import com.soft.web.expressapi.request.HttpResult;
import com.soft.web.expressapi.request.QueryTrackParam;
import com.soft.web.expressapi.request.QueryTrackReq;
import com.soft.web.expressapi.utils.ObjectToMapUtils;
import com.soft.web.expressapi.utils.QueryTrack;
import com.soft.web.expressapi.utils.SignUtils;
import com.soft.web.vo.ExpressGoodsVO;
import com.soft.web.vo.ReceiveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ExpressService {

    @Autowired
    ExpressMapper expressMapper;
    @Autowired
    SmsConstants smsConstants;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserMapper userMapper;

    public Object orderAbouts(Long userId, Long id) {
        if(userId == null) {
            return AjaxResult.error("请先登录");
        }

        //  总价
        BigDecimal price = BigDecimal.ZERO;

        BigDecimal discount = BigDecimal.ZERO;


        List<ExpressGoodsVO> expressGoodsVOS = expressMapper.selectConfirmById(id);
        ReceiveVO receiveVO = expressMapper.selectReceiveByOid(id);
        for (ExpressGoodsVO goodsVO : expressGoodsVOS) {
            price = price.add(goodsVO.getSums());
            discount = discount.add(goodsVO.getDiscount().multiply(new BigDecimal(goodsVO.getProductQuantity())));
        }

        if(receiveVO.getPayStatus() == 0) {
            receiveVO.setComments("未支付");
        } else if(receiveVO.getPayWay() != null) {
            switch (receiveVO.getPayWay()){
                case 1: receiveVO.setPayComments("微信支付");break;
                case 2: receiveVO.setPayComments("支付宝支付");break;
                case 3: receiveVO.setPayComments("储值卡支付");break;
                case 4: receiveVO.setPayComments("购物积分支付");break;
            }
        }
        if(receiveVO.getTradeStatus() != null) {
            switch (receiveVO.getTradeStatus()){
                case 1: receiveVO.setComments("已取消");break;
                case 2: receiveVO.setComments("待发货");break;
                case 3: receiveVO.setComments("待收货");break;
                case 4: receiveVO.setComments("已签收");break;
            }
        }

        return MyListUtils.getDataTable(expressGoodsVOS, expressGoodsVOS.size(), price, receiveVO.getExpressFee(),
                receiveVO.getComments(), receiveVO.getName(), receiveVO.getPhone(), receiveVO.getAddress(),
                receiveVO.getOrderId(), receiveVO.getTotalWeight(), id, discount, receiveVO.getPayComments());
    }

    public Object expressAbouts(Long userId, Long id) throws Exception {
        if(userId == null) {
            return AjaxResult.error("请先登录");
        }
        smsConstants.init();

        Order order = orderMapper.selectById(id);
        Express express = expressMapper.selectById(order.getExpressId());

        QueryTrackReq queryTrackReq = new QueryTrackReq();
        QueryTrackParam queryTrackParam = new QueryTrackParam();
        queryTrackParam.setCom(express.getExpressCode());
        queryTrackParam.setNum(order.getExpressNo());
        queryTrackParam.setPhone(order.getReceiveTelphone());
        String param = new Gson().toJson(queryTrackParam);

        queryTrackReq.setParam(param);
        queryTrackReq.setCustomer(smsConstants.getCustomer());
        queryTrackReq.setSign(SignUtils.querySign(param ,smsConstants.getAuthenticationKey(),smsConstants.getCustomer()));

        IBaseClient baseClient = new QueryTrack();
        //Map<String, String> stringStringMap = ObjectToMapUtils.objectToMap(baseClient);
        HttpResult execute = baseClient.execute(queryTrackReq);
        execute.setExpress(express.getExpressName());

        Map<String, String> stringStringMap = ObjectToMapUtils.objectToMap(baseClient);
        Set<String> strings = stringStringMap.keySet();
        for (String string : strings) {

        }
        return execute;
    }
}
