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
@Alias("OrderDetail")
@TableName("fa_order_detail")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID=1L;


      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long oid;

    private String orderId;

    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 重量
     */
    private String measurement;

    /**
     * 当前价格
     */
    private BigDecimal productPrice;

    /**
     * 优惠价
     */
    private BigDecimal specialofferprice;

    /**
     * 数量
     */
    private Integer productQuantity;

    /**
     * 小图
     */
    private String productIcon;

    /**
     * 积分
     */
    private BigDecimal score;

    /**
     * 退款金额
     */
    private BigDecimal backpayAmount;

    /**
     * 创建时间
     */
    private Date createTime;

    private Date addTime;


}
