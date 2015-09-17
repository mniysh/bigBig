package com.ms.ebangw.service;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.ms.ebangw.MyApplication;
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
    private final String TAG = getClass().getSimpleName();

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
     * 登录
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
     * 短信接口
     * @param phone 电话
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle messageCode(String phone, AsyncHttpResponseHandler
        asyncHttpResponseHandler ) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        return doPost(RequestUrl.message_code, params, asyncHttpResponseHandler);
    }

    /**
     * 登出接口
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
     *  注册接口
     * @param name 姓名
     * @param phone 电话
     * @param email 邮箱
     * @param gender       性别 male、 female
     * @param categorg 类别，有四种，个人investor，农民工woker，工长headman，开发商developers
     * @param password 密码
     * @param asyncHttpResponseHandler 请求
     * @return
     */
    public static RequestHandle register(String name,String phone,String email,String gender,String categorg, String password, AsyncHttpResponseHandler
        asyncHttpResponseHandler ) {
        RequestParams params = new RequestParams();
        params.put("name",name);
        params.put("phone", phone);
        params.put("password", password);
        params.put("email", email);
        params.put("categorg", categorg);
        params.put("gender", gender);

        return doPost(RequestUrl.register, params, asyncHttpResponseHandler);
    }

    public static RequestHandle smsPassword(String phone, String code, String password,
                                            AsyncHttpResponseHandler
        asyncHttpResponseHandler ) {
        RequestParams params = new RequestParams();
        params.put("phone",phone);
        params.put("code", code);
        params.put("password", password);

        return doPost(RequestUrl.register, params, asyncHttpResponseHandler);
    }



    public static RequestHandle doPost(String url, RequestParams params, AsyncHttpResponseHandler asyncHttpResponseHandler) {

        if (!NetUtils.isConnected(MyApplication.getInstance())) {
            T.show("网络异常");
            return null;
        }
        initAsyncHttpClient();
        L.d(url + " : " + params.toString() );
        return mClient.post(url, params, asyncHttpResponseHandler);

//        mHttpUtils.send(HttpRequest.HttpMethod.POST, url, new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                int statusCode = responseInfo.statusCode;
//                String result = responseInfo.result;
//                ResponseData responseData = JsonUtil.toObj(new TypeToken<ResponseData>() {
//                }, result);
//                if (200 == statusCode) {        //返回数据是正确
//                    String data = responseData.getDate();
//
//                } else {
//
//                    T.show(responseData.getMessage());
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(HttpException error, String msg) {
//                T.show(error.getMessage());
//            }
//
//            @Override
//            public Object getUserTag() {
//                return super.getUserTag();
//            }
//        });


    }

    /**
     * 取消所有请求
     */
    public static void cancelAllRequests() {
        if (null != mClient)
            mClient.cancelAllRequests(true);
    }

    public static void upLoad() {
        File myFile = new File("/path/to/file.png");
        RequestParams params = new RequestParams();
        try {
            params.put("profile_picture", myFile);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}  
