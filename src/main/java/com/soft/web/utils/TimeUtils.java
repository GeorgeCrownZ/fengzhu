package com.soft.web.utils;

import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    /**
     * 判断验证码是否过期
     * @param updateDate：对比日期
     * @return
     */
    public static boolean time(Date updateDate, int timeout) {
        Calendar c1=Calendar.getInstance();
        Calendar c2=Calendar.getInstance();
        Calendar c3=Calendar.getInstance();

        //要判断的日期
        c1.setTime(updateDate);
        //初始日期
        c2.setTime(new Date());
        //初始日期 + 过期时间
        c3.setTime(new Date());

        c3.add(Calendar.MINUTE, timeout);
        c2.add(Calendar.MINUTE, -timeout);
        if(c1.after(c2)&&c1.before(c3)){
            return true;
        }else {
            return false;
        }
    }
}
