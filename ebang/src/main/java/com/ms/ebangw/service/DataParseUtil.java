package com.ms.ebangw.service;

import com.google.gson.Gson;
import com.ms.ebangw.bean.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-09-14 09:45
 */
public class DataParseUtil {


    public static User login(JSONObject jsonObject) {
        try {
            String data = jsonObject.getString("data");
            Gson gson = new Gson();
            User user = gson.fromJson(data, User.class);
            return user;

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    };

}  
