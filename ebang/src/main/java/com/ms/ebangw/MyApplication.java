package com.ms.ebangw;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.ms.ebangw.bean.Craft;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.db.UserDao;
import com.ms.ebangw.listener.MyLocationListener;
import com.ms.ebangw.utils.L;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class MyApplication extends Application {

    public static MyApplication instance;
    public Bitmap mBitmap;
    public String imagePath;
    private int flag_sub;
    private String phone;
    private String password;
    private Craft craft;
    private BDLocation location;
    public LocationClient mLocationClient = null;
    private boolean flag_home;

    public boolean isFlag_home() {
        return flag_home;
    }

    public void setFlag_home(boolean flag_home) {
        this.flag_home = flag_home;
    }

    public Craft getCraft() {
        return craft;
    }

    public void setCraft(Craft craft) {
        this.craft = craft;
    }


    /**
     * 定位得到的位置描述
     */
    public String mLocation;
    /**
     * 存放活动状态的(未被销毁)的Activity列表
     */
    public static List<Activity> unDestroyActivityList = new ArrayList<Activity>();


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initUMeng();
        initLocation();
        initJpush();
    }

    /**
     * 初始化友盟    友盟相关log的tag是MobclickAgent。
     */
    private void initUMeng() {
        MobclickAgent.setDebugMode(true);
    }

    /**
     * 初始化百度定位
     */
    private void initLocation(){

        mLocationClient = new LocationClient(this);     //声明LocationClient类
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(new MyLocationListener());    //注册监听函数
        mLocationClient.start();

    }

    public void startLocation() {
        mLocationClient.start();
    }

    public void endLocation() {
        mLocationClient.stop();
    }

    /**
     * 初始化极光推送
     */
    private void initJpush() {
        JPushInterface.setDebugMode(false);
        User user = getUser();
        if (null != user) {
            String id = user.getId();
            JPushInterface.setAlias(this, id, new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
                    if (i == 0) {
                        L.d("setAlias: 极光alias设置成功, alias: " + s);
                    }else {
                        L.d("setAlias: 极光alias设置失败, 返回的状态码: " + i);
                    }
                }
            });
        }

        JPushInterface.init(this);
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
    public String getmLocation() {
        return mLocation;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
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

    public User getUser() {
        UserDao userDao = new UserDao(this);
        return userDao.getUser();
    }

    public boolean saveUser(User user) {
        UserDao userDao = new UserDao(this);
        return userDao.add(user);
    }

    public BDLocation getLocation() {
        return mLocationClient.getLastKnownLocation();
    }

    public void setLocation(BDLocation location) {
        this.location = location;
    }
}
