package com.soft.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressAbouts implements Serializable {

    private String systemComments;

    private String receiveName;

    private String receiveTelphone;

    private String address;

    private String comments;
}
