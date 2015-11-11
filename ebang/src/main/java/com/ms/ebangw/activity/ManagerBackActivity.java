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
 * 工长的认证的银行卡页面
 */
public class ManagerBackActivity extends BaseActivity {
    private LinearLayout layout;
    @Bind(R.id.act_pea_submit)
    Button bSubmit;
//    @OnClick(R.id.act_pea_submit)
//    public void submit(){
//
//        layout=(LinearLayout) getLayoutInflater().inflate(R.layout.popup_lay, null, false);
//        Button bBack= (Button) layout.findViewById(R.id.popup_lay_iv_back);
//        pWindow(layout, 600, LinearLayout.LayoutParams.WRAP_CONTENT, bBack, null, null, null, bSubmit, Gravity.TOP, 0, 150);
//    }
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
        setContentView(R.layout.activity_manager_back);
        ButterKnife.bind(this);
        initView();
        initData();
    }


}
