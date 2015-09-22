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

    /**
     * 输入的密码是否正确
     * @param password
     * @return
     */
    public static boolean isPasswordRight(String password){
        if (TextUtils.isEmpty(password)) {
            T.show("密码不能为空");
            return false;
        }

        if (password.length() < 6 || password.length() > 20) {
            T.show("密码的长度为6到20");
            return false;
        }
        return true;
    }

    /**
     * 判断验证码
     * @param code
     * @return
     */
    public static boolean isCode(String code){
        if(TextUtils.isEmpty(code)){
            T.show("验证码不能为空");
            return  false;
        }
        return true;
    }
}  
