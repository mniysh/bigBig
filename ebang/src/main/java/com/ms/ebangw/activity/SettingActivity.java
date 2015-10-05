package com.ms.ebangw.activity;

import android.content.Intent;
import android.os.Bundle;
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

import org.apache.http.Header;
import org.json.JSONObject;

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
    @Bind(R.id.tv_phone)
    TextView tPhone;

    @Bind(R.id.tv_phoneModify)
    TextView tvPhoneModify;
    @Bind(R.id.tv_passModify)
    TextView tvPassModify;

    @OnClick(R.id.tv_nameModify)
    public void changeNickName(){

        Intent intent = new Intent(this, ModifyNickNameActivity.class);

        startActivityForResult(intent, 111);

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == RESULT_OK) {
            User user = MyApplication.getInstance().getUser();
            String nick_name = user.getNick_name();
            tvNickName.setText(nick_name);
        }

    }

    @Override
    public void initView() {
        initTitle(null, "返回", "设置", null, null);
        tvNickName.setText(getUser().getNick_name());
        tPhone.setText(getUser().getPhone());
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

//    @Override
//    public void onResume() {
//        super.onResume();
//        User user = getUser();
//        L.d("xxx",user.toString());
//        if(null!= user){
//            String newName=user.getNick_name();
//            String phone=user.getPhone();
//            tvNickName.setText(newName);
//            tPhone.setText(phone);
//        }else{
//            T.show("用户不存在");
//        }
//
//    }




    @OnClick(R.id.btn_exit)
    public void exit() {
        UserDao userDao = new UserDao(this);
        userDao.removeAll();

        MyApplication.getInstance().quit();
        startActivity(new Intent(this, LoginActivity.class));
        setResult(Constants.REQUEST_EXIT);
        logout();
        finish();
    }

    private void logout() {
        DataAccessUtil.exit(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    boolean b = DataParseUtil.processDataResult(response);

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
