package com.ms.ebangw.findpassword;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.utils.VerifyUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 输入手机号  （找回密码）
 * @author wangkai
 */
public class SetPhoneActivity extends BaseActivity {
    private CountDownTimer countDownTimer;
    private Handler mHandler;
    @Bind(R.id.et_phone)
    EditText phoneEt;
    @Bind(R.id.et_verifyCode)
    EditText verifyCodeEt;
    @Bind(R.id.btn_smsCode)
    Button smsCodeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_phone);
        ButterKnife.bind(this);
        initView();
        initData();

    }

    @Override
    public void initView() {
        initTitle(null, "返回", "找回密码", null, null);

    }

    @Override
    public void initData() {
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                int what = msg.what;
                if (what == 0) {
                    smsCodeBtn.setPressed(false);
                    smsCodeBtn.setClickable(true);
                    smsCodeBtn.setText("获取验证码");
                }else {
                    smsCodeBtn.setText(what + " 秒");
                }

                return false;
            }
        });
    }
    @OnClick(R.id.btn_next)
    public void goNext() {
        final String phone = phoneEt.getText().toString().trim();
        final String verifyCode = verifyCodeEt.getText().toString().trim();
        if (isInputRight(phone, verifyCode)) {
            DataAccessUtil.checkCode(phone, verifyCode, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        boolean b = DataParseUtil.processDataResult(response);
                        if (b) {
                            T.show(response.getString("message"));
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.key_phone, phone);
                            bundle.putString(Constants.KEY_VERIFY_CODE, verifyCode);

                            Intent intent = new Intent(SetPhoneActivity.this, ResetPasswordActivity.class);
                            intent.putExtras(bundle);
                            startActivityForResult(intent, 1000);
                        }
                    } catch (ResponseException e) {
                        e.printStackTrace();
                        T.show(e.getMessage());
                    } catch (JSONException e) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            finish();
        }
    }

    /**输入是否正确*/
    public boolean isInputRight(String phone, String verifyCode) {

        if (!VerifyUtils.isPhone(phone)) {
            T.show("请输入正确的手机号");
            return false;
        }

        if (TextUtils.isEmpty(verifyCode)) {
            T.show("验证码不能为空");
            return false;
        }

        return true;
    }


    /**
     * 获取短信验证码
     */
    @OnClick(R.id.btn_smsCode)
    public void getMsmCode() {
        String phone = phoneEt.getText().toString().trim();
        if (VerifyUtils.isPhone(phone)) {

            DataAccessUtil.messageCode(phone, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        boolean b = DataParseUtil.messageCode(response);
                        L.d("xxx", b + "b的值");
                        if (b) {
                            T.show("验证码已发送，请注意查收");
                            smsCodeBtn.setPressed(true);
                            smsCodeBtn.setClickable(false);
                            executeCountDown();
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
        }else {
            T.show("请输入正确的手机号");

        }
    }

    private void executeCountDown() {
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mHandler.sendEmptyMessage((int)(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                mHandler.sendEmptyMessage(0);
            }
        };
        countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer != null){
            countDownTimer.cancel();
        }

    }


}
