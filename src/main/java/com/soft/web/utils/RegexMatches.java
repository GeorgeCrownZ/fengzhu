package com.soft.web.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatches {

    public static boolean check(String phone) {
        String pattern = "^[1][3,4,5,7,8][0-9]{9}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(phone);
        return m.matches();
    }
}