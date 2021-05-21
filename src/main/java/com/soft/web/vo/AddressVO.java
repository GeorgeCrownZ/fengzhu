package com.soft.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressVO implements Serializable {

    private Long id;

    private String name;

    private String mobile;

    private String address;

    private Integer isdefault;
}
