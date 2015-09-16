package com.ms.ebangw.activity;

import android.os.Bundle;
import android.view.View;

import com.ms.ebangw.R;

public class SubmitSuccessActivity extends BaseActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_sub);
		initView();
		initData();
	}

	@Override
	public void initView() {

	}

	@Override
	public void initData() {
		initTitle(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SubmitSuccessActivity.this.finish();
			}
		},"返回","提交信息",null,null);
	}
}
