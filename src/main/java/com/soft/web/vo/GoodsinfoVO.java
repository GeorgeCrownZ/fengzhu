package com.soft.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsinfoVO implements Serializable {

    private Long id;

    private List<GoodsPicVO> goodspic;

    private String productName;

    private List<GoodsSpecVO> spec;

    private BigDecimal price;

    private BigDecimal specialofferprice;

    private List<GoodsParam> goodsparam;

    private List<DetailVO> detail;

    private Integer salenum2;

    private Integer specialoffer;
}
