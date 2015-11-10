package com.ms.ebangw.setting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SettingAllActivity extends BaseActivity {
    @Bind(R.id.ll_security)
    LinearLayout lSecurity;
    @Bind(R.id.ll_message)
    LinearLayout lMessage;
    @Bind(R.id.ll_update)
    LinearLayout lUpdate;
    @Bind(R.id.ll_feedback)
    LinearLayout lFeedback;
    @Bind(R.id.ll_about_our)
    LinearLayout lAboutOut;
    @Override
    public void initView() {
        initTitle(null, "返回", "设置", null, null);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_all);
        ButterKnife.bind(this);
        initView();
        initData();
    }
    @OnClick(R.id.ll_security)
    public void goSecuritySetting(){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
        finish();
    }
    @OnClick(R.id.ll_message)
    public void goMessageSetting(){
        Intent intent = new Intent(this, SettingMessageActivity.class);
        startActivity(intent);
        finish();
    }
    @OnClick(R.id.ll_update)
    public void goUpdateSetting(){

    }
    @OnClick(R.id.ll_feedback)
    public void goFeelbackSetting(){

    }
    @OnClick(R.id.ll_about_our)
    public void goAboutOurSetting(){

    }
}
