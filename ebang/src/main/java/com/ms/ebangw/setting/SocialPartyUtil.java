package com.ms.ebangw.setting;

import android.text.TextUtils;

import com.ms.ebangw.bean.Party;
import com.ms.ebangw.utils.T;

import java.util.List;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-12-04 09:45
 */
public class SocialPartyUtil {
    public static boolean isRight(Party party) {

        if (TextUtils.isEmpty(party.getTitle()) || null == party) {
            T.show("请输入标题");
            return false;
        }

        if (TextUtils.isEmpty(party.getArea_other())) {
            T.show("请输入工地地址");
            return false;
        }

        if (TextUtils.isEmpty(party.getNumber_people())) {
            T.show("请输入人数");
            return false;
        }

        if (TextUtils.isEmpty(party.getPrice())) {
            T.show("请输价格");
            return false;
        }

        if (TextUtils.isEmpty(party.getStart_time())) {
            T.show("请输入开始时间");
            return false;
        }

        if (TextUtils.isEmpty(party.getEnd_time())) {
            T.show("请输入结束时间");
            return false;
        }

        if (TextUtils.isEmpty(party.getTheme())) {
            T.show("请输入活动主题");
            return false;
        }
        List<String> active_image = party.getActive_image();
        if (active_image == null || active_image.size() < 1) {
            T.show("请上传至少一张照片");
            return false;
        }

        return true;
    }
}  
