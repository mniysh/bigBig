package com.ms.ebangw.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ms.ebangw.R;

/**
 * 企业简介页面
 */

public class IntroActivity extends BaseActivity implements OnClickListener {
	private LinearLayout lin_back;
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
		lin_back=(LinearLayout) findViewById(R.id.act_intro_Lin_back);
		lin_back.setOnClickListener(this);
		iv_share.setOnClickListener(this);

		iv_share=(ImageView) findViewById(R.id.act_intro_share);
	}

	@Override
	public void initData() {

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.act_intro_Lin_back:
				this.finish();
				break;
			case R.id.act_intro_share:



				//放分享的代码，暂时空
				break;

			default:
				break;
		}
	}
}
