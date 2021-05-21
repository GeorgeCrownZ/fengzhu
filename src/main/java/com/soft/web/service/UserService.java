package com.soft.web.service;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.exceptions.ClientException;
import com.soft.web.common.AjaxResult;
import com.soft.web.constant.SmsConstants;
import com.soft.web.dao.GlobalMapper;
import com.soft.web.dao.UserMapper;
import com.soft.web.entity.Global;
import com.soft.web.entity.PwdData;
import com.soft.web.entity.User;
import com.soft.web.entity.UserCode;
import com.soft.web.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 电商用户表 服务实现类
 * </p>
 *
 * @author md
 * @since 2021-03-30
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    GlobalMapper globalMapper;
    @Autowired
    DownloadImageUtil downloadImageUtil;
    @Autowired
    QRCodeUtil qrCodeUtil;
    @Autowired
    SmsConstants smsConstants;


    public Global userAgreement() {
        return globalMapper.selectById(1L);
    }

    public AjaxResult checkUser(Long userId) {
        if(userId == null) {
            return AjaxResult.error("请重新登录");
        }
        User user = userMapper.selectUserStatus(userId);
        if(user == null) {
            return AjaxResult.error("用户信息不存在，请重新登录");
        }
        if(user.getUserState() == 0) {
            return AjaxResult.error("用户已被封禁，请联系管理员");
        }
        return AjaxResult.success();
    }

    public int addCode(UserCode userCode) {
        userCode.setUpdateTime(new Date());
        return userMapper.addCode(userCode);
    }

    /**
     * 发送验证码
     * @param phone：手机号码
     * @param sms：短信格式
     * @return
     */
    public AjaxResult sendSms(String phone, String sms, String signName) throws ClientException {
        if(!RegexMatches.check(phone)) {
            return AjaxResult.error("请输入合法的手机号码");
        }
        String send = AliyunSmsUtil.send(phone, sms, signName);
        UserCode userCode = new UserCode(phone, send, new Date());
        UserCode selectCodeByNum = userMapper.selectCodeByNum(userCode.getMobileNumber());
        int result = 0;
        if(selectCodeByNum!=null) {
            result = updateCode(userCode);
        } else {
            result = addCode(userCode);
        }

        if(result > 0) {
            return AjaxResult.success("发送验证码成功");
        }
        return AjaxResult.success("发送验证码失败");
    }

    /**
     * 修改验证码
     * @param userCode
     * @return
     */
    public int updateCode(UserCode userCode) {
        userCode.setUpdateTime(new Date());
        int result = userMapper.updateCode(userCode);
        if(result > 0) {
            return result;
        }
        return -1;
    }

    /**
     * 注册账号
     * @param phone：用户传入的手机号/用户名及密码
     * @param smscode：用户接收的验证码
     * @return
     */
    public AjaxResult regist(String phone, String smscode, String password, Long pid) {
        if(phone==null || "".equals(phone)) {
            return AjaxResult.error("请输入手机号码");
        }
        if(smscode==null || "".equals(smscode)) {
            return AjaxResult.error("请输入验证码");
        }
        if(password==null || "".equals(password)) {
            return AjaxResult.error("请输入密码");
        }
        if(password.length()<6 || password.length()>16) {
            return AjaxResult.error("请输入合法的密码（密码长度6-16位）");
        }
        if(!RegexMatches.check(phone)) {
            return AjaxResult.error("请输入合法的手机号码");
        }
        User user = new User();
        user.setMobileNumber(phone);
        user.setUserAccount(phone);
        user.setPassword(password);
        user.setPid(pid);
        user.setHeadPic("/upload/user/headpic/default.png");
        UserCode userCode = new UserCode();
        userCode.setCode(smscode);
        userCode.setCode(smscode);
        userCode.setUpdateTime(new Date());

        UserCode code = userMapper.selectCodeByNum(phone);
        if(code==null) {
            return AjaxResult.error("请获取验证码");
        }
        if(TimeUtils.time(code.getUpdateTime(), 5)) {
            UserCode selectCode = userMapper.selectCode(code);
            if(selectCode!=null) {
                User userByNum = userMapper.selectUserByNum(user.getMobileNumber());
                if(userByNum==null) {
                    //  生成密钥及密码加密
                    PasswordHelper passwordHelper = new PasswordHelper();
                    PwdData pwdData = new PwdData();
                    pwdData.setUsername(user.getMobileNumber());
                    pwdData.setPassword(user.getPassword());
                    PwdData encryptPassword = passwordHelper.encryptPassword(pwdData);
                    user.setPassword(encryptPassword.getPassword());
                    user.setPasswordSalt(encryptPassword.getSalt());
                    user.setAddTime(new Date());
                    int random = (int) (Math.random() * 1000000000 + 1);
                    user.setUserNick("用户"+ random);
                    user.setUserState(1);

                    //  判断用户填写的上级id
                    if(user.getPid()!=null) {
                        User userByPid = userMapper.selectUserByPid(user.getPid());
                        if(userByPid==null) {
                            return AjaxResult.error("邀请码错误，无该推荐人");
                        }
                    }

                    int result = userMapper.insert(user);
                    if(result > 0) {
                        User selectUserByNum = userMapper.selectUserByNum(user.getMobileNumber());
                        try {
                            String path = qrCodeUtil.QRCode("https://cs.fzsir.com/register.html?pid=" + selectUserByNum.getId(), selectUserByNum.getId());
                            User userUpdate = new User();
                            userUpdate.setId(selectUserByNum.getId());
                            userUpdate.setQrCode(path);
                            userMapper.updateById(userUpdate);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return AjaxResult.success("注册成功", selectUserByNum.getId());
                    }
                    return AjaxResult.error("注册失败");
                } else {
                    return AjaxResult.error("该手机号已经被注册过了");
                }

            }
            return AjaxResult.error("验证码错误，请重新输入");
        } else {
            return AjaxResult.error("验证码超时，请重新输入");
        }
    }

    /**
     * 找回密码
     * @param phone：用户手机号
     * @param password：新密码
     * @param smscode：验证码
     * @return
     */
    public AjaxResult findpwd(String phone, String password, String smscode) {
        if(phone==null || "".equals(phone)) {
            return AjaxResult.error("请输入手机号码");
        }
        if(password==null || "".equals(password)) {
            return AjaxResult.error("请输入密码");
        }
        if(smscode==null || "".equals(smscode)) {
            return AjaxResult.error("请输入验证码");
        }
        if(!RegexMatches.check(phone)) {
            return AjaxResult.error("请输入合法的手机号码");
        }
        if(password.length()<6 || password.length()>16) {
            return AjaxResult.error("请输入合法的密码（密码长度6-16位）");
        }
        User user = new User();
        user.setMobileNumber(phone);
        user.setUserAccount(phone);
        user.setPassword(password);
        UserCode userCode = new UserCode();
        userCode.setMobileNumber(phone);
        userCode.setCode(smscode);

        UserCode codeByNum = userMapper.selectCodeByNum(user.getMobileNumber());
        if(TimeUtils.time(codeByNum.getUpdateTime(), 5)) {
            User userByNum = userMapper.selectUserByNum(userCode.getMobileNumber());
            if(userByNum==null) {
                return AjaxResult.error("该手机号未注册，请先注册");
            }

            UserCode selectCodeByNum = userMapper.selectCode(userCode);
            if(selectCodeByNum==null) {
                return AjaxResult.error("验证码错误，请重新输入");
            }
            PasswordHelper passwordHelper = new PasswordHelper();
            PwdData pwdData = new PwdData();
            pwdData.setPassword(user.getPassword());
            pwdData.setUsername(userByNum.getMobileNumber());
            pwdData.setSalt(userByNum.getPasswordSalt());
            PwdData pwdData1 = passwordHelper.encryptPassword(pwdData);
            user.setPassword(pwdData1.getPassword());

            int result = userMapper.updateUserByNum(user);
            if(result > 0) {
                return AjaxResult.success("修改密码成功");
            }
            return AjaxResult.error("修改密码失败");
        } else {
            return AjaxResult.error("验证码超时，请重新输入");
        }
    }

    /**
     * 登录
     * @param phone：用户手机号
     * @param password：用户密码
     * @param smscode：手机验证码
     * @return
     */
    public AjaxResult login(String phone, String password, String smscode) {
        if(!RegexMatches.check(phone)) {
            return AjaxResult.error("请输入合法的手机号码");
        }
        if(phone==null || "".equals(phone)) {
            return AjaxResult.error("请输入手机号码");
        }

        User userByNum = userMapper.selectUserByNum(phone);
        if(smscode!=null){
            UserCode userCode = new UserCode();
            userCode.setCode(smscode);
            userCode.setMobileNumber(phone);
            UserCode codeByNum = userMapper.selectCodeByNum(userCode.getMobileNumber());
            if (TimeUtils.time(codeByNum.getUpdateTime(), 5)){
                UserCode checkCode = userMapper.selectCode(userCode);
                UserCode selectCodeByNum = userMapper.selectCodeByNum(phone);
                if(checkCode==null) {
                    return AjaxResult.error("验证码错误");
                }
                if(userByNum==null) {
                    User registUser = new User();
                    registUser.setMobileNumber(phone);
                    registUser.setUserAccount(phone);
                    String subPassword = phone.substring(5, 11);

                    PasswordHelper passwordHelper = new PasswordHelper();
                    PwdData pwdData = new PwdData();
                    pwdData.setUsername(registUser.getMobileNumber());
                    pwdData.setPassword(subPassword);
                    PwdData encryptPassword = passwordHelper.encryptPassword(pwdData);
                    registUser.setHeadPic("/upload/user/headpic/default.png");
                    registUser.setPassword(encryptPassword.getPassword());
                    registUser.setPasswordSalt(encryptPassword.getSalt());
                    registUser.setAddTime(new Date());

                    int result = userMapper.insert(registUser);
                    if(result > 0) {
                        return AjaxResult.success("该手机号并未注册过，已为您自动注册。初始密码为您手机号的后 6 位", registUser.getId());
                    }

                }
                if(selectCodeByNum!=null) {
                    return AjaxResult.success("登录成功", userByNum.getId());
                }
                return AjaxResult.error("登录失败，验证码错误");
            } else {
                return AjaxResult.error("验证码超时，请重新获取");
            }
        } else if(password!=null) {
            if(userByNum == null) {
                return AjaxResult.error("暂无该用户相关的信息，请先注册");
            }
            User user = new User();
            user.setPassword(password);
            user.setMobileNumber(phone);
            user.setUserAccount(phone);
            //  再次进行加密，对比加密后的密码是否一致
            PasswordHelper passwordHelper = new PasswordHelper();
            PwdData pwdData = new PwdData();
            pwdData.setPassword(user.getPassword());
            pwdData.setUsername(user.getMobileNumber());
            pwdData.setSalt(userByNum.getPasswordSalt());
            PwdData pwdData1 = passwordHelper.encryptPassword(pwdData);
            user.setPassword(pwdData1.getPassword());

            User userByPwd = userMapper.selectUserByPwd(user);
            if(userByPwd!=null) {
                if(userByPwd.getUserState() == 0) {
                    return AjaxResult.error("用户已被封禁，请联系管理员");
                }
                return AjaxResult.success("登录成功", userByNum.getId());
            }
            return AjaxResult.error("密码错误");
        } else {
            return AjaxResult.error("请输入验证码或密码进行登录");
        }
    }

    /**
     * 微信注册
     */
    public AjaxResult WechatRegist(String code) throws Exception {

        smsConstants.init();
        Map params1 = new HashMap();
        params1.put("appid", smsConstants.getAM_APPID());
        params1.put("secret", smsConstants.getAM_Secret());
        params1.put("code", code);
        params1.put("grant_type", "authorization_code");

        //  通过get请求向微信发送请求
        String result = HttpGetUtil.httpRequestToString("https://api.weixin.qq.com/sns/oauth2/access_token", params1);
        com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) JSON.parse(result);

        //  获得openid和token
        String openid = jsonObject.get("openid").toString();
        String access_token = jsonObject.get("access_token").toString();

        //  通过openid和token获取用户信息
        //  https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
        HashMap hashMap = new HashMap();
        hashMap.put("access_token", access_token);
        hashMap.put("openid", openid);
        hashMap.put("lang", "zh_CN");
        String fresult = HttpGetUtil.httpRequestToString("https://api.weixin.qq.com/sns/userinfo", hashMap);
        com.alibaba.fastjson.JSONObject object = (com.alibaba.fastjson.JSONObject) JSON.parse(fresult);

        String nickname = EmojiFilter.filterEmoji(object.get("nickname").toString());
        if(nickname.length()==0) {
            int random = (int) (Math.random() * 1000000000 + 1);
            nickname = "用户"+ random;
        }
        String sex = object.get("sex").toString();
        if(Integer.valueOf(sex) == 0) {
            sex = "女";
        } else {
            sex = "男";
        }
        String headimgurl = object.get("headimgurl").toString();
        String unionid = object.get("unionid").toString();

        User user = new User();
        user.setUserNick(nickname);
        user.setSex(sex);

        downloadImageUtil.download(headimgurl, openid + ".jpg");
        user.setHeadPic("/upload/wechat/" + openid + ".jpg");

        User userByOpenId = userMapper.selectUserByOpenId(openid);

        //  判断是否第一次使用微信登录或使用后未绑定手机号
        if(userByOpenId == null) {
            user.setUserState(0);
            user.setOpenId(openid);
            user.setAddTime(new Date());
            user.setUnionId(unionid);

            int i = userMapper.addUserByWechat(user);
            if(i > 0) {
                try {
                    String path = qrCodeUtil.QRCode("https://cs.fzsir.com/register.html?pid=" + user.getId(), user.getId());
                    User userUpdate = new User();
                    userUpdate.setId(user.getId());
                    userUpdate.setQrCode(path);
                    int i1 = userMapper.updateById(userUpdate);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return AjaxResult.warn("注册成功，即将跳转至绑定手机页面", user.getId());
            }
        } else {
            if(userByOpenId.getMobileNumber() == null) {
                return AjaxResult.warn("请绑定手机号码", userByOpenId.getId());
            } else {
                user.setId(userByOpenId.getId());
                int i = userMapper.updateById(user);
                if(i > 0) {
                    return AjaxResult.success("登录成功", user.getId());
                }
            }
        }
        return AjaxResult.error("注册失败");
    }

    public AjaxResult wechatphone(String phone, String smscode, Long userId) {
        if(!RegexMatches.check(phone)) {
            return AjaxResult.error("请输入合法的手机号码");
        }
        if(phone==null || "".equals(phone)) {
            return AjaxResult.error("请输入手机号码");
        }

        User userByNum = userMapper.selectUserByNum(phone);
        if(userByNum !=null) {
            return AjaxResult.error("该手机号已被其他账号绑定");
        } else {
            UserCode userCode = new UserCode();
            userCode.setCode(smscode);
            userCode.setMobileNumber(phone);
            UserCode codeByNum = userMapper.selectCodeByNum(userCode.getMobileNumber());
            if(codeByNum == null) {
                return AjaxResult.error("您还没有获取验证码，请获取验证码");
            }
            if (TimeUtils.time(codeByNum.getUpdateTime(), 5)){
                UserCode checkCode = userMapper.selectCode(userCode);
                if(checkCode==null) {
                    return AjaxResult.error("验证码错误");
                }
                //  截取手机号后6位作为密码
                String subPassword = phone.substring(5, 11);

                User registUser = new User();
                registUser.setMobileNumber(phone);
                registUser.setUserAccount(phone);
                PasswordHelper passwordHelper = new PasswordHelper();
                PwdData pwdData = new PwdData();
                pwdData.setUsername(registUser.getMobileNumber());
                pwdData.setPassword(subPassword);
                PwdData encryptPassword = passwordHelper.encryptPassword(pwdData);
                registUser.setPassword(encryptPassword.getPassword());
                registUser.setPasswordSalt(encryptPassword.getSalt());
                registUser.setAddTime(new Date());
                registUser.setId(userId);
                registUser.setUserState(1);

                int result = userMapper.updateById(registUser);
                if(result > 0) {
                    return AjaxResult.success("手机号绑定成功，初始密码位您手机号码后 6 位", userId);
                }
                return AjaxResult.error("绑定失败");
            } else {
                return AjaxResult.error("验证码超时，请重新获取");
            }
        }
    }

}
