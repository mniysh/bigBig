package com.ms.ebangw.service;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.ms.ebangw.MyApplication;
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

    public RequestHandle login(String phone, String password, AsyncHttpResponseHandler asyncHttpResponseHandler ) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        params.put("password", password);

        return doPost(RequestUrl.login, params, asyncHttpResponseHandler);
    }


    public RequestHandle doPost(String url, RequestParams params, AsyncHttpResponseHandler asyncHttpResponseHandler) {

        if (!NetUtils.isConnected(MyApplication.getInstance())) {
            T.show("网络异常");
            return null;
        }
        initAsyncHttpClient();

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
