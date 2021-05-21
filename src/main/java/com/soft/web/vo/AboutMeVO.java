package com.soft.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AboutMeVO implements Serializable {

    private Long uid;

    private String headpic;

    private String nickname;

    private String truename;

    private String phone;

    private Long pid;
}
