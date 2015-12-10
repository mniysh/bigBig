package com.ms.ebangw.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.L;
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
	private CountDownTimer countDownTimer;
	private Handler mHandler;

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

		changeColor();
		setStarRed();
	}
	/**
	 * 变颜色
	 */
	public void setStarRed() {


		TextView a = (TextView) findViewById(R.id.act_login_register);
		String s = a.getText().toString();
		SpannableString spannableString = new SpannableString(s);
		spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#036287")), 32, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		a.setText(spannableString);
		a.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(RegisterActivity.this, ServiceContractActivity.class));
			}
		});

	}
	/**
	 * 变色
	 */
	public void changeColor(){
		TextView textView = (TextView) findViewById(R.id.act_login_register);
		String s = textView.getText().toString().trim();
		SpannableString spannableString = new SpannableString(s);
		spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#347A93")), 36, s.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

	}

	@Override
	public void initData() {
		mHandler = new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				int what = msg.what;
				if (what == 0) {
					smsCodeBtn.setPressed(false);
					smsCodeBtn.setClickable(true);
					smsCodeBtn.setText("获取验证码");
				}else {
					smsCodeBtn.setText(what + " 秒");
				}
				return false;
			}
		});
	}

	/**点击：注册*/
	@OnClick(R.id.btn_register)
	public void goRegister2(View view) {
		phone = phonetEt.getText().toString().trim();
		verifyCode = verifyCodeEt.getText().toString().trim();
		DataAccessUtil.checkCode(phone, verifyCode, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				try {
					boolean b = DataParseUtil.processDataResult(response);
					if (b) {
						Bundle bundle = new Bundle();
						bundle.putString(Constants.key_phone, phone);
						bundle.putString(Constants.KEY_VERIFY_CODE, verifyCode);
						Intent intent = new Intent(RegisterActivity.this, RegisterActivity_2.class);
						intent.putExtras(bundle);
						startActivity(intent);
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

	/**输入是否正确*/
	public boolean isInputRight(String phone, String verifyCode) {

		if (!VerifyUtils.isPhone(phone)) {
			T.show("请输入正确的手机号");
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
		if (VerifyUtils.isPhone(phone)) {

			DataAccessUtil.messageCodeRegiste(phone, new JsonHttpResponseHandler(){
				@Override
				public void onStart() {
					executeCountDown();
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					try {
						boolean b = DataParseUtil.messageCode(response);
						L.d("xxx",b+"b的值");
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

	private void executeCountDown() {
		smsCodeBtn.setPressed(true);
		smsCodeBtn.setClickable(false);
		countDownTimer = new CountDownTimer(60000, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				mHandler.sendEmptyMessage((int)(millisUntilFinished / 1000));
			}

			@Override
			public void onFinish() {
				mHandler.sendEmptyMessage(0);
			}
		};
		countDownTimer.start();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(countDownTimer != null){
			countDownTimer.cancel();
		}
	}
}

