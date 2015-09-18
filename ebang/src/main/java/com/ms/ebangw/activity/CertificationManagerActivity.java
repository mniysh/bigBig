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

/**
 * 工长认证的上传身份证照片
 */
public class CertificationManagerActivity extends BaseActivity {
    @Bind(R.id.act_certification_commit)
    Button bNext;
    @OnClick(R.id.act_certification_commit)
    public void setbNext(){
        this.finish();
        startActivity(new Intent(this, ManagerBackActivity.class));

    }


    @Override
    public void initView() {
        initTitle(null,"返回","工长认证",null,null);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification_manager);
        ButterKnife.bind(this);
        initView();
        initData();
        bNext.setText("下一步");

    }


}
