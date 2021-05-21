package com.soft.web.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

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
 * @since 2021-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Alias("Productinfo")
public class Productinfo implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "product_id", type = IdType.AUTO)
    private Long productId;

    private String productName;

    /**
     * 商品分类
     */
    private Integer productCategoryId;

    /**
     * 渠道id，标注商品来自哪家。对应fa_channel 的channel id
     */
    private Integer channelId;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 当前库存量
     */
    private Integer currentInventory;

    /**
     * 重量
     */
    private String measurement;

    /**
     * 特性说明
     */
    private String lable;

    private String specifications1;

    private String specifications2;

    private String specifications3;

    private Integer indexno;

    private String imgurl1;

    private String imgurl2;

    private String imgurl3;

    private String imgurl4;

    private String imgurl5;

    private String imgurl6;

    private String imgurl7;

    private String imgurl8;

    /**
     * 每日特价：0不启动特价，1启动特价
     */
    private Integer specialoffer;

    private String recommend;

    /**
     * 特价价格
     */
    private BigDecimal specialofferprice;

    /**
     * 保存条件
     */
    private String storageConditions;

    /**
     * 保质期
     */
    private String shelfLife;

    /**
     * 净含量
     */
    private String netContent;

    /**
     * 活动图
     */
    private String activityUrl;

    /**
     * 0下架，1上架
     */
    private Integer updownStatus;

    /**
     * 真实销量
     */
    private Integer salenum;

    /**
     * 显示销量
     */
    private Integer salenum2;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 上下架日期
     */
    private Date updownTime;

    /**
     * 是否首页 展示
     */
    private Integer showIndex;


}
