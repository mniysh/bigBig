package com.ms.ebangw.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

import com.ms.ebangw.R;
import com.ms.ebangw.utils.T;

/**
 * 个人认证页面
 *
 * @author admin
 */
public class SelfAuthenticationActivity extends BaseActivity implements OnCheckedChangeListener, OnItemSelectedListener, OnClickListener {
	private EditText et_name, et_identityCard, phoneNum;
	private Spinner sp_sheng, sp_shi, sp_qu;
	private RadioGroup rg;
	private String sex_value="男",addr;
	private String sheng,shi,qu,et_name_value,et_identity_value,phoneNum_value,sheng_value="省",shi_value="市",qu_value="区";
	private StringBuilder sb=new StringBuilder();
	private Button but_submit;
	private PopupWindow pw;
	private LinearLayout layout;
	private boolean flag_submit=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_selfauthen);
		initView();
		initViewOper();

	}

	private void initViewOper() {
		// TODO Auto-generated method stub
		rg.setOnCheckedChangeListener(this);
		sp_sheng.setOnItemSelectedListener(this);
		sp_shi.setOnItemSelectedListener(this);
		sp_qu.setOnItemSelectedListener(this);


		but_submit.setOnClickListener(this);


	}
	public void getDatas(){
		addr=sb.append(sheng_value).append(shi_value).append(qu_value).toString();
		et_name_value=et_name.getText().toString().trim();
		et_identity_value=et_identityCard.getText().toString().trim();
		phoneNum_value=phoneNum.getText().toString().trim();



	}

	private void initView() {
		// TODO Auto-generated method stub

		et_name=(EditText) findViewById(R.id.act_self_name);
		et_identityCard=(EditText) findViewById(R.id.act_self_identityCard);
		phoneNum=(EditText) findViewById(R.id.act_self_phoneNum);
		sp_sheng=(Spinner) findViewById(R.id.act_self_sp_sheng);
		sp_shi=(Spinner) findViewById(R.id.act_self_sp_shi);
		sp_qu=(Spinner) findViewById(R.id.act_self_sp_qu);
		rg=(RadioGroup) findViewById(R.id.act_self_rg_sex);
		but_submit=(Button) findViewById(R.id.act_self_but_submit);


	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		if(checkedId==R.id.act_self_rb_man){
			sex_value="男";
		}else{
			sex_value="女";
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
							   long id) {
		// TODO Auto-generated method stub
		switch (parent.getId()) {
			case R.id.act_self_sp_sheng:
				sheng_value=getResources().getStringArray(R.array.sheng)[position];
				break;
			case R.id.act_self_sp_shi:
				shi_value=getResources().getStringArray(R.array.shi)[position];
				break;
			case R.id.act_self_sp_qu:
				qu_value=getResources().getStringArray(R.array.qu)[position];
				break;

			default:
				break;
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.act_self_but_submit:
				Log.i("xxx", flag_submit+"biaozhiwei");

				Log.i("xxx","能进来吗");
				getDatas();
				if(et_name_value==null&&et_name_value.equals("")){
					T.show("姓名不能为空");
					return;
				}
				if(et_identity_value==null&&et_identity_value.equals("")){
					T.show("身份证不能为空");
					return;
				}
				if(phoneNum_value==null&&phoneNum_value.equals("")){
					T.show("手机号不能为空");
					return;
				}
				if(sheng_value=="省"){
					T.show("请选择省份");
					return;
				}
				if(shi_value=="市"){
					T.show("请选择城市");
					return;
				}
				if(qu_value=="区"){
					T.show("请选择城市");
					return;
				}
				Log.i("aaa", "长度是"+et_identity_value.length());
				if(et_identity_value.length()==18||et_identity_value.length()==16){

				}else{
					T.show("身份证号位数不正确");
				}
				//验证合格后把所有的数据打包到bean里面


				layout=(LinearLayout) getLayoutInflater().inflate(R.layout.popup_lay, null, false);
				pw=new PopupWindow(layout,600, LayoutParams.WRAP_CONTENT);
				pw.setBackgroundDrawable(new BitmapDrawable());
				Log.i("aaa", "layout的值是"+layout.toString());
				layout.findViewById(R.id.popup_lay_iv_back).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						backgroundAlpha(1.0f);
						pw.dismiss();
						flag_submit=!flag_submit;
					}
				});
				backgroundAlpha(0.5f);
				pw.showAtLocation(but_submit, Gravity.TOP, 0, 150);




				break;

			default:
				break;
		}

	}
	public void backgroundAlpha(float bgAlpha)
	{
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = bgAlpha; //0.0-1.0
		getWindow().setAttributes(lp);
	}
}
