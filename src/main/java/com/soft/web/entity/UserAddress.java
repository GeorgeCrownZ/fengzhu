package com.soft.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

/**
 * <p>
 * 
 * </p>
 *
 * @author md
 * @since 2021-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Alias("UserAddress")
@TableName("fa_user_address")
public class UserAddress implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 来源用户表用户ID
     */
    private Long userId;

    /**
     * 收货人名字
     */
    private String receiveName;

    /**
     * 性别
     */
    private String receiverSex;

    /**
     * 收货人手机
     */
    private String receiverMobile;

    /**
     * 收货地址
     */
    private String receiverAddress;

    /**
     * 门牌号
     */
    private String receiverNumber;

    /**
     * 家、公司、其他
     */
    private String lable;

    /**
     * 1和0，1是默认收货地址
     */
    private Integer isDefault;

    /**
     * 状态
     */
    private String status;


}
