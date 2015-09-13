package com.ms.ebangw.service;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.bean.ResponseData;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.utils.JsonUtil;
import com.ms.ebangw.utils.NetUtils;
import com.ms.ebangw.utils.T;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-09-13 21:44
 */
public class DataAccessUtil {
    private final String TAG = getClass().getSimpleName();
    private static HttpUtils mHttpUtils;
    private static void init() {
        if (mHttpUtils == null) {
            mHttpUtils = new HttpUtils();
        }
    }

    public void login(String account, String password, RequestCallBack<User> callBack ) {



    }


    public void doPost(String url, RequestParams params, RequestCallBack callBack) {

        if (!NetUtils.isConnected(MyApplication.getInstance())) {
            T.show("网络异常");
            return;
        }

        mHttpUtils.send(HttpRequest.HttpMethod.POST, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                int statusCode = responseInfo.statusCode;
                String result = responseInfo.result;
                ResponseData responseData = JsonUtil.toObj(new TypeToken<ResponseData>() {
                }, result);
                if (200 == statusCode) {        //返回数据是正确
                    String data = responseData.getDate();

                } else {

                    T.show(responseData.getMessage());

                }

            }

            @Override
            public void onFailure(HttpException error, String msg) {
                T.show(error.getMessage());
            }

            @Override
            public Object getUserTag() {
                return super.getUserTag();
            }
        });


    }
}  
