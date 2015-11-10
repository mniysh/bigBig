package com.ms.ebangw.setting;

import android.app.Notification;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;

import java.util.IllegalFormatFlagsException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

public class SettingMessageActivity extends BaseActivity {
    @Bind(R.id.iv_messageRemind)
    ImageView messageRemindIv;
    @Bind(R.id.iv_ring)
    ImageView ringIv;
    @Bind(R.id.iv_vibration)
    ImageView vibrationIv;

    private boolean flag_messageRemind = true;
    private boolean flag_ring = true;
    private boolean flag_vibration = true;

    @Override
    public void initView() {
        initTitle(null,"返回", "消息设置", null, null);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_message);
        ButterKnife.bind(this);
        initView();
        initData();
    }
    @OnClick(R.id.iv_messageRemind)
    public void setMessageRemind(){
        if(flag_messageRemind){
            flag_messageRemind = !flag_messageRemind;
            JPushInterface.stopPush(this);
            messageRemindIv.setImageResource(R.drawable.button_unselected);
        }else{
            flag_messageRemind = !flag_messageRemind;
            JPushInterface.resumePush(this);
            messageRemindIv.setImageResource(R.drawable.button_selected);
        }
    }
    @OnClick(R.id.iv_ring)
    public void setRing(){
        if(flag_ring){
            flag_ring = !flag_ring;
            ringIv.setImageResource(R.drawable.button_unselected);
            BasicPushNotificationBuilder build = new BasicPushNotificationBuilder(this);
            build.notificationDefaults = 0;
            JPushInterface.setPushNotificationBuilder(1, build);
        }else{
            flag_ring = !flag_ring;
            ringIv.setImageResource(R.drawable.button_selected);
        }

    }
    @OnClick(R.id.iv_vibration)
    public void setVibration(){
        if(flag_vibration){
            flag_vibration = !flag_vibration;
            vibrationIv.setImageResource(R.drawable.button_unselected);
        }else{
            flag_vibration = !flag_vibration;
            vibrationIv.setImageResource(R.drawable.button_selected);
        }

    }

}
