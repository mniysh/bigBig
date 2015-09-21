package com.ms.ebangw.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置页面
 */
public class SettingActivity extends BaseActivity {


    @Bind(R.id.tv_nickName)
    TextView tvNickName;
    /**
     * 三个修改按钮
     */
    @Bind(R.id.tv_nameModify)
    TextView tvNameModify;
    @Bind(R.id.tv_phoneModify)
    TextView tvPhoneModify;
    @Bind(R.id.tv_passModify)
    TextView tvPassModify;
    @OnClick(R.id.tv_nameModify)
    public void changeNickName(){
        startActivity(new Intent(this,ModifyNickNameActivity.class));

    }
    @OnClick(R.id.tv_phoneModify)
    public void changePhone(){
        startActivity(new Intent(this,ModifyPhoneActivity.class));

    }
    @OnClick(R.id.tv_passModify)
    public void changePassword(){
        startActivity(new Intent(this,ModifyPasswordAvtivity.class));

    }



    @Override
    public void initView() {
        initTitle(null,"返回","设置",null,null);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        User user = getUser();
        if(null!=user){
            String newName=user.getNick_name();
            tvNickName.setText(newName);
        }

    }
}