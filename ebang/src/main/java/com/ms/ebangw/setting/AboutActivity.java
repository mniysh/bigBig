package com.ms.ebangw.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.utils.AppUtils;
import com.ms.ebangw.utils.T;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {
    @Bind(R.id.btn_dial)
    Button telBt;
    @Bind(R.id.tv_version)
    TextView tvVersion;


    @Override
    public void initView() {
        initTitle(null, "返回", "关于我们", null, null);
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

        String versionName = AppUtils.getVersionName(this);
        tvVersion.setText("当前版本：" + versionName);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about2);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @OnClick(R.id.ll_update)
    public void goUpdateSetting(){
        initUmengUpdata();
    }

    private void initUmengUpdata() {
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
            @Override
            public void onUpdateReturned(int i, UpdateResponse updateResponse) {
                boolean hasUpdata = false;
                if (updateResponse != null) {
                    hasUpdata = updateResponse.hasUpdate;
                }
                if(!hasUpdata){
                    T.show("已经是最新版本");
                }
            }
        });
        UmengUpdateAgent.update(this);
    }
}
