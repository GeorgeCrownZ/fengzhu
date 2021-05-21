package com.soft.web.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

/**
 * <p>
 * 电商用户表
 * </p>
 *
 * @author md
 * @since 2021-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Alias("User")
@TableName("fa_user")
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 上级id
     */
    private Long pid;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 昵称
     */
    private String userNick;

    /**
     * 真实姓名
     */
    private String trueName;

    /**
     * 电话
     */
    private String mobileNumber;

    /**
     * 密码
     */
    private String password;

    private String passwordSalt;

    /**
     * 支付密码
     */
    private String payPassword;

    /**
     * 性别
     */
    private String sex;

    /**
     * 身份证号
     */
    private String pincode;

    /**
     * 用户状态：0禁用，1启用
     */
    private Integer userState;

    /**
     * 是否员工：0不是，1是
     */
    private Integer isEmployee;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 头像
     */
    private String headPic;

    /**
     * 微信openid
     */
    private String openId;

    private String unionId;

    /**
     * 是否专员：0未办储值卡，1办理储值卡
     */
    private Integer grade1;

    /**
     * 职位：2主任，3经理，4总监，5总裁
     */
    private Integer grade2;

    /**
     * 回馈积分
     */
    private BigDecimal giveScore;

    /**
     * 服务积分
     */
    private BigDecimal serviceScore;

    /**
     * 购物积分
     */
    private BigDecimal consumeScore;

    /**
     * 可提现积分
     */
    private String cashoutScore;

    /**
     * 来源
     */
    private String comeFrom;

    /**
     * 注册二维码
     */
    private String qrCode;

    /**
     * 自身购买储值卡总额
     */
    private BigDecimal selfScore;

    /**
     * 社区购买储值卡总额
     */
    private BigDecimal totalScore;

    /**
     * 网页防刷code
     */
    private String code;

    private Date addTime;

    private Date updateTime;


}
