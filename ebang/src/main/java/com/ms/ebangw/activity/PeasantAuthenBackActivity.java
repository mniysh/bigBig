package com.ms.ebangw.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ms.ebangw.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 务工人认证的银行卡信息认证
 */
public class PeasantAuthenBackActivity extends BaseActivity {
    private LinearLayout layout;
    @Bind(R.id.bt_next)
    Button bNext;
    @OnClick(R.id.bt_next)
    public  void  next(){
        layout=(LinearLayout) getLayoutInflater().inflate(R.layout.popup_lay, null, false);
        Button bBack= (Button) layout.findViewById(R.id.popup_lay_iv_back);
        pWindow(layout,600,LinearLayout.LayoutParams.WRAP_CONTENT,bBack,null,null,null,bNext, Gravity.TOP,0,150);
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
        setContentView(R.layout.activity_peasant_authen_back);
        ButterKnife.bind(this);
        initView();
        initData();
    }


}
