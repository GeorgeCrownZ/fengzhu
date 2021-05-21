package com.soft.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO implements Serializable {

    private Long id;

    private Integer productCount;

    private Long productId;

    private String productName;

    private BigDecimal price;

    private Integer specialoffer;

    private BigDecimal specialofferprice;
}
