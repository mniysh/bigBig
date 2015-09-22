package com.ms.ebangw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.db.UserDao;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.utils.VerifyUtils;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 登录页面
 */

public class LoginActivity extends BaseActivity{
    @Bind(R.id.et_account)
    EditText accountEt;         //用户名
    @Bind(R.id.et_password)
    EditText passwordEt;        //密码
    @Bind(R.id.act_login_but_login)
    Button loginBtn;            //登录
    @Bind(R.id.tv_register)
    TextView registerTv;        //注册
    @Bind(R.id.tv_find_password)       //找回密码
    TextView findPwdTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        ButterKnife.bind(this);
        initView();
        initData();

    }

    public void initView() {
        initTitle(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        }, "返回", "登录", null, null);
    }

    @Override
    public void initData() {

        registerTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        findPwdTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });



    }

    @OnClick(R.id.act_login_but_login)
    public void loginAccount(View view) {
        String account = accountEt.getText().toString().trim();
        String pwd = passwordEt.getText().toString().trim();
        if (isLoginInfoRight(account, pwd)) {
            login(account, pwd);
        }
    }

    /**
     * 判断输入框输入是否正确
     * @param phone
     * @param password
     * @return
     */
    public boolean isLoginInfoRight(String phone, String password) {
        if(TextUtils.isEmpty(phone)){
            T.show("账号不能为空");
            return false;
        }

        if (!VerifyUtils.isPhone(phone) && !VerifyUtils.isEmail(phone) ) {
            T.show("请输入正确的手机号或邮箱");
            return false;
        }

        if(TextUtils.isEmpty(password)){
            T.show("密码不能为空");
            return false;
        }

        return true;

    }

    /**
     * 登录请求
     * @param phone
     * @param password
     */
    public void login(String phone, String password) {
        DataAccessUtil.login(phone,password,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    User user = DataParseUtil.login(response);

                    if(null != user) {
                        MyApplication.getInstance().saveUser(user);     //保存或更新User信息
                        L.d("xxx", "user的内容登录部分的" + MyApplication.getInstance().saveUser(user));
                        L.d("xxx", "user的内容登录部分的" + user.toString());
                        //跳转到主页
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constants.KEY_USER, user);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();

                    }else {
                        T.show("登录失败，请重试！");
                    }

                } catch (ResponseException e) {
                    e.printStackTrace();
                    T.show(e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }


    //清空文本框
    private void empty_content() {
        passwordEt.setText("");
        accountEt.setText("");
        new UserDao(this);
    }


}
