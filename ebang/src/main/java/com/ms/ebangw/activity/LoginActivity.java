package com.ms.ebangw.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.utils.T;


/**
 * 登录页面
 */

public class LoginActivity extends BaseActivity implements OnClickListener {
    private TextView tv_register;
    private TextView tv_findpassword;

    private EditText et_username, et_password;
    private String et_username_content, et_password_content;
    private Button but_login;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        //初始化组件

        initView();
        Log.d("aa", "bb");
        //获取组件数据
        getDatas();
        //组件操作
        initViewOper();

    }

    private void getDatas() {
        // TODO Auto-generated method stub
        et_username_content = et_username.getText().toString().trim();
        et_password_content = et_password.getText().toString().trim();

    }

    private void initViewOper() {
        // TODO Auto-generated method stub
        tv_register.setOnClickListener(this);
        tv_findpassword.setOnClickListener(this);

        but_login.setOnClickListener(this);
        tv_findpassword.setOnClickListener(this);
        initTitle(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        }, "返回", "登录", null, null);

    }


    public void initView() {
        et_username = (EditText) findViewById(R.id.act_login_edit_user);
        et_password = (EditText) findViewById(R.id.act_login_edit_password);
        tv_register = (TextView) findViewById(R.id.act_login_register);
        tv_findpassword = (TextView) findViewById(R.id.act_login_find_password);

        but_login = (Button) findViewById(R.id.act_login_but_login);

    }

    @Override
    public void initData() {

    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.act_login_register:
                startActivity(new Intent(this, RegisterActivity.class));
                this.finish();
                break;
            case R.id.act_login_find_password:
                startActivity(new Intent(this, CommentActivity.class));
                this.finish();
                break;

            //登录按钮事件
            case R.id.act_login_but_login:
                getDatas();
                if (et_username_content == null || et_password_content == null) {
                    T.show("账号密码不能为空");
                    return;
                }
                if (check_user(et_username_content, et_password_content)) {
                    //这里应该跳转到用户中心
                } else {
                    T.show("输入的用户名或者密码错误，请重新输入");
                    empty_content();
                    return;
                }

                break;

            default:
                break;
        }
    }

    //保留接口，判断当前输入信息是否是合法的用户信息
    private boolean check_user(String et_username_content2,
                               String et_password_content2) {
        // TODO Auto-generated method stub
        return false;
    }

    //清空文本框
    private void empty_content() {
        // TODO Auto-generated method stub
        et_password.setText("");
        et_username.setText("");
    }


}
