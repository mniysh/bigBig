package com.ms.ebangw.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.easemob.easeui.EaseConstant;
import com.ms.ebangw.bean.EMUser;
import com.ms.ebangw.chat.ChatActivity;
import com.ms.ebangw.db.EMUserDao;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-12-04 15:15
 */
public class ChartUtil {

    /**
     * 聊天
     * @param context
     * @param userId
     * @param type  {@link EaseConstant}
     */
    public static  void chatTo(Context context , EMUser emUser) {
        if (null != emUser) {
            new EMUserDao(context).update(emUser);
            Bundle bundle = new Bundle();
            bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE, emUser.getType());
            bundle.putString("userId", emUser.getUserId());

            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);

        }
    }


}  
