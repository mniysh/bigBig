package com.ms.ebangw.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.User;
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

        final String newNickName=etNewName.getText().toString().trim();
        if(isEmpty(newNickName)){

            T.show("昵称不可为空");
        }else{
            DataAccessUtil.modifyNickName(newNickName, new JsonHttpResponseHandler(){

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        if(DataParseUtil.modifyName(response)){
                            try {
                                String message=response.getString("message");
                                User user = getUser();
                                user.setNick_name(newNickName);
                                MyApplication.getInstance().saveUser(user);
                                T.show(message);
                                ModifyNickNameActivity.this.finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (ResponseException e) {
                        e.printStackTrace();
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
        initTitle(null,"放回","昵称修改",null,null);
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
        return str==null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        makeKeyboard();
    }
}
