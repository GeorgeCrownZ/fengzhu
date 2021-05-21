package com.soft.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsVO implements Serializable {

    private Long id;

    private String pic;

    private String productName;

    private String label;

    private BigDecimal price;

    private Integer salenum2;

    private String measurement;

    private Integer productQuantity;

    private BigDecimal sums;

    private BigDecimal specialofferprice;

    private Integer specialoffer;

    private String receiveName;
    private String receiveTelphone;
    private String address;

    private String systemComments;
    private String comments;
}
