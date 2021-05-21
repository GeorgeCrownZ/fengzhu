package com.soft.web.dao;

import com.soft.web.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soft.web.entity.UserCode;
import com.soft.web.vo.AboutMeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 电商用户表 Mapper 接口
 * </p>
 *
 * @author md
 * @since 2021-03-30
 */
@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {

    public int addCode(UserCode userCode);

    public UserCode selectCodeByNum(@Param("mobileNumber")String mobileNumber);

    public int updateCode(UserCode userCode);

    public UserCode selectCode(UserCode userCode);

    public User selectUserByNum(@Param("mobileNumber")String mobileNumber);

    public User selectUserByPwd(User user);

    public int updateUserByNum(User user);

    public User selectUserByPid(@Param("pid")Long pid);

    public User selectUserById(@Param("userId")Long userId);

    public User selectByUserId(@Param("userId")Long userId);

    public int addUserByWechat(User user);

    public User selectUserByOpenId(@Param("openId")String openId);

    public AboutMeVO selectAboutByUserid(@Param("userId")Long userId);

    public User selectUserStatus(@Param("id")Long id);
}
