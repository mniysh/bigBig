package com.ms.ebangw.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.User;
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

public class ModifyPhone02Activity extends BaseActivity {
    private String newPhone,newPhone2;
    @Override
    public void initView() {

    }

    @Override
    public void initData() {

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
    EditText etPhone;
    @Bind(R.id.et_newPhoneAgain)
    EditText etPhone02;

    @Bind(R.id.bt_finish)
    Button bFinish;
    @OnClick(R.id.bt_finish)
    public void setfinish(){
        Bundle bundle=getIntent().getExtras();
        String code=bundle.getString(Constants.KEY_VERIFY_CODE);
        L.d("code的值是"+code);
        newPhone=etPhone.getText().toString().trim();
        newPhone2=etPhone.getText().toString().trim();
        if(inputRightPassword(newPhone,newPhone2)){

            DataAccessUtil.modifyPhone(newPhone, code, new JsonHttpResponseHandler() {


                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        User user = MyApplication.getInstance().getUser();
                        boolean b = DataParseUtil.modifyPhone(response);
                        if (b) {
                            T.show(response.getString("message"));
                            user.setPhone(newPhone);
                            MyApplication.getInstance().saveUser(user);
                            ModifyPhone02Activity.this.finish();
                        }
                    } catch (ResponseException e) {
                        e.printStackTrace();
                        T.show(e.getMessage());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    T.show(responseString);
                }
            });
        }
    }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        makeKeyboard();
    }
}
