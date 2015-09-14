package com.ms.ebangw.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ms.ebangw.R;

public class QiangDanActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiang_dan);
        initView();
        initData();

    }
    @Override
    public void initView() {
        setTitle("抢单");

    }

    @Override
    public void initData() {

    }


}
