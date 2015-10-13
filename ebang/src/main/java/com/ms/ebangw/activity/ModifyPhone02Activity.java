package com.ms.ebangw.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
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
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyPhone02Activity extends BaseActivity {
    private String newPhone, newCode;
    private boolean flag_code;
    private CountDownTimer countDownTimer;
    private Handler handler;
    @Override
    public void initView() {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_phone02);
        ButterKnife.bind(this);
        initTitle(null, "返回", "修改手机", null, null);
        initView();
        initData();

    }
    @Bind(R.id.et_newPhone)
    EditText etNewPhone;
    @Bind(R.id.bt_code)
    Button bCode;
    @Bind(R.id.et_code)
    EditText eNewCode;
    @Bind(R.id.bt_finish)
    Button bFinish;

    @OnClick(R.id.bt_code)
    public void setCheckCode() {
        newPhone = etNewPhone.getText().toString().trim();
        if(VerifyUtils.isPhone(newPhone)){
            DataAccessUtil.messageCode(newPhone, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        boolean b = DataParseUtil.processDataResult(response);
                        if(b){
                            T.show(response.getString("message"));
                            bCode.setPressed(true);
                            bCode.setClickable(false);
                            excuteDownCount();
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
                    L.d(responseString);
                }
            });
        }


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

    private void excuteDownCount() {
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
    @OnClick(R.id.bt_finish)
    public void setFinish(){
        newPhone = etNewPhone.getText().toString().trim();
        newCode = eNewCode.getText().toString().trim();
        if(VerifyUtils.isPhone(newPhone) && VerifyUtils.isCode(newCode)){
            DataAccessUtil.checkCode(newPhone, newCode, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        boolean b = DataParseUtil.processDataResult(response);
                        L.d("xxx", "boolean值是" + b);
                        if (b) {
                            T.show(response.getString("message"));
                            flag_code = true;
                            modfiyThePhone();
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
                    L.d(responseString);
                }
            });
        }


    }

    public void modfiyThePhone(){
        L.d("xxx", "flag"+flag_code);
        if(VerifyUtils.isPhone(newPhone) && flag_code == true){
            Bundle bundle = getIntent().getExtras();
            String oldCold = bundle.getString(Constants.KEY_VERIFY_CODE);
            L.d("xxx", "oldCold"+oldCold);
            if(VerifyUtils.isCode(oldCold)){
                DataAccessUtil.modifyPhone(newPhone, oldCold,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        try {
                            boolean b = DataParseUtil.modifyPhone(response);
                            if(b){
                                T.show(response.getString("message"));
                                User user = getUser();
                                user.setPhone(newPhone);
                                UserDao userDao = new UserDao(ModifyPhone02Activity.this);
                                userDao.update(user);
                                setResult(RESULT_OK);
                                finish();
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
                        L.d(responseString);
                    }
                });
            }

        }
    }


    //    @OnClick(R.id.bt_finish)
//    public void setfinish(){
//        Bundle bundle=getIntent().getExtras();
//        String code=bundle.getString(Constants.KEY_VERIFY_CODE);
//        newPhone=etNewPhone.getText().toString().trim();
//
//
////        if(inputRightPassword(newPhone,newPhone2)){
////
////            DataAccessUtil.modifyPhone(newPhone, code, new JsonHttpResponseHandler() {
////
////
////                @Override
////                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
////                    super.onSuccess(statusCode, headers, response);
////                    try {
////                        boolean b = DataParseUtil.modifyPhone(response);
////                        if (b) {
////                            T.show(response.getString("message"));
////
////                            ModifyPhone02Activity.this.finish();
////                        }
////                    } catch (ResponseException e) {
////                        e.printStackTrace();
////                        T.show(e.getMessage());
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
////
////                }
////
////                @Override
////                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
////                    super.onFailure(statusCode, headers, responseString, throwable);
////                    T.show(responseString);
////                }
////            });
//        }
//    }
    private boolean inputRightPassword(String password,String confrimPassword){
        if(VerifyUtils.isPhone(password)&&VerifyUtils.isPhone(confrimPassword)){
            if(TextUtils.equals(password,confrimPassword)){
                return true;
            }else{
                T.show("两次输入不一致");
            }
        }
        return false;

    }



}
