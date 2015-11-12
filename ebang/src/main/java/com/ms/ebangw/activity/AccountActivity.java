package com.ms.ebangw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ms.ebangw.R;

/**
 * 帐户
 * @author wangkai
 */
public class AccountActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }


    @Override
    public void initView() {
        initTitle(null, "返回", "账单", "交易明细", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, AccountDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {

    }
}
