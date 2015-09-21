package com.ms.ebangw.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ms.ebangw.R;

import butterknife.ButterKnife;

public class ModifyPasswordAvtivity extends BaseActivity {

    @Override
    public void initView() {
        initTitle(null,"返回","密码修改",null,null);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password_avtivity);
        ButterKnife.bind(this);
        initView();
        initData();
    }


}
