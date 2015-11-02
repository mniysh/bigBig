package com.ms.ebangw.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.ms.ebangw.R;

/**
 * 企业简介页面
 */

public class CompanyIntroduceActivity extends BaseActivity implements OnClickListener {

	private ImageView iv_share;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_intro);
		initView();
		initData();

	}
	@Override
	public void initView() {
		//lin_back=(LinearLayout) findViewById(R.id.act_intro_Lin_back);
		iv_share=(ImageView) findViewById(R.id.act_intro_share);
		//lin_back.setOnClickListener(this);
		iv_share.setOnClickListener(this);


	}

	@Override
	public void initData() {
		initTitle(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CompanyIntroduceActivity.this.finish();
			}
		},"返回","企业简介",null,null);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
//			case R.id.act_intro_Lin_back:
//				this.finish();
//				break;
			case R.id.act_intro_share:



				//放分享的代码，暂时空
				break;

			default:
				break;
		}
	}
}
