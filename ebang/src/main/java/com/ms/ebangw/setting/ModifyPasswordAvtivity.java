package com.ms.ebangw.setting;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.activity.HomeActivity;
import com.ms.ebangw.bean.User;
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

public class ModifyPasswordAvtivity extends BaseActivity {
    private String sOldpass,sNewPsaa,sAgainPass;
    @Override
    public void initView() {
        initTitle(null,"返回","密码修改",null,null);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password_avtivity);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Bind(R.id.et_oldPass)
    EditText eOldPass;
    @Bind(R.id.et_newPass)
    EditText eNewpass;
    @Bind(R.id.et_againPass)
    EditText eNewPassAgain;
    @Bind(R.id.bt_threeFinish)
    Button bFinish;
    @OnClick(R.id.bt_threeFinish)
    public void complete(){
        sOldpass=eOldPass.getText().toString().trim();
        sNewPsaa=eNewpass.getText().toString().trim();
        sAgainPass=eNewPassAgain.getText().toString().trim();
        L.d(sOldpass+sNewPsaa+sAgainPass);
        if(isRightPass(sOldpass,sNewPsaa,sAgainPass)){
            User user=getUser();
            if(user!=null){
                DataAccessUtil.changePwd(user.getPhone(),sOldpass,sNewPsaa,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            if(response.getString("code").equals("501")){
                                T.show("当前账号已在其他设备上登录,如非本人操作，请修改密码。");
                                logout(ModifyPasswordAvtivity.this);
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        super.onSuccess(statusCode, headers, response);
                        L.d("xxx","nengbuneng");
                        try {
                            boolean b= DataParseUtil.modifyPassword(response);
                            if(b){
                                T.show(response.getString("message"));
                                User user=getUser();
                                user.setPassword(sNewPsaa);

                                MyApplication.getInstance().saveUser(user);
                                ModifyPasswordAvtivity.this.finish();
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
                    }
                });
            }

        }

    }
    public boolean isRightPass(String oldP,String newP,String confrimP){
        if(VerifyUtils.isPasswordRight(oldP)&&VerifyUtils.isPasswordRight(newP)&&VerifyUtils.isPasswordRight(confrimP)){
            if(getUser()!=null){
                //L.locationpois_item("xxx","getuserdezhi"+getUser().toString());
                if(TextUtils.equals(newP,confrimP)){
                    return true;
                }else{
                    T.show("两次密码输入不一致，请重新输入");
                    eNewPassAgain.setText("");
                    eNewpass.setText("");
                }
            }

        }

        return  false;
    }


}
