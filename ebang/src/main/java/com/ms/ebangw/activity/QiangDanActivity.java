package com.ms.ebangw.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;


import com.ms.ebangw.R;

import butterknife.Bind;
import butterknife.OnClick;

public class QiangDanActivity extends BaseActivity {
    @Bind(R.id.activity_qiang_dan_but_qianddan)
    Button qiangdan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiang_dan);
        initView();
        initData();

    }

    @OnClick
    @Override
    public void initView() {
        initTitle(new OnClickListener() {
            @Override
            public void onClick(View v) {
                QiangDanActivity.this.finish();
            }
        },"返回","抢单",null,null);



    }

    @Override
    public void initData() {

    }


}
