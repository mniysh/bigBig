package com.ms.ebangw.setting;

import android.app.Notification;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.utils.SPUtils;

import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

public class SettingMessageActivity extends BaseActivity {

    @Bind(R.id.cb_messageRemind)
    CheckBox cbMessageRemind;
    @Bind(R.id.cb_ring)
    CheckBox cbRing;
    @Bind(R.id.cb_vibration)
    CheckBox cbVibration;
    private String alias = "";
    private Set<String> tags;

    private boolean flag_messageRemind = true;
    private boolean flag_ring = true;
    private boolean flag_vibration = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_message);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void initView() {
        initTitle(null, "返回", "消息设置", null, null);
        flag_messageRemind = (boolean) SPUtils.get(Constants.KEY_MESSAGE_SETTING_ALERT, true);
        flag_ring = (boolean) SPUtils.get(Constants.KEY_MESSAGE_SETTING_RING, true);
        flag_vibration = (boolean) SPUtils.get(Constants.KEY_MESSAGE_SETTING_VIBRATE, true);

        cbMessageRemind.setChecked(flag_messageRemind);
        cbRing.setChecked(flag_ring);
        cbVibration.setChecked(flag_vibration);
    }

    @Override
    public void initData() {

        cbMessageRemind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    JPushInterface.resumePush(MyApplication.getInstance());
                } else {
                    JPushInterface.stopPush(MyApplication.getInstance());
                }
                SPUtils.put(Constants.KEY_MESSAGE_SETTING_ALERT, isChecked);
            }
        });

        cbRing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPUtils.put(Constants.KEY_MESSAGE_SETTING_RING, isChecked);
                updateRingAndVibrate();
            }
        });

        cbVibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPUtils.put(Constants.KEY_MESSAGE_SETTING_VIBRATE, isChecked);
                updateRingAndVibrate();
            }
        });
    }

    private void updateRingAndVibrate() {

        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(MyApplication.getInstance());
        builder.statusBarDrawable = R.drawable.ms_logo;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为自动消失

        if (cbRing.isChecked() && cbVibration.isChecked()) {
            builder.notificationDefaults = Notification.DEFAULT_SOUND|Notification
                .DEFAULT_VIBRATE|Notification
                .DEFAULT_LIGHTS;  // 设置为铃声与震动都要
        }else if (cbRing.isChecked() && !cbVibration.isChecked()) {
            builder.notificationDefaults = Notification.DEFAULT_SOUND|Notification
                .DEFAULT_LIGHTS;  //  不要震动
        }else if (!cbRing.isChecked() && cbVibration.isChecked()) {
            builder.notificationDefaults = Notification.DEFAULT_VIBRATE|Notification
                .DEFAULT_LIGHTS;  //不要铃声
        }else {
            builder.notificationDefaults = Notification.DEFAULT_LIGHTS; //无铃声和震动
        }

        JPushInterface.setPushNotificationBuilder(1, builder);
    }

}
