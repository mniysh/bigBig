package com.ms.ebangw.service;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.utils.L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-09-14 09:45
 */
public class DataParseUtil {
    private static final String TAG = "DataParseUtil";


    /**
     * 注册接口
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static User register(JSONObject jsonObject) throws ResponseException {
        String  data = processDataStr(jsonObject);
        Gson gson = new Gson();
        User user = gson.fromJson(data, User.class);
        return user;
    };

    /**
     * 登录
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static User login(JSONObject jsonObject) throws ResponseException {
                String  data = processDataStr(jsonObject);
                Gson gson = new Gson();
                User user = gson.fromJson(data, User.class);
                return user;
    };

    /**
     * 登出接口
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static boolean exit(JSONObject jsonObject) throws ResponseException {
        return processDataResult(jsonObject);
    };

    /**
     * 短信接口
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static boolean messageCode(JSONObject jsonObject) throws ResponseException {
        return processDataResult(jsonObject);
    };

    /**
     * 修改昵称接口
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static boolean modifyName(JSONObject jsonObject)throws  ResponseException{
        return processDataResult(jsonObject);

    }


    /**
     * 通用解析方法， 判断请求是否成功
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static  boolean  processDataResult(JSONObject jsonObject) throws ResponseException {
        if (null == jsonObject) {
            L.d(TAG, "processDataResult: json对象为null");
            return false;
        }

        try {
            String code = jsonObject.getString("code");
            String message = jsonObject.getString("message");
            if (TextUtils.equals("200", code)) {        //数据正确
                return true;
            } else {
                throw new ResponseException(code, message);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }





    public static  String  processDataStr(JSONObject jsonObject) throws ResponseException {
        if (null == jsonObject) {
            L.d(TAG, "processData: json对象为null");
            return null;
        }

        try {
            String code = jsonObject.getString("code");
            String message = jsonObject.getString("message");
            if (TextUtils.equals("200", code)) {        //数据正确
                return jsonObject.optString("data", "");
            } else {
                throw new ResponseException(code, message);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Json数据解析  返回正确的JSONObject
     * @param jsonObject
     * @return
     * @throws ResponseException    抛出异常：code message
     */
    public static  JSONObject processData(JSONObject jsonObject) throws ResponseException {
        if (null == jsonObject) {
            L.d(TAG, "processData: json对象为null");
            return null;
        }

        try {
            String code = jsonObject.getString("code");
            String message = jsonObject.getString("message");
            if (TextUtils.equals("200", code)) {        //数据正确
                return jsonObject.getJSONObject("data");
            } else {
                throw new ResponseException(code, message);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static JSONArray processDataArray(JSONObject jsonObject) throws ResponseException {
        if (null == jsonObject) {
            L.d(TAG, "processDataArray: json对象为null");
            return null;
        }
        try {
            String code = jsonObject.getString("code");
            String message = jsonObject.getString("message");
            if (TextUtils.equals("200", code)) {        //数据正确
                return jsonObject.getJSONArray("data");
            } else {
                throw new ResponseException(code, message);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}  
