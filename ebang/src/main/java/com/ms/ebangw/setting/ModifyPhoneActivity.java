package com.ms.ebangw.setting;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改手机页面
 */
public class ModifyPhoneActivity extends BaseActivity {
    private String phone,codeValue;
    private Handler handler;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_phone);
        ButterKnife.bind(this);
        initView();
        initData();
    }


    @Bind(R.id.et_newPhone)
    EditText etPhone;

    @Bind(R.id.et_code)
    EditText code;

    @Bind(R.id.bt_twoFinish)
    Button bfinish;

    @Bind(R.id.bt_code)
    Button bCode;

    @OnClick(R.id.bt_code)
    public void getCode(){
        phone=etPhone.getText().toString();
        if(VerifyUtils.isPhone(phone)){
            DataAccessUtil.messageCode(phone,new JsonHttpResponseHandler(){

                @Override
                public void onStart() {


                    excuteDownCount();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    L.d("xxx", "phone的值" + phone);
                    try {
                        boolean b= DataParseUtil.messageCode(response);
                        if(b){
                            T.show("验证码已发请注意查收");

                        }

                    } catch (ResponseException e) {
                        e.printStackTrace();
                        T.show(e.getMessage());
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    T.show(responseString);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });

        }else{
            T.show("请输入正确手机号");
        }

    }
    @OnClick(R.id.bt_twoFinish)
    public  void bFinish(){
        phone=etPhone.getText().toString().trim();
        codeValue=code.getText().toString().trim();

        if(VerifyUtils.isPhone(phone)&&VerifyUtils.isCode(codeValue)){
            DataAccessUtil.checkCode(phone, codeValue, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        boolean b = DataParseUtil.processDataResult(response);
                        if (b) {
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.KEY_VERIFY_CODE, codeValue);
                            Intent intent = new Intent(ModifyPhoneActivity.this, ModifyPhone02Activity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            ModifyPhoneActivity.this.finish();
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


    @Override
    public void initView() {
        initTitle(null, "返回", "手机验证", null, null);
        if(getUser()!=null){
            etPhone.setText(getUser().getPhone());
        }
        setStarRed();


    }

    @Override
    public void initData() {
        handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                int what=msg.what;
                if(what==0){
                    bCode.setPressed(false);
                    bCode.setClickable(true);
                    bCode.setText("获取验证码");
                }else{
                    bCode.setText(what+"秒");
                }

                return false;
            }
        });


    }
    public void setStarRed() {
        int[] resId = new int[]{R.id.tv_phone,R.id.tv_code};
        for (int i = 0; i < resId.length; i++) {
            TextView a = (TextView) findViewById(resId[i]);
            String s = a.getText().toString();
            SpannableString spannableString = new SpannableString(s);
            spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            a.setText(spannableString);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer != null){
            countDownTimer.cancel();
        }

    }
    public void excuteDownCount(){
        bCode.setPressed(true);
        bCode.setClickable(false);
        countDownTimer=new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                handler.sendEmptyMessage((int) (millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                handler.sendEmptyMessage(0);
            }
        };
        countDownTimer.start();
    }
}
