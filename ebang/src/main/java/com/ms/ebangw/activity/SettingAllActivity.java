package com.ms.ebangw.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.ms.ebangw.R;

import butterknife.Bind;


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
        initTitle(null, null, "设置", null, null);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_all);
        initView();
        initData();
    }
}
