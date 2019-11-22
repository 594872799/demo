package com.liangwc.demo.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liangweicheng
 * @date 2019/11/22 17:07
 */
public class RegrexUtils {
    final static String emailRe = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    public static boolean checkEmail(String email) {
        Pattern regex = Pattern.compile(emailRe);
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }
}
