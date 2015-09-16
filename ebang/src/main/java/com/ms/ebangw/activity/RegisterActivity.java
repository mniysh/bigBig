package com.ms.ebangw.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;

import com.ms.ebangw.utils.OtherLogin;
import com.ms.ebangw.utils.T;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 注册页面————手机验证
 */
public class RegisterActivity extends BaseActivity implements OnClickListener {
	private Button but_check,but_register;
	private boolean flag_check=true;
	private EditText et_phone,et_code;
	private String et_phone_content,et_code_content;
	//mob的appkey和appsecret
	private String APPKEY="9b3017e847ab";
	private String APPSECRET="beaf59b0e3f348a6cf36602c1a40c969";



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_register);


		initView();
		getDatas();
		initViewOper();


	}
	//获取文本框信息
	private void getDatas() {
		// TODO Auto-generated method stub
		et_phone_content=et_phone.getText().toString().trim();
		et_code_content=et_code.getText().toString().trim();
	}
	private void initViewOper() {
		// TODO Auto-generated method stub
		but_check.setOnClickListener(this);
		but_register.setOnClickListener(this);
		initTitle(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RegisterActivity.this.finish();
			}
		},"返回","注册",null,null);
	}

	public void initView() {
		// TODO Auto-generated method stub
		but_check=(Button) this.findViewById(R.id.act_register_check);
		but_register=(Button) this.findViewById(R.id.act_register_register);
		et_phone=(EditText) findViewById(R.id.act_register_et_phone);
		et_code=(EditText) findViewById(R.id.act_register_et_code);
	}

	@Override
	public void initData() {

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {


			//点击注册按钮进行一些判定
			case R.id.act_register_register:
				if(et_code_content!=null&&et_phone_content!=null){
					//toast("能进来");
					//SMSSDK.submitVerificationCode("86",et_phone_content , et_code_content);
					Intent intent=new Intent(RegisterActivity.this,RegisterActivity_2.class);
					getDatas();
					MyApplication.instance.setPhone(et_phone_content);
					intent.putExtra("phone_number", et_phone_content);
					Log.i("xxx", "注册1里的电话号"+et_phone_content);
					startActivity(new Intent(intent));
					et_phone.setText("");
					et_code.setText("");
					RegisterActivity.this.finish();
				}

				break;
			//微博按钮

			default:
				break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}
	//正则判断数字组成
	public boolean isNumeric(String str)
	{
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() )
		{
			return false;
		}
		return true;
	}
}

