package com.easemob.easeui.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.easemob.easeui.R;
import com.easemob.easeui.controller.EaseUI;
import com.easemob.easeui.controller.EaseUI.EaseUserProfileProvider;
import com.easemob.easeui.domain.EaseUser;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.bean.EMUser;
import com.ms.ebangw.db.EMUserDao;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;

import org.apache.http.Header;
import org.json.JSONObject;

public class EaseUserUtils {
    
    static EaseUserProfileProvider userProvider;
    
    static {
        userProvider = EaseUI.getInstance().getUserProfileProvider();
    }
    
    /**
     * 根据username获取相应user
     * @param username
     * @return
     */
    public static EaseUser getUserInfo(String username){
        if(userProvider != null)
            return userProvider.getUser(username);
        
        return null;
    }
    
    /**
     * 设置用户头像
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView){
    	final EaseUser user = getUserInfo(username);
        EMUserDao emUserDao = new EMUserDao(context);
//        List<EMUser> allEmUser = emUserDao.getAllEmUser();

//        EMUser emUser;
//        for (int i = 0; i < allEmUser.size(); i++) {
//            emUser = allEmUser.get(i);
//            if (TextUtils.equals(username, emUser.getUserId())) {
//
//            }
//        }
        EMUser emUser = emUserDao.getUserById(Integer.parseInt(username));
        if (null != emUser && !TextUtils.isEmpty(emUser.getHead_image())) {
            Glide.with(context).load(emUser.getHead_image()).diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ease_default_avatar).into(imageView);
        } else {
            loadAndSaveAvatar(context, username, imageView);
        }



//        if(user != null && user.getAvatar() != null){
//            try {
//                int avatarResId = Integer.parseInt(user.getAvatar());
////                Glide.with(context).load(avatarResId).into(imageView);
//                loadAndSaveAvatar(context, username, imageView);
//            } catch (Exception e) {
//                //正常的string路径
//                Glide.with(context).load(user.getAvatar()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ease_default_avatar).into(imageView);
//            }
//        }else{
////            Glide.with(context).load(R.drawable.ease_default_avatar).into(imageView);
//            loadAndSaveAvatar(context, username, imageView);
//        }
    }

    public static void loadAndSaveAvatar(final Context context, final String username, final ImageView
        imageView) {
        if (!TextUtils.isEmpty(username)) {
            DataAccessUtil.queryAvatar(username, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        EMUser emUser = DataParseUtil.queryAvatar(response);
                        if (emUser != null) {
                            emUser.setUserId(username);
                            saveEmUser(context, emUser);
                            Glide.with(context).load(emUser.getHead_image()).diskCacheStrategy
                                (DiskCacheStrategy
                                .ALL)
                                .placeholder(R.drawable.ease_default_avatar).into(imageView);
                        }
                    } catch (ResponseException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        }
    }

    public static boolean saveEmUser(Context context, EMUser emUser) {

        EMUserDao emUserDao = new EMUserDao(context);
        return emUserDao.update(emUser);

    }
    
    /**
     * 设置用户昵称
     */
    public static void setUserNick(String username,TextView textView){
        EMUserDao emUserDao = new EMUserDao(MyApplication.getInstance());
        EMUser emUser = emUserDao.getUserById(Integer.parseInt(username));
        if (emUser != null && !TextUtils.isEmpty(emUser.getReal_name())) {
            if (textView != null) {
                textView.setText(emUser.getReal_name());
            }else {
                textView.setText(username);
            }
        }

//        if(textView != null){
//        	EaseUser user = getUserInfo(username);
//        	if(user != null && user.getNick() != null){
//        		textView.setText(user.getNick());
//        	}else{
//        		textView.setText(username);
//        	}
//        }
    }
    
}
