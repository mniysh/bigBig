package com.ms.ebangw.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.utils.T;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 注册页面----密码确认
 * @author admin
 *
 */
public class RegisterActivity_2 extends BaseActivity implements OnClickListener {
//	private ImageView iv_back,iv_weixin,iv_weibo,iv_qq;
	private EditText et_password01,et_password02;
	private String et_passwordValue01,et_passwordValue02;
	private Button but_register;
	private SharedPreferences sp;
	private boolean flag_log=false;
	private String url="http://labour.chinadeer.cn/api/user/index/register";
	private TextWatcher tWatccher=new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			but_register.setBackgroundColor(R.color.but_gongzhang);
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_register_2);
		initView();
		initViewOper();

		initTitle("注册");

	}

	public void initView() {
		// TODO Auto-generated method stub

		et_password01=(EditText) findViewById(R.id.act_register_2_et_password);
		et_password02=(EditText) findViewById(R.id.act_register_2_et_password2);
		but_register=(Button) findViewById(R.id.act_register_2_but_register);

	}

	@Override
	public void initData() {

	}

	private void initViewOper() {
		et_password01.addTextChangedListener(tWatccher);
		et_password02.addTextChangedListener(tWatccher);
		but_register.setOnClickListener(this);
		initTitle(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RegisterActivity_2.this.finish();
			}
		},"返回","设置密码",null,null);

	}
	public void getDatas(){
		et_passwordValue01=et_password01.getText().toString().trim();
		et_passwordValue02=et_password02.getText().toString().trim();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
//			case R.id.act_register2_iv_back:
//				finish();
//				break;
//			case R.id.act_register2_iv_qq:
//				OtherLogin.login_qq(RegisterActivity_2.this);
//				break;
//			case R.id.act_register2_iv_weibo:
////				OtherLogin.login_weibo(RegisterActivity_2.this);
//				break;
//			case R.id.act_register2_iv_weixin:
//				OtherLogin.login_weixin(RegisterActivity_2.this);
//				break;
			case R.id.act_register_2_but_register:
				getDatas();
				if(et_passwordValue01==null||et_passwordValue02==null){
					T.show("密码不能为空");
					return;
				}
				if(isNumeric(et_passwordValue01)||isNumeric(et_passwordValue02)){
					T.show("密码只支持字母或数字");
					et_password01.setText("");
					et_password02.setText("");
					return;
				}
				if(et_passwordValue01.length()<6||et_passwordValue01.length()>20){
					T.show("密码支持的长度为6到20");
					et_password01.setText("");
					et_password02.setText("");
					return;
				}
				if(et_passwordValue01.equals(et_passwordValue02)){

					Log.i("aaa", "电话号码"+getIntent().getStringExtra("phone_number"));
					Log.i("aaa", "密码"+et_passwordValue01);
					flag_log=true;
					T.show("注册成功");
					sp=getSharedPreferences("shuju", Context.MODE_PRIVATE);
					Editor editor=sp.edit();
					editor.putString("phoneNumber", getIntent().getStringExtra("phone_number"));
					editor.putString("password", et_passwordValue01);
					editor.putInt("flag_int", 1);
					editor.commit();
					MyApplication.instance.setPassword(et_passwordValue01);
					this.finish();
					//下面注释的方法是关联注册接口用的，现在是测试，服务器没开，先关掉
					//fangfa(getIntent().getStringExtra("phone_number"),et_passwordValue01);
					User user=new User();
					user.setPhone(getIntent().getStringExtra("phone_number"));
					user.setPassword(et_passwordValue01);

				}else{
					T.show("两次输入的密码不一致，请重新输入");
					et_password01.setText("");
					et_password02.setText("");
					return;

				}

				break;

			default:
				break;
		}
	}
	private void fangfa(String phone, String password) {
		// TODO Auto-generated method stub
		AsyncHttpClient hc=new AsyncHttpClient();
		RequestParams params=new RequestParams();
		params.put("phone", phone);
		params.put("password", password);

		hc.post(url, params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONObject response) {
				int result=0;
				try {
					result=response.getInt("code");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if(result==200){
					T.show("注册成功");
					//注册成功，就缓存一下
					sp=getSharedPreferences("shuju", Context.MODE_PRIVATE);
					Editor editor=sp.edit();
					editor.putString("phoneNumber", getIntent().getStringExtra("phone_number"));
					editor.putString("password", et_passwordValue01);
					editor.putInt("flag_int", 1);
					editor.commit();
//					startActivity(new Intent(RegisterActivity_2.this,HomeActivity.class));
					finish();

				}else{
					T.show("注册失败");
				}

			}


			@Override
			public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				Log.i("ccc", "错误的信息是" + errorResponse.toString());

			}
		});
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
