package com.ms.ebangw.utils;

import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.ms.ebangw.bean.User;

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
     * 是否是身份证号
     * @param identifyCard
     * @return
     */
    public static boolean isIdentifyCard(String identifyCard){
        if (TextUtils.isEmpty(identifyCard)) {
            return false;
        }
        return  IDCardUtil.isIDCard(identifyCard);

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

    /**
     * 正则表达式判断汉字
     * @param str
     * @return
     */
    public static boolean isChinese(String str){
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]+");
        Matcher isNum = pattern.matcher(str);
        if(!isNum.matches() ){

            return false;
        }
        return true;
    }

    /**
     * 处理银行卡号
     * @param view
     */
    public static void setBankCard(final EditText view){
        view.addTextChangedListener(new TextWatcher() {
            int beforeTextLength = 0;
            int onTextLength = 0;
            boolean isChanged = false;

            int location = 0;// 记录光标的位置
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();
            int konggeNumberB = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                beforeTextLength = s.length();
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                konggeNumberB = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        konggeNumberB++;
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 3
                        || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChanged) {
                    location = view.getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if (buffer.charAt(index) == ' ') {
                            buffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }

                    index = 0;
                    int konggeNumberC = 0;
                    while (index < buffer.length()) {
                        if ((index == 4 || index == 9 || index == 14 || index == 19)) {
                            buffer.insert(index, ' ');
                            konggeNumberC++;
                        }
                        index++;
                    }

                    if (konggeNumberC > konggeNumberB) {
                        location += (konggeNumberC - konggeNumberB);
                    }

                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }

                    view.setText(str);
                    Editable etable = view.getText();
                    Selection.setSelection(etable, location);
                    isChanged = false;
                }
            }
        });


    }

    /**
     * 转化卡号格式
     * @param s
     * @return
     */
    public static String bankCard(String s){


        return s.replaceAll(" ","");
    }



}  
