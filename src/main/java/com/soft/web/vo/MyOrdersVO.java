package com.soft.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyOrdersVO {

    private Long id;
    private Date orderTime;
    private BigDecimal price;
    private Integer isComments;
    private Integer isSales;
    private Integer payStatus;
    private Integer isBackpay;
    private Integer tradeStatus;
    private String state;
    private Integer count;
    private BigDecimal payable;
    private Integer productAmount;
    private String express_fee;
    private List<MyOrderDetailsVO> detail;
}
