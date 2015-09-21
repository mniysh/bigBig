package com.ms.ebangw.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ms.ebangw.R;

import butterknife.ButterKnife;

public class ModifyPhoneActivity extends BaseActivity {

    @Override
    public void initView() {
        initTitle(null,"返回","手机修改",null,null);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_phone);
        ButterKnife.bind(this);
        initView();
        initData();
    }


}
