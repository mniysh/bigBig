package com.ms.ebangw;

import android.content.Context;

import com.ms.ebangw.MyApplication;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.utils.L;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public static String getDeviceInfo(Context context) {
        try{
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();

            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if( TextUtils.isEmpty(device_id) ){
                device_id = mac;
            }

            if( TextUtils.isEmpty(device_id) ){
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);
            L.d(json.toString());
            return json.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void saveUser(){
        User user = new User();
        user.setId("3333");
        user.setApp_token("dlsfjlsfjklsfjk");

        boolean b = MyApplication.getInstance().saveUser(user);
        L.d("Test saveUser : " + b);
    }
}