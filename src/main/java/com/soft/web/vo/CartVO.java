package com.soft.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartVO {

    private Long id;

    private Long userId;

    private Long productId;

    private Integer productCount;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String productName;

    private BigDecimal price;

    private String picPath;

    private int counts;

}
