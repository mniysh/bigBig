package com.ms.ebangw.setting;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.activity.HomeActivity;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.db.UserDao;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改昵称页面
 */
public class ModifyNickNameActivity extends BaseActivity {


    @Bind(R.id.tv_oneFinish)
    Button tvOneFinish;
    @Bind(R.id.ed_nickname)
    EditText etNewName;
    @OnClick(R.id.tv_oneFinish)
    public void changeName(){

        final String newNickName = etNewName.getText().toString().trim();
        if(isEmpty(newNickName)){

            T.show("昵称不可为空");

        }else{
            DataAccessUtil.modifyNickName(newNickName, new JsonHttpResponseHandler(){

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        if(response.getString("code").equals("501")){
                            T.show("当前账号已在其他设备上登录,如非本人操作，请修改密码。");
                            logout(ModifyNickNameActivity.this);
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        if(DataParseUtil.modifyName(response)){
                            try {
                                String message=response.getString("message");
                                User user = getUser();
                                user.setNick_name(newNickName);
                                UserDao userDao = new UserDao(ModifyNickNameActivity.this);
                                userDao.update(user);
                                T.show(message);
                                setResult(RESULT_OK);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                T.show(e.getMessage());
                            }
                        }
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
        }

    }



    @Override
    public void initView() {
        initTitle(null,"返回","昵称修改",null,null);
        if(getUser() != null){
            if(getUser().getNick_name() != null){
                etNewName.setHint(getUser().getNick_name());
            }
        }
        User u = getUser();
        if(u != null){
            if(u.getNick_name() != null){
                etNewName.setHint(u.getNick_name());
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        User user = getUser();
        if(user !=null ){
            if(user.getNick_name() != null){
                etNewName.setText(user.getName());
            }
        }
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_nick_name);
        ButterKnife.bind(this);
        initView();
        initData();

    }
    public boolean isEmpty(String str){
        return str == null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
