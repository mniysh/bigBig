package com.ms.ebangw.utils;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
            T.show("手机号不能为空");
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
            T.show("邮箱不能为空");
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
     * 判断单体时间
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static boolean isRight(int year, int month, int day){
        List<Integer> data = getCurrentTime();
        int currentYear = data.get(0);
        int currentMonth = data.get(1);
        int currentDay = data.get(2);
        if(year < currentYear){
            return false;
        }else if(month < currentMonth){
            return false;
        }else if(day < currentDay){
            return false;
        }
        return true;

    }

    /**
     * 判断开始时间和结束时间是否合法
     * @param startYear
     * @param startMonth
     * @param startDay
     * @param endYear
     * @param endMonth
     * @param endDay
     * @return
     */
    public static boolean isRightTime(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay){



        if(startYear > endYear){
            return false;
        }else if(startYear == endYear){
            if(startMonth > endMonth){
                return false;
            }else if(startMonth == endMonth){
                if(startDay > endDay){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 获取当前时间
     * @return
     */
    public static List<Integer> getCurrentTime(){
        List<Integer> data = new ArrayList<Integer>();
        SimpleDateFormat sim = new SimpleDateFormat("yyyyMMdd");
        String format = sim.format(new Date());
        char[] arr = format.toCharArray();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            sb.append(arr[i]);
        }
        int year = Integer.valueOf(sb.toString());
        sb.delete(0, sb.toString().length());
        for (int j = 5; j < 7; j++) {
            sb.append(arr[j]);
        }
        int month = Integer.valueOf(sb.toString());
        sb.delete(0, sb.toString().length());
        for (int n = 7; n < format.length(); n++) {
            sb.append(arr[n]);
        }
        int day = Integer.valueOf(sb.toString());
        data.add(year);
        data.add(month);
        data.add(day);
        return data;
    }

//    /**
//     * 弹出popupwindow
//     */
//    public void pWindow(LinearLayout layout,int pwWidth,int pwHeight,View layoutView,View layoutView2,View layoutView3,View layoutView4,View clickView,int location,int localWidth,int localHeight){
//        final PopupWindow pw=new PopupWindow(layout,pwWidth,pwHeight);
//        pw.setBackgroundDrawable(new BitmapDrawable());
//        layoutView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pw.dismiss();
//                finish();
//                backgroundAlpha(1.0f);
//
//            }
//        });
//        backgroundAlpha(0.5f);
//        pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                finish();
//                backgroundAlpha(1.0f);
//            }
//        });
//        pw.setOutsideTouchable(true);
//        pw.showAtLocation(clickView, location, localWidth, localHeight);
//    }
//    public void backgroundAlpha(float bgAlpha)
//    {
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.alpha = bgAlpha; //0.0-1.0
//        getWindow().setAttributes(lp);
//    }

}  
