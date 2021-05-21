package com.soft.web.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderUtils {

    public static String createBS() {
        StringBuffer result = new StringBuffer();
        result.append("BS").append(new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date())).append(((int)(Math.random()*10)+1));
        return result.toString();
    }

    public static String createMD() {
        StringBuffer result = new StringBuffer();
        result.append("MD").append(new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date())).append(((int)(Math.random()*10)+1));
        return result.toString();
    }

    public static String createFS() {
        StringBuffer result = new StringBuffer();
        result.append("FS").append(new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date())).append(((int)(Math.random()*10)+1));
        return result.toString();
    }

}
