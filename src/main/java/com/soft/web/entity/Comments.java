package com.soft.web.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Data
@Alias("Comments")
public class Comments implements Serializable {

    private String comments;
}
