package com.ms.ebangw.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.ms.ebangw.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FactoryAutherIdentityActivity extends BaseActivity {
    @Bind(R.id.bt_next)
    Button bNext;
    @OnClick(R.id.bt_next)
    public void next(){
        this.finish();
        startActivity(new Intent(this, FactoryAutherCompleteActivity.class));
    }
    @Override
    public void initView() {
        initTitle(null,"返回","企业认证",null,null);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory_auther_identity);
        ButterKnife.bind(this);
        initView();
        initData();

    }


}
