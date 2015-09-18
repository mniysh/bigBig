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
 * 务工人的身份证照片的上传页面
 *
 */
public class PeasantAuthenCardActivity extends BaseActivity {
    @Bind(R.id.bt_next)
    Button bNext;
    @OnClick(R.id.bt_next)
    public  void  next(){
        startActivity(new Intent(this,PeasantAuthenBackActivity.class));
        this.finish();
    }

    @Override
    public void initView() {
        initTitle(null,"返回","务工认证",null,null);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peasant_authen_card);
        ButterKnife.bind(this);
        initView();
        initData();
    }


}
