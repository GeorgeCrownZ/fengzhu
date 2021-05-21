package com.soft.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyOrderDetailsVO implements Serializable {

    private Long id;
    private String pic;
    private String productName;
    private String label;
    private BigDecimal price;
    private Integer productQuantity;

    private MyProductinfo productinfo;
}
