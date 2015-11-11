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
import com.ms.ebangw.utils.L;

import java.util.IllegalFormatFlagsException;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class SettingMessageActivity extends BaseActivity {
    @Bind(R.id.iv_messageRemind)
    ImageView messageRemindIv;
    @Bind(R.id.iv_ring)
    ImageView ringIv;
    @Bind(R.id.iv_vibration)
    ImageView vibrationIv;
    private String alias = "";
    private Set<String> tags;

    private boolean flag_messageRemind = true;
    private boolean flag_ring = true;
    private boolean flag_vibration = true;

    @Override
    public void initView() {
        initTitle(null,"返回", "消息设置", null, null);
    }

    @Override
    public void initData() {
//        JPushInterface.setAliasAndTags(this, alias, tags, new TagAliasCallback() {
//            @Override
//            public void gotResult(int i, String s, Set<String> set) {
//                if( i == 0){
//                    L.d("别名，标签设置成功");
//                    alias = s;
//                    tags = set;
//                }else{
//                    L.d("别名设置失败");
//                }
//            }
//        });

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
            BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
            builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;
            builder.notificationDefaults = Notification.DEFAULT_ALL;
            JPushInterface.setPushNotificationBuilder(2, builder);
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
