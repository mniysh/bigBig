package com.ms.ebangw.setting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.activity.LoginActivity;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.db.UserDao;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingAllActivity extends BaseActivity {
    @Bind(R.id.ll_security)
    LinearLayout lSecurity;
    @Bind(R.id.ll_message)
    LinearLayout lMessage;
    @Bind(R.id.ll_update)
    LinearLayout lUpdate;
    @Bind(R.id.ll_feedback)
    LinearLayout lFeedback;
    @Bind(R.id.ll_about_our)
    LinearLayout lAboutOut;
    @Bind(R.id.bt_exit)
    Button bt_exit;
    @Override
    public void initView() {
        initTitle(null, "返回", "设置", null, null);
    }

    @Override
    public void initData() {

    }
    @OnClick(R.id.bt_exit)
    public void exitSystem(){
        logout();
        UserDao userDao = new UserDao(this);
        userDao.removeAll();
        MyApplication.getInstance().quit();
        startActivity(new Intent(this, LoginActivity.class));
        setResult(Constants.REQUEST_EXIT);
        finish();

    }
    public  void logout(){
        DataAccessUtil.exit(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    boolean b = DataParseUtil.exit(response);

                } catch (ResponseException e) {
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

        //此方法为异步方法 环信退出
        EMChatManager.getInstance().logout(new EMCallBack() {

            @Override
            public void onSuccess() {
                L.d("环信退出");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_all);
        ButterKnife.bind(this);
        initView();
        initData();
    }
    @OnClick(R.id.ll_security)
    public void goSecuritySetting(){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);

    }
    @OnClick(R.id.ll_message)
    public void goMessageSetting(){
        Intent intent = new Intent(this, SettingMessageActivity.class);
        startActivity(intent);

    }
    @OnClick(R.id.ll_update)
    public void goUpdateSetting(){
        initUmengUpdata();
    }

    @OnClick(R.id.ll_feedback)
    public void goFeelbackSetting(){
        Intent intent = new Intent(this, FeedbackSettingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ll_about_our)
    public void goAboutOurSetting(){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
    private void initUmengUpdata() {
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
            @Override
            public void onUpdateReturned(int i, UpdateResponse updateResponse) {
                boolean hasUpdata = false;
                if (updateResponse != null) {
                    hasUpdata = updateResponse.hasUpdate;
                }
                if(!hasUpdata){
                    T.show("已经是最新版本");
                }
            }
        });
        UmengUpdateAgent.update(this);
    }
}
