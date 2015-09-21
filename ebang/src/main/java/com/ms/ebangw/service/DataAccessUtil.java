package com.ms.ebangw.service;

import android.text.TextUtils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.NetUtils;
import com.ms.ebangw.utils.T;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-09-13 21:44
 */
public class DataAccessUtil {
    private static final String TAG = "DataAccessUtil";

    private static AsyncHttpClient mClient;
    private static void initAsyncHttpClient() {
        if (mClient == null) {
            mClient = new AsyncHttpClient();
            //修改超时时间为15秒
            mClient.setTimeout(15000);
            mClient.setResponseTimeout(15000);
        }
    }

    /**
     * 1. 注册接口
     * @param name 姓名
     * @param phone 电话
     * @param email 邮箱
     * @param gender       性别 male、 female
     * @param code 验证码
     * @param password 密码
     * @param asyncHttpResponseHandler 请求
     * @return
     */
    public static RequestHandle register(String name,String phone,String email,String gender,
                                         String code, String password, AsyncHttpResponseHandler
        asyncHttpResponseHandler ) {
        RequestParams params = new RequestParams();
        params.put("name",name);
        params.put("phone", phone);
        params.put("password", password);
        params.put("email", email);
        params.put("code", code);
        params.put("gender", gender);

        return doPost(RequestUrl.register, params, asyncHttpResponseHandler);
    }

    /**
     * 2.登录
     * @param phone 电话
     * @param password 密码
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle login(String phone, String password, AsyncHttpResponseHandler
        asyncHttpResponseHandler ) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        params.put("password", password);

        return doPost(RequestUrl.login, params, asyncHttpResponseHandler);
    }

    /**
     * 3.登出接口
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle exit(AsyncHttpResponseHandler
                                         asyncHttpResponseHandler ) {
        RequestParams params = new RequestParams();
//        params.put("phone", phone);
//        params.put("password", password);

        return doPost(RequestUrl.logout, params, asyncHttpResponseHandler);
    }

    /**
     * 4.短信接口
     * @param phone 电话
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle messageCode(String phone, AsyncHttpResponseHandler
        asyncHttpResponseHandler ) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        return doPost(RequestUrl.msg, params, asyncHttpResponseHandler);
    }




    /**
     * 5.短信修改密码接口
     * @param phone
     * @param code
     * @param password
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle updatePassword(String phone, String code, String password,
                                               AsyncHttpResponseHandler
                                                   asyncHttpResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone",phone);
        params.put("code", code);
        params.put("password", password);

        return doPost(RequestUrl.update_pwd, params, asyncHttpResponseHandler);
    }

    /**
     * 6.用旧密码修改新密码接口
     * @param phone
     * @param password
     * @param newPassword
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle changePwd(String phone, String password, String newPassword,
                                            AsyncHttpResponseHandler
                                                asyncHttpResponseHandler ) {
        RequestParams params = new RequestParams();
        params.put("phone",phone);
        params.put("password", password);
        params.put("newpassword", newPassword);

        return doPost(RequestUrl.change_pwd, params, asyncHttpResponseHandler);
    }

    /**
     * 7.个人信息验证接口
     * @param identity_card 身份证
     * @param phone     手机
     * @param province  省
     * @param city      市
     * @param area          区
     * @param area_other    门牌号
     * @param card_image_front  身份证正面
     * @param card_image_back       身份证反面
     * @param card_expiration_time  身份证到期时间
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle personIdentify(String identity_card, String phone, String province,
                                               String city, String area,String area_other,
                                               String card_image_front,
                                               String card_image_back, String card_expiration_time,
                                               AsyncHttpResponseHandler
                                              asyncHttpResponseHandler ) {
        RequestParams params = new RequestParams();
        params.put("identity_card",identity_card);
        params.put("phone",phone);
        params.put("province",province);
        params.put("city",city);
        params.put("area",area);
        params.put("area_other",area_other);
        params.put("card_image_front",card_image_front);
        params.put("card_image_back",card_image_back);
        params.put("card_expiration_time",card_expiration_time);

        return doPost(RequestUrl.person_identify, params, asyncHttpResponseHandler);
    }

    /**
     * 8.上传图片
     * @param imageFile 图片文件
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle uploadImage(File imageFile,
                                          AsyncHttpResponseHandler asyncHttpResponseHandler ) {

        return upLoadImage(RequestUrl.upload_image, imageFile, asyncHttpResponseHandler);
    }

    /**
     * 9.下载图片       RequestUrl.down_image + 图片名字
     * @param url
     * @param params
     * @param asyncHttpResponseHandler
     * @return
     */

    /**
     * 10.省市区接口 /api/common/address/child/+数字（0返回所有省与直辖市）   Get方式
     * @param num   默认0 (0返回所有省与直辖市)
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle address(String num,
                                          AsyncHttpResponseHandler
                                              asyncHttpResponseHandler ) {
        if (TextUtils.isEmpty(num)) {
            num = "0";
        }
        return doGet(RequestUrl.address + num, new RequestParams(), asyncHttpResponseHandler);
    }

    /**
     * 修改昵称接口
     * @param nickname 修改后的昵称
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle modifyNickName(String nickname,AsyncHttpResponseHandler asyncHttpResponseHandler){
        RequestParams params=new RequestParams();
        params.put("nickname",nickname);
        return doPost(RequestUrl.modify_nickName,params,asyncHttpResponseHandler);
    }


    public static RequestHandle doPost(String url, RequestParams params, AsyncHttpResponseHandler asyncHttpResponseHandler) {

        if (!NetUtils.isConnected(MyApplication.getInstance())) {
            T.show("网络异常");
            return null;
        }
        initAsyncHttpClient();
        addCommonParams(params);
        L.d(TAG, "doPost: " + url + " : " + params.toString());
        return mClient.post(url, params, asyncHttpResponseHandler);

    }

    public static RequestHandle doGet(String url, RequestParams params, AsyncHttpResponseHandler
        asyncHttpResponseHandler) {

        if (!NetUtils.isConnected(MyApplication.getInstance())) {
            T.show("网络异常");
            return null;
        }
        initAsyncHttpClient();
        addCommonParams(params);
        L.d(TAG, "doPost: " + url + " : " + params.toString());
        return mClient.get(url, params, asyncHttpResponseHandler);

    }

    /**
     * 取消所有请求
     */
    public static void cancelAllRequests() {
        if (null != mClient)
            mClient.cancelAllRequests(true);
    }

    /**
     * 上传:Post方式
     */
    public static void upLoad(String url, File file) {
        File myFile = new File("/path/to/file.png");
        RequestParams params = new RequestParams();
        try {
            params.put("profile_picture", myFile);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传图片:Post方式
     */
    private static RequestHandle upLoadImage(String url, File imageFile, AsyncHttpResponseHandler
        asyncHttpResponseHandler) {
        RequestParams params = new RequestParams();
        try {
            params.put("image", imageFile);
            params = addCommonParams(params);
            mClient.post(url, params, asyncHttpResponseHandler);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            L.d(TAG, "upLoadImage：文件不存在");
        }
        return null;
    }

    private static RequestParams addCommonParams(RequestParams params) {
        User user = MyApplication.getInstance().getUser();
        if (null != user) {

            String id = user.getId();
            String app_token = user.getApp_token();
            params.put("id", id);
            params.put("app_token", app_token);
        }

        return params;
    }
}  
