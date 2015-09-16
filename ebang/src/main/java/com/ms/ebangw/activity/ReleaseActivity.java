package com.ms.ebangw.activity;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.PostMeassge;
import com.ms.ebangw.utils.T;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 发布信息的页面,此页面没有用，最后删掉
 * @author admin
 *
 */
public class ReleaseActivity extends BaseActivity implements OnClickListener, OnItemSelectedListener, OnFocusChangeListener {
	private LinearLayout lin_back;
	private Spinner spi_qu,spi_lu,spi_style;
	private String str_add_qu="--选区--";
	private String str_add_lu="--选路--";
	private String str_type="--类别--";
	//adapters所需要的数据源
	private String[] datas_qu;
	private String[] datas_lu;
	private String[] datas_type;
	private int flag_count;

	//文本组件
	private EditText ed_add_xiangxi,ed_money,ed_title,ed_content,ed_time,ed_name,ed_phone;
	//文本组件的取值
	private String address_xiangxi_value,type_value,money_value,title_value,content_value,time_value,name_value,phone_value;
	private String address_complete;
	//拼接地址
	private StringBuilder sb;
	private Button but_submit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frag_release);
		initView();
		initViewOper();

	}
	//初始化
	public void initView() {
		// TODO Auto-generated method stub
//		lin_back=(LinearLayout) findViewById(R.id.act_release_Lin_back);
		spi_qu=(Spinner) findViewById(R.id.act_release_spinner_qu);
		spi_lu=(Spinner) findViewById(R.id.act_release_spinner_lu);
		spi_style=(Spinner) findViewById(R.id.act_release_spinner_style);
		ed_add_xiangxi=(EditText) findViewById(R.id.act_release_ed_add);
		ed_money=(EditText) findViewById(R.id.act_release_ed_money);
		ed_name=(EditText) findViewById(R.id.act_release_ed_name);
		ed_title=(EditText) findViewById(R.id.act_release_ed_title);
		ed_content=(EditText) findViewById(R.id.act_release_ed_content);
		ed_time=(EditText) findViewById(R.id.act_release_ed_time);
		ed_phone=(EditText) findViewById(R.id.act_release_ed_phone);
		but_submit=(Button) findViewById(R.id.act_release_but_submit);


	}

	@Override
	public void initData() {

	}

	//取值
	private void getDatas(){
		sb=new StringBuilder();

		address_xiangxi_value=ed_add_xiangxi.getText().toString().trim();//详细文本框的值
		money_value=ed_money.getText().toString().trim();
		title_value=ed_title.getText().toString().trim();
		content_value=ed_content.getText().toString().trim();
		time_value=ed_time.getText().toString().trim();
		name_value=ed_name.getText().toString().trim();
		phone_value=ed_phone.getText().toString().trim();
		//组合的完整的地址
		address_complete=sb.append(str_add_qu).append(str_add_qu).append(address_xiangxi_value).toString();



	}
	//组件处理
	private void initViewOper() {
		// TODO Auto-generated method stub

//		lin_back.setOnClickListener(this);
		spi_qu.setOnItemSelectedListener(this);
		spi_lu.setOnItemSelectedListener(this);
		spi_style.setOnItemSelectedListener(this);
		ArrayAdapter<String> adapter01=new ArrayAdapter<String>(this, R.layout.sim_spinner_item);
		ArrayAdapter<String> adapter02=new ArrayAdapter<String>(this, R.layout.sim_spinner_item);
		ArrayAdapter<String> adapter03=new ArrayAdapter<String>(this, R.layout.sim_spinner_item);

		datas_qu=getResources().getStringArray(R.array.release_add_qu);
		datas_lu=getResources().getStringArray(R.array.release_add_lu);
		datas_type=getResources().getStringArray(R.array.release_add_style);
		for (int i = 0; i < datas_qu.length; i++) {
			adapter01.add(datas_qu[i]);
		}
		spi_qu.setAdapter(adapter01);


		for (int i = 0; i < datas_lu.length; i++) {
			adapter02.add(datas_lu[i]);
		}
		spi_lu.setAdapter(adapter02);

		for (int i = 0; i < datas_type.length; i++) {
			adapter03.add(datas_type[i]);
		}
		spi_style.setAdapter(adapter03);


		but_submit.setOnClickListener(this);
		//设置自动获取系统时间
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		ed_time.setText(sdf.format(new Date())+"点击可选");

		ed_time.setOnFocusChangeListener(this);




	}
	//=====================================================
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			///case R.id.act_release_Lin_back:
			//this.finish();
			//break;
			case R.id.act_release_spinner_qu:

				break;
			case R.id.act_release_spinner_lu:
				this.finish();
				break;
			case R.id.act_release_spinner_style:
				this.finish();
				break;
			case R.id.act_release_but_submit:
				getDatas();
				if(stringSubmit(address_xiangxi_value,money_value,title_value,content_value,name_value,phone_value)){

					//此处为提交成功的没有后台，暂时搁置
					T.show("提交成功");
					PostMeassge pm=new PostMeassge(address_complete, str_type,
						money_value, title_value, content_value, time_value, name_value, phone_value);
					Log.i("aaa", pm.toString());
				}else{
//				if(MyApplication.instance.getFlag_sub()==1){
//
//				}
				}

				break;

			default:
				break;
		}
	}
	//=========================================================

	private boolean stringSubmit(String address,
								 String money, String title, String content,
								 String name, String phone) {
		// TODO Auto-generated method stub
		if(money.equals("")||money==null){
			flag_count++;
		}
		if(title.equals("")||title==null){
			flag_count++;
		}
		if(name.equals("")||name==null){
			flag_count++;
		}
		if(content.equals("")||content==null){
			flag_count++;
		}
		if(phone.equals("")||phone==null){
			flag_count++;
		}
		if(address.equals("")||address==null){
			flag_count++;
		}
		if(flag_count>=2){
			T.show("内容不能为空");
			flag_count=0;
			return false;
		}


		if(money.equals("")||money==null){
			T.show("出价不能为空");
			return false;
		}
		if(title.equals("")||title==null){
			T.show("标题不能为空");
			return false;
		}
		if(name.equals("")||name==null){
			T.show("联系人不能为空");
			return false;
		}
		if(content.equals("")||content==null){
			T.show("内容描述不能为空");
			return false;
		}
		if(phone.equals("")||phone==null){
			T.show("电话不能为空");
			return false;
		}
		if(address.equals("")||address==null){
			T.show("详细地址不能为空");
			return false;
		}
		if(str_add_qu=="--选区--"){
			T.show("未选择区");
			return false;
		}
		if(str_add_lu=="--选路--"){
			T.show("未选择路");
			return false;
		}
		if(str_type=="--类型--"){
			T.show("未选择类型");
			return false;
		}

		return true;
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
							   long id) {
		// TODO Auto-generated method stub
		//确定三分spinner的内容
		switch (parent.getId()) {
			case R.id.act_release_spinner_qu:
				Log.i("xxx", "qude");
				str_add_qu=getResources().getStringArray(R.array.release_add_qu)[position];
				break;
			case R.id.act_release_spinner_lu:
				Log.i("xxx", "lude");
				str_add_lu=getResources().getStringArray(R.array.release_add_lu)[position];
				break;
			case R.id.act_release_spinner_style:
				Log.i("xxx", "typede");
				str_type=getResources().getStringArray(R.array.release_add_style)[position];
				break;

			default:
				break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	//=============================================================
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		if(hasFocus){
			new DatePickerDialog(this,  new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
									  int dayOfMonth) {
					// TODO Auto-generated method stub
					ed_time.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
				}
			}, 2015, 8, 31).show();
		}
	}
	//=============================================================
}
