package com.ms.ebangw.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ms.ebangw.R;

/**
 * 服务协议
 */
public class ServiceContractActivity extends BaseActivity {

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_contract);
        initTitle(null, "返回", "亿帮无忧使用协议", null, null);
    }


}
