package com.soft.web.service;

import com.soft.web.common.AjaxResult;
import com.soft.web.common.MyListUtils;
import com.soft.web.dao.GlobalMapper;
import com.soft.web.dao.OrderMapper;
import com.soft.web.dao.UserAddressMapper;
import com.soft.web.dao.UserMapper;
import com.soft.web.entity.Order;
import com.soft.web.entity.User;
import com.soft.web.entity.UserAddress;
import com.soft.web.utils.DownloadImageUtil;
import com.soft.web.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AboutMeService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    DownloadImageUtil downloadImageUtil;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    GlobalMapper globalMapper;
    @Autowired
    UserAddressMapper userAddressMapper;

    public AboutMeVO editinfo(Long userId) {
        return userMapper.selectAboutByUserid(userId);
    }

    @Transactional
    public AjaxResult saveinfo(AboutMeVO aboutMeVO) {
        try{
            if(aboutMeVO.getUid() == null) {
                return AjaxResult.error("请先登录");
            }

            if(aboutMeVO.getPid().equals(aboutMeVO.getUid())) {
                return AjaxResult.error("推荐人不可填写自己");
            }

            User user = new User();
            User checkPid = userMapper.selectById(aboutMeVO.getUid());

            if(checkPid.getPid() == null && aboutMeVO.getPid() != null) {
                User user1 = userMapper.selectById(aboutMeVO.getPid());
                if(user1 == null) {
                    return AjaxResult.error("推荐人信息不存在");
                }
                user.setPid(aboutMeVO.getPid());
            }

            user.setId(aboutMeVO.getUid());
            user.setTrueName(aboutMeVO.getTruename());

            userMapper.updateById(user);
            return AjaxResult.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("修改失败");
        }
    }

    public Object order(Long userId, String type) {
        if(userId == null) {
            return AjaxResult.error("请先登录");
        }

        Order order = new Order();
        order.setUserId(userId);
        switch (type) {
            case "all": //全部
                break;
            case "nopay":   //待支付
                order.setPayStatus(0);
                break;
            case "nosend":  //待发货
                order.setTradeStatus(2);
                break;
            case "norec":   //待收货
                order.setTradeStatus(3);
                break;
            case "finish":
                order.setTradeStatus(4);
                break;
        }

        List<MyOrdersVO> myOrdersVOS = orderMapper.selectOrderByType(order);
        BigDecimal price = BigDecimal.ZERO;
        for (MyOrdersVO myOrdersVO : myOrdersVOS) {
            if(myOrdersVO.getPrice() != null) {
                price = price.add(myOrdersVO.getPrice());
            }
            if(myOrdersVO.getPayStatus() != null && myOrdersVO.getPayStatus() == 0) {
                myOrdersVO.setState("待支付");
            } else if(myOrdersVO.getTradeStatus() != null && myOrdersVO.getTradeStatus() == 2) {
                myOrdersVO.setState("待发货");
            } else if(myOrdersVO.getTradeStatus() != null && myOrdersVO.getTradeStatus() == 3) {
                myOrdersVO.setState("待收货");
            } else if(myOrdersVO.getTradeStatus() != null && myOrdersVO.getTradeStatus() == 4) {
                myOrdersVO.setState("已完成");
            }
            List<MyOrderDetailsVO> myOrderDetailsVOS = orderMapper.selectOrderDetailByType(myOrdersVO.getId());

            for (MyOrderDetailsVO myOrderDetailsVO : myOrderDetailsVOS) {
                MyProductinfo myProductinfo = orderMapper.selectLableByType(myOrderDetailsVO.getId());
                myOrderDetailsVO.setProductinfo(myProductinfo);
            }

            myOrdersVO.setDetail(myOrderDetailsVOS);
        }

        return MyListUtils.getDataTable(myOrdersVOS, myOrdersVOS.size(), price, null);
    }

    public Object adpic() {
        MinePicVO minePic = globalMapper.selectMinePic();
        if(minePic == null) {
            return AjaxResult.error("暂无图片");
        }
        return minePic;
    }

    public AjaxResult received(Long userId, Long id) {
        if(userId == null) {
            return AjaxResult.error("请先登录");
        }

        Order order = orderMapper.selectById(id);
        if(order.getTradeStatus() == 3) {
            Order updateOrder = new Order();
            updateOrder.setId(id);
            updateOrder.setTradeStatus(4);
            int result = orderMapper.updateById(updateOrder);
            if(result > 0) {
                return AjaxResult.success("确认收货成功", id);
            }
            return AjaxResult.error("订单状态错误");
        }
        return AjaxResult.error("订单状态错误");
    }

    public AjaxResult editaddress(EditAddressVO editAddrVO) {
        if(editAddrVO.getUid() == null) {
            return AjaxResult.error("请先登录");
        }

        UserAddress userAddress = userAddressMapper.selectDefaultById(editAddrVO.getUid());

        if(userAddress != null) {
            userAddress.setIsDefault(0);
            userAddressMapper.updateById(userAddress);
        }
        int result = userAddressMapper.updateAddress(editAddrVO);
        if(result > 0) {
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    public AjaxResult deleteaddress(Long aid) {
        int result = userAddressMapper.deleteById(aid);
        if(result > 0) {
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
