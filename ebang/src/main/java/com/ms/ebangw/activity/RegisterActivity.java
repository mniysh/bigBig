package com.ms.ebangw.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.T;

import org.apache.http.Header;
import org.json.JSONObject;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_register);


		initView();
		getDatas();
		initViewOper();
		IntentFilter filter=new IntentFilter("RegisterActivity");

	}
	//获取文本框信息
	private void getDatas() {
		et_phone_content=et_phone.getText().toString().trim();
		et_code_content=et_code.getText().toString().trim();
	}
	private void initViewOper() {
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
		switch (v.getId()) {
			//点击注册按钮进行一些判定
			case R.id.act_register_register:
				if(et_code_content!=null&&et_phone_content!=null){

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
			case R.id.act_register_check:
				getDatas();
				if(et_phone_content==null||et_phone_content.equals("")){
					T.show("手机号不能为空");
				}else{
					getMsmCode();
				}

				break;
			//微博按钮

			default:
				break;
		}
	}

	/**
	 * 获取短信验证码
	 */
	public void getMsmCode() {
		DataAccessUtil.messageCode(et_phone_content, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				T.show("成功，状态吗是"+statusCode);

				try {
					User user = DataParseUtil.register(response);
				} catch (ResponseException e) {
					e.printStackTrace();
					T.show(e.getMessage());
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				T.show("失败，状态吗是"+statusCode);
			}
		});
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

