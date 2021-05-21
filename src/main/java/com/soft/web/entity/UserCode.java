package com.soft.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("UserCode")
public class UserCode implements Serializable {

    private String mobileNumber;
    private String code;
    private Date updateTime;
}
