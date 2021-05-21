package com.soft.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpressGoodsVO implements Serializable {

    private Long id;
    private String productName;
    private String pic;
    private String lable;
    private BigDecimal price;
    private Integer productQuantity;
    private BigDecimal sums;
    private String measurement;
    private Integer tradeStatus;
    private BigDecimal discount;
}
