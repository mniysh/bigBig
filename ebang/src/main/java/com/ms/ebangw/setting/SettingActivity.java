package com.ms.ebangw.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.activity.LoginActivity;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.db.UserDao;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.scancode.MipcaActivityCapture;
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
    private final int REQUESTCODENAME = 111;
    private final int REQUESTCODEPHONE = 222;
    private final static int SCANNIN_GREQUEST_CODE = 1;

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
    @Bind(R.id.tv_realNameModify)
    TextView tvRealNameModify;
    /**
     * 扫码推荐工长
     */
    @Bind(R.id.ll_recommend_handman)
    LinearLayout recommendHandmanLayout;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == RESULT_OK) {
            User user = MyApplication.getInstance().getUser();
            String nick_name = user.getNick_name();
            tvNickName.setText(nick_name);
        }
        if(requestCode == 222 && resultCode == RESULT_OK){
            User user = MyApplication.getInstance().getUser();
            String phone = user.getPhone();
            tPhone.setText(phone);
        }

        if (requestCode == SCANNIN_GREQUEST_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            //显示扫描到的内容
            String result = bundle.getString("result");
            L.d("二维码扫描结果: " + result);
            workerRecommendHeadman(result);
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
        tvNameModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, ModifyNickNameActivity.class);
                startActivityForResult(intent, REQUESTCODENAME);
            }
        });

        tvPhoneModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, ModifyPhoneActivity.class);
                startActivityForResult(intent, REQUESTCODEPHONE);
            }
        });

        tvPassModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, ModifyPasswordAvtivity.class));
            }
        });

        recommendHandmanLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SettingActivity.this, MipcaActivityCapture.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);

            }
        });
        tvRealNameModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                

            }
        });

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
        L.d("xxx", user.toString());
        if(null!= user){
            String newName=user.getNick_name();
            String phone=user.getPhone();
            tvNickName.setText(newName);
            tPhone.setText(phone);
        }else{
            T.show("用户不存在");
        }

    }

    @OnClick(R.id.btn_exit)
    public void exit() {
        logout();
        UserDao userDao = new UserDao(this);
        userDao.removeAll();

        MyApplication.getInstance().quit();
        startActivity(new Intent(this, LoginActivity.class));
        setResult(Constants.REQUEST_EXIT);
        finish();
    }

    private void logout() {
        DataAccessUtil.exit(new JsonHttpResponseHandler() {
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

    /**
     * 工人扫码推荐工长
     * @param headmanId
     */
    private void workerRecommendHeadman(String headmanId) {
        DataAccessUtil.workerRecommendHeadman(headmanId, new JsonHttpResponseHandler(){

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
