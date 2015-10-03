package com.ms.ebangw.findpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.activity.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FoundPasswordActivity extends BaseActivity {

    @Bind(R.id.btn_ok)
    Button okBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_password);
        ButterKnife.bind(this);
        initView();
        initData();
    }


    @Override
    public void initView() {
        initTitle("成功");
    }

    @Override
    public void initData() {
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoundPasswordActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}
