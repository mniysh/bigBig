package com.ms.ebangw.setting;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {
    @Override
    public void initView() {
        initTitle(null, "返回" , "关于我们", null , null);
    }

    @Override
    public void initData() {
        telBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.DIAL");
                intent.setData(Uri.parse("tel:400 616 0066"));
                startActivity(intent);

            }
        });
    }

    @Bind(R.id.btn_dial)
    Button telBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about2);
        ButterKnife.bind(this);
        initView();
        initData();
    }
}
