package com.soft.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditAddressVO implements Serializable {

    private Long uid;
    private Long id;
    private Long aid;
    private String name;
    private String mobile;
    private String address;
    private String city;
    private String street;
    private Integer isdefault;
}
