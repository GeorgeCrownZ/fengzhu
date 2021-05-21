package com.soft.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveVO implements Serializable {

    private String orderId;
    private String name;
    private String phone;
    private String address;
    private BigDecimal totalWeight;
    private Integer tradeStatus;
    private Integer payStatus;
    private BigDecimal expressFee;
    private Integer payWay;

    private String comments;
    private String payComments;
}
