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
import com.ms.ebangw.service.MyService;
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
	private BroadcastReceiver receiver=new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			but_check.setText("已发送"+intent.getIntExtra("data", 0));
			if(intent.getIntExtra("data", 0)==0){
				but_check.setBackgroundColor(getResources().getColor(R.color.back));
				but_check.setText("重新获取");
				flag_check=!flag_check;
				stopService(new Intent(RegisterActivity.this,MyService.class));
			}
		}
	};

	Handler handler=new Handler(){
		public void handleMessage(Message msg) {
//			int event=msg.arg1;
//			int result=msg.arg2;
//			Object data=msg.obj;
//			Log.i("xxx", "handler里面的obj数据"+data.toString());
//			//
//			if (result == 0) {
//				//回调完成
//				if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
//					Toast.makeText(RegisterActivity.this, "验证成功", Toast.LENGTH_LONG).show();
//					Intent intent=new Intent(RegisterActivity.this,RegisterActivity_2.class);
//					getDatas();
//					intent.putExtra("phone_number", et_phone_content);
//					Log.i("xxx", "注册1里的电话号"+et_phone_content);
//					startActivity(new Intent(intent));
//					et_phone.setText("");
//					et_code.setText("");
//					RegisterActivity.this.finish();
//					//提交验证码成功
//				}else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
//					Toast.makeText(RegisterActivity.this, "获取验证码成功", Toast.LENGTH_LONG).show();
//					//获取验证码成功
//				}else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
//					//返回支持发送验证码的国家列表
//				}
//			}else{
//
//				Toast.makeText(RegisterActivity.this, "失败", Toast.LENGTH_LONG).show();
//				((Throwable)data).printStackTrace();
//				System.out.println("---------------------"+((Throwable) data).getMessage());
//
//			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_register);
		//SMSSDK.initSDK(RegisterActivity.this, APPKEY, APPSECRET);
//		EventHandler eh=new EventHandler(){
//			@Override
//			public void afterEvent(int event, int result, Object data) {
//				Message msg=new Message();
//				msg.arg1=event;
//				msg.arg2=result;
//				msg.obj=data;
//				handler.sendMessage(msg);
//			}
//		};
		//SMSSDK.registerEventHandler(eh); //注册短信回调

		initView();
		getDatas();
		initViewOper();
		IntentFilter filter=new IntentFilter("RegisterActivity");
		registerReceiver(receiver, filter);
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
			case R.id.act_register_check:
				getDatas();
				//对手机号进行判断
				if(et_phone_content==null){
					T.show("手机号不可为空，请输入");
					return;
				}else{
					if(et_phone_content.length()>11||et_phone_content.length()<11){
						T.show("手机号位数不正确");
						et_phone.setText("");
						return;
					}
					//正则判断手机号
					if(!isNumeric(et_phone_content)){
						T.show("请输入正确的数字手机号");
						et_phone.setText("");
						return;
					}
				}
				//起始为可点击状态，点击后变换标志位为不可点击，等广播技术变换标志位为可点击
				if(flag_check){
					but_check.setBackgroundColor(Color.GRAY);
					startService(new Intent(this,MyService.class));
					flag_check=!flag_check;
					if(et_phone_content!=null){
						//获取验证码
						//SMSSDK.getVerificationCode("86", et_phone_content);

					}
				}
				break;
			//back键，finish（）掉当前的activity

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
		unregisterReceiver(receiver);
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

