package com.soft.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ShopCartVO implements Serializable {


    private Long gid;

    private Long id;

    private Integer num;
}
