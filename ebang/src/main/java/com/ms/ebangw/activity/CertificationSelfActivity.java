package com.ms.ebangw.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.ms.ebangw.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人认证身份认证的传照片的页面
 */
public class CertificationSelfActivity extends BaseActivity {


    private LinearLayout layout;
    @Bind(R.id.act_certification_commit)
    Button bCommit;
    @OnClick(R.id.act_certification_commit)
    public void commit(){

        layout=(LinearLayout) getLayoutInflater().inflate(R.layout.popup_lay, null, false);
        Button bBack= (Button) layout.findViewById(R.id.popup_lay_iv_back);
        pWindow(layout,600,LinearLayout.LayoutParams.WRAP_CONTENT,bBack,null,null,null,bCommit,Gravity.TOP,0,150);

    }
    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        initTitle(null,"返回","实名认证",null,null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);
        ButterKnife.bind(this);
        initView();
        initData();



    }


}
