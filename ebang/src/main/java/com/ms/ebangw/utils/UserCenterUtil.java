package com.ms.ebangw.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.ms.ebangw.R;

/**
 * 用户认证工具类
 * User: WangKai(123940232@qq.com)
 * 2015-12-10 16:18
 */
public class UserCenterUtil {

    /**
     * 用户认证时把TextView中的第一个*变成红色
     */
    public static void setStarRed(View contentLayout) {
        int[] resId = new int[]{R.id.tv_a, R.id.tv_b, R.id.tv_c, R.id.tv_d, R.id.tv_e, R.id.tv_f,
            R.id.tv_g, R.id.tv_h, R.id.tv_i, R.id.tv_j, R.id.tv_k};
        if(null != contentLayout)
        for (int i = 0; i < resId.length; i++) {
            TextView a = (TextView) contentLayout.findViewById(resId[i]);
            if (null != a) {
                String s = a.getText().toString();
                if (!TextUtils.isEmpty(s) && s.contains("*")) {
                    SpannableString spannableString = new SpannableString(s);
                    spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    a.setText(spannableString);
                }
            }
        }
    }

}  
