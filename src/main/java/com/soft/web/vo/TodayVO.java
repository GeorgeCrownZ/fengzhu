package com.soft.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TodayVO implements Serializable {

    private Long id;

    private String productName;

    private BigDecimal price;

    private BigDecimal specprice;

    private String pic;
}
