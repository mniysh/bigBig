package com.ms.ebangw.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.ms.ebangw.R;

/**
 * 抢单须知的页面
 * @author admin
 *
 */
public class NoticeActivity extends BaseActivity implements OnClickListener {
	private LinearLayout lin_back;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_notice);
		initView();
		initData();
	}


	@Override
	public void initView() {
		lin_back=(LinearLayout) findViewById(R.id.act_notice_Lin_back);
		lin_back.setOnClickListener(this);
	}

	@Override
	public void initData() {

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.act_notice_Lin_back:
			this.finish();
			break;
		default:
			break;
		}
	}
}
