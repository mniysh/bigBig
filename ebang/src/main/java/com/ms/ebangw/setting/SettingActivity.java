package com.ms.ebangw.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.StringUtils;
import com.ms.ebangw.utils.T;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 设置页面
 */
public class SettingActivity extends BaseActivity {
    private static final int REQUESTCODENAME = 111;
    private static final int REQUESTCODEPHONE = 222;


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

    }

    @Override
    public void initView() {
        initTitle(null, "返回", "设置", null, null);
        tvNickName.setText(getUser().getNick_name());
        tPhone.setText(getUser().getPhone());
        String realName = getUser().getReal_name();
        if(realName != null){
            tvRealNameModify.setText(StringUtils.setRealName(realName));
        }else{
            tvRealNameModify.setText("还没有实名认证");
        }
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
//        logout();
//        UserDao userDao = new UserDao(this);
//        userDao.removeAll();
//
//        MyApplication.getInstance().quit();
//        startActivity(new Intent(this, LoginActivity.class));
//        setResult(Constants.REQUEST_EXIT);
//        finish();
    }

//    private void logout() {
//        DataAccessUtil.exit(new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                try {
//                    boolean b = DataParseUtil.processDataResult(response);
//
//                } catch (ResponseException e) {
//                    e.printStackTrace();
//                    T.show(e.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                super.onFailure(statusCode, headers, responseString, throwable);
//            }
//        });
//    }


}
