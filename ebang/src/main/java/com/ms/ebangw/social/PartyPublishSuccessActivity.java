package com.ms.ebangw.social;

import android.os.Bundle;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;

/**
 * 发布成功后
 */
public class PartyPublishSuccessActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_publish_success);
        initView();
        initData();
    }

    @Override
    public void initView() {
        initTitle(null, "返回", "发布活动", null, null);
    }

    @Override
    public void initData() {

    }
}
