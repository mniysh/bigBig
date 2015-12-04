package com.ms.ebangw.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.easemob.easeui.EaseConstant;
import com.ms.ebangw.chat.ChatActivity;

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
    private static  void chatTo(Context context ,String userId, int type) {
        Bundle bundle = new Bundle();
            bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE, type);

        bundle.putString("userId", userId);

        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void chatBySingle(Context context, String userId) {
        chatTo(context, userId, EaseConstant.CHATTYPE_SINGLE);
    }
}  
