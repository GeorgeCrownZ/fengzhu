package com.soft.web.entity;

import java.math.BigDecimal;
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
 * @since 2021-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Alias("Global")
@TableName("fa_global")
public class Global implements Serializable {

    private static final long serialVersionUID=1L;

      private Long id;

    /**
     * 网站名称
     */
    private String webName;

    /**
     * 网站logo
     */
    private String webLogo;

    /**
     * 网站备案信息
     */
    private String webRecord;

    /**
     * 快递费用(满100包邮)
     */
    private BigDecimal deliveryCost;

    /**
     * 首页公告弹出图片
     */
    private String noticePic1;

    private String noticePic2;

    private String noticePic3;

    private String noticePic4;

    /**
     * 公告开关：0关，1开
     */
    private Integer noticeSwitch;

    /**
     * 用户协议
     */
    private String userAgreement;

    /**
     * 提现下限
     */
    private BigDecimal cashOutDown;

    /**
     * 提现次数
     */
    private Integer cashOutNum;

    /**
     * 提现手续费
     */
    private BigDecimal cashOutFee;

    /**
     * 提现开关
     */
    private Integer cashOutSwitch;

    /**
     * 分销参数
     */
    private String distParam;

    /**
     * 升级主任条件1
     */
    private Long upgradeDirector1;

    /**
     * 升级主任条件2
     */
    private Integer upgradeDirector2;

    /**
     * 升级经理条件1
     */
    private Integer upgradeManager1;

    /**
     * 升级经理条件2
     */
    private Integer upgradeManager2;

    /**
     * 升级总监条件1
     */
    private Integer upgradeChiefinspector1;

    /**
     * 升级总监条件2
     */
    private Integer upgradeChiefinspector2;

    /**
     * 升级总裁条件1
     */
    private Integer upgradeCeo1;

    /**
     * 升级总裁条件2
     */
    private Integer upgradeCeo2;

    /**
     * 专员分享奖1
     */
    private BigDecimal share1;

    /**
     * 专员分享奖2
     */
    private BigDecimal share2;

    /**
     * 主任差额奖
     */
    private BigDecimal directorDifferenceAward;

    /**
     * 经理差额奖
     */
    private BigDecimal managerDifferenceAward;

    /**
     * 总监差额奖
     */
    private BigDecimal chiefinspectorDifferenceAward;

    /**
     * 总裁差额奖
     */
    private BigDecimal ceoDifferenceAward;

    /**
     * 管理奖
     */
    private BigDecimal managementAward;

    /**
     * 普通商品返积分
     */
    private BigDecimal integral;

    /**
     * 丰收产品购买上限金额（每周）
     */
    private BigDecimal purchaseCap;

    /**
     * 微信APP ID
     */
    private String wechatAppid;

    /**
     * 商户号
     */
    private String wechatMerchantNum;

    /**
     * 商户密钥
     */
    private String wechatMerchantKey;

    /**
     * 密钥
     */
    private String wechatToken;

    /**
     * 回调路径
     */
    private String wechatCallbackUrl;

    /**
     * wechat_cert_pem
     */
    private String wechatCertPem;

    /**
     * wechat_key_pem
     */
    private String wechatKeyPem;

    /**
     * 短信签名
     */
    private String smsSignature;

    /**
     * 身份验证验证码
     */
    private String authenticationCode;

    /**
     * 登录确认验证码
     */
    private String loginCode;

    /**
     * 登录异常验证码
     */
    private String loginExceptionCode;

    /**
     * 用户注册验证码
     */
    private String registerCode;

    /**
     * 修改密码验证码
     */
    private String updatePasswordCode;

    /**
     * 信息变更验证码
     */
    private String informationChangeCode;

    /**
     * AccessKeyID
     */
    private String accessKeyId;

    /**
     * AccessKeySecret
     */
    private String accessKeySecret;

    /**
     * 快递授权key
     */
    private String authenticationKey;

    /**
     * customer
     */
    private String customer;

    private String fromName;

    private String fromTelminePic;

    private String fromAddress;

    private String minePic1;

    private String minePic2;
}
