package com.ms.ebangw.findpassword;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.utils.VerifyUtils;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 重置密码
 */
public class ResetPasswordActivity extends BaseActivity {
    private String phone;
    private String verifyCode;

    @Bind(R.id.et_password)
    EditText passwordEt;
    @Bind(R.id.et_password_confirm)
    EditText confirmPasswordEt;
    @Bind(R.id.cb_show_password)
    CheckBox showPwdCb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void initView() {
        initTitle(null, "返回", "重置密码", null, null);
    }

    @Override
    public void initData() {
        Bundle extras = getIntent().getExtras();

        phone = extras.getString(Constants.key_phone);
        verifyCode = extras.getString(Constants.KEY_VERIFY_CODE);

        showPwdCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    passwordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    confirmPasswordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    passwordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmPasswordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }

    /**
     * 密码输入验证
     * @param password
     * @param confirmPassword
     * @return
     */
    private boolean isInputRight(String password, String confirmPassword) {
        if (VerifyUtils.isPasswordRight(password) && VerifyUtils.isPasswordRight(confirmPassword)) {
            if (TextUtils.equals(password, confirmPassword)) {
                return true;
            }else {
                T.show("两次密码输入不一致");
            }
        }
        return false;
    }

    @OnClick(R.id.btn_ok)
    public void commit() {
        String password = passwordEt.getText().toString().trim();
        String confirmPassword = confirmPasswordEt.getText().toString().trim();

        if (isInputRight(password, confirmPassword)) {
            DataAccessUtil.recoveredPassword(phone, verifyCode, password, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        boolean b = DataParseUtil.processDataResult(response);
                        if (b) {        //跳转到成功页面

                            setResult(RESULT_OK);
                            startActivity(new Intent(ResetPasswordActivity.this,
                                FoundPasswordActivity.class));
                            finish();
                        }
                    } catch (ResponseException e) {
                        e.printStackTrace();
                        T.show(e.getMessage());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });


        }



    }


}
