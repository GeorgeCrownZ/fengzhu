package com.soft.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCartVO implements Serializable {

    private Long gid;

    private Long pid;

    private String lable;

    private String productName;

    private BigDecimal price;

    private String pic;

    private Integer number;

    private BigDecimal specialofferprice;

    private Integer specialoffer;
}
