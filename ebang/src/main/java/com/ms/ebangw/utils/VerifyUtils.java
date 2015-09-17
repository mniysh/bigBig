package com.ms.ebangw.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串验证相关类
 * User: WangKai(123940232@qq.com)
 * 2015-09-17 16:12
 */
public class VerifyUtils {

    /**
     * 是不是手机号
     * @param
     * @return
     */
    public static Boolean isPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        }

        if (phone.replace("-", "").trim() == null || "".equals(phone.replace("-", "").trim())) {
            return false;
        } else {
            String mobileTrim = phone.replace("-", "").trim();
            Pattern patternMobile = Pattern.compile("^1[0-9][0-9]{9}$");
            Matcher matcherMobile = patternMobile.matcher(mobileTrim);
            return matcherMobile.matches();
        }
    }

    /**
     * 验证邮箱
     * @param email
     * @return
     */
    public static boolean isEmail(String email){
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        boolean flag = false;
        try{
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }
}  
