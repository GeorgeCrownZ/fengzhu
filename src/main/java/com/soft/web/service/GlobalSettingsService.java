package com.soft.web.service;

import com.soft.web.common.AjaxResult;
import com.soft.web.dao.GlobalMapper;
import com.soft.web.dao.UserMapper;
import com.soft.web.entity.Global;
import com.soft.web.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GlobalSettingsService {

    @Autowired
    GlobalMapper globalMapper;
    @Autowired
    UserMapper userMapper;

    public Global getGlobal() {
        return globalMapper.selectById(1);
    }

    public AjaxResult checkUser(Long userId) {
        User user = userMapper.selectUserStatus(userId);
        if(user == null) {
            return AjaxResult.error("该用户不存在");
        }
        if(user.getUserState() == 0) {
            return AjaxResult.error("用户已被封禁");
        }
        return AjaxResult.success();
    }
}
