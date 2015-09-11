package com.ms.ebangw;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {



    public static MyApplication instance;
    private int flag_sub;
    private String phone;
    private String password;
    /**
     * 存放活动状态的(未被销毁)的Activity列表
     */
    public static List<Activity> unDestroyActivityList = new ArrayList<Activity>();


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public int getFlag_sub() {
        return flag_sub;
    }

    public void setFlag_sub(int flag_sub) {
        this.flag_sub = flag_sub;
    }


    public static MyApplication getInstance() {
        return instance;
    }


    /**
     * 退出应用
     */
    public void quit() {
        for (Activity activity : unDestroyActivityList) {
            if (null != activity) {
                activity.finish();
            }
        }
        unDestroyActivityList.clear();
    }

}












