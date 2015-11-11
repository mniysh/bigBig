package com.ms.ebangw.setting;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.utils.T;

import butterknife.Bind;
import butterknife.ButterKnife;


public class FeelbackSettingActivity extends BaseActivity {
    @Bind(R.id.et_content)
    EditText contentEt;
    @Bind(R.id.bt_commit)
    Button commitBt;
    private String content;


    @Override
    public void initView() {
        initTitle(null, "返回" , "意见反馈", null, null);
    }

    @Override
    public void initData() {

        commitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = contentEt.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    T.show("内容不能为空");

                }else{
                    T.show("该功能敬请期待！");
                }
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feelback_setting);
        ButterKnife.bind(this);
        initView();
        initData();
    }
}
