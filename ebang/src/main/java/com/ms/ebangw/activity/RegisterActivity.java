package com.ms.ebangw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.utils.VerifyUtils;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册页面————手机验证
 */
public class RegisterActivity extends BaseActivity  {
	private String phone, verifyCode;
	@Bind(R.id.btn_smsCode)
	Button smsCodeBtn;
	/**
	 * 手机号
	 */
	@Bind(R.id.et_phone)
	EditText phonetEt;
	/**
	 * 验证码
	 */
	@Bind(R.id.et_verifyCode)
	EditText verifyCodeEt;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_register);
		ButterKnife.bind(this);
		initView();
		initData();
	}

	public void initView() {
		initTitle(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RegisterActivity.this.finish();
			}
		}, "返回", "注册", null, null);

	}

	@Override
	public void initData() {

	}

	/**点击：注册*/
	@OnClick(R.id.btn_register)
	public void goRegister2(View view) {
		Bundle bundle = new Bundle();
		bundle.putString(Constants.key_phone, phone);
		bundle.putString(Constants.KEY_VERIFY_CODE, verifyCode);

		Intent intent=new Intent(RegisterActivity.this,RegisterActivity_2.class);
		intent.putExtras(bundle);
		startActivity(intent);

	}

	/**输入是否正确*/
	public boolean isInputRight(String phone, String verifyCode) {

		if (!VerifyUtils.isPhone(phone)) {
			T.show("手机号不能为空");
			return false;
		}

		if (TextUtils.isEmpty(verifyCode)) {
			T.show("验证码不能为空");
			return false;
		}

		return true;
	}



	/**
	 * 获取短信验证码
	 */
	@OnClick(R.id.btn_smsCode)
	public void getMsmCode() {
		phone = phonetEt.getText().toString().trim();
		verifyCode = verifyCodeEt.getText().toString().trim();
		if (VerifyUtils.isPhone(phone)) {

			DataAccessUtil.messageCode(phone, new JsonHttpResponseHandler(){

				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					try {
						boolean b = DataParseUtil.messageCode(response);
						if (b) {
							T.show("验证码已发送，请注意查收");
						}

					} catch (ResponseException e) {
						e.printStackTrace();
						T.show(e.getMessage());
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
					super.onFailure(statusCode, headers, throwable, errorResponse);
				}
			});
		}else {
			T.show("请输入正确的手机号");

		}
	}

}

