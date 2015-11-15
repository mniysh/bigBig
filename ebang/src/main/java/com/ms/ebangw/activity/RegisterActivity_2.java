package com.ms.ebangw.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.ms.ebangw.utils.AppUtils;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.utils.VerifyUtils;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * 注册页面----密码确认
 * @author admin
 *
 */
public class RegisterActivity_2 extends BaseActivity{
	private String phone, verifyCode;

	@Bind(R.id.et_password)
	EditText passwordEt;
	@Bind(R.id.et_password_confirm)
	EditText confirmPasswordEt;
	@Bind(R.id.btn_register)
	Button registerBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_register_2);
		ButterKnife.bind(this);
		initView();
		initData();


	}

	public void initView() {
		initTitle("设置密码");

	}

	@Override
	public void initData() {
		Bundle extras = getIntent().getExtras();
		phone = extras.getString(Constants.key_phone);
		verifyCode = extras.getString(Constants.KEY_VERIFY_CODE);

	}



	@OnClick(R.id.btn_register)
	public void registerAccount(View view) {
		String password = passwordEt.getText().toString().trim();
		String confirmPassword = confirmPasswordEt.getText().toString().trim();
		String come_from = "";
		try {
			come_from = AppUtils.getAppChannel(this);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		if (isInputRight(password, confirmPassword)) {
			DataAccessUtil.register(null, phone, null, null, verifyCode, password, come_from, new
				JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					try {
						User user = DataParseUtil.register(response);
//						L.locationpois_item("xxx", "user的内容注册部分的" + user.toString());
						if (null != user) {
							MyApplication.getInstance().saveUser(user);
							Intent intent = new Intent(RegisterActivity_2.this, HomeActivity.class);
							startActivity(intent);

							//跳转到主页

							finish();
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

	/**
	 * 密码输入验证
	 * @param password
	 * @param confirmPassword
	 * @return
	 */
	private boolean isInputRight(String password, String confirmPassword) {
		if (VerifyUtils.isPasswordRight(password) && VerifyUtils.isPasswordRight(confirmPassword)) {
			if (TextUtils.equals(password, confirmPassword)) {
				return true;
			}else {
				T.show("两次密码输入不一致");
			}
		}
		return false;
	}

	//正则判断数字组成
	public boolean isNumeric(String str)
	{
		Pattern pattern = Pattern.compile("[0-9][a-zA-Z]");
		Matcher isNum = pattern.matcher(str);
		if(!isNum.matches() )
		{
			return false;
		}
		return true;
	}
}
