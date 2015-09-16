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

	}

	@Override
	public void initData() {
		initTitle(new OnClickListener() {
			@Override
			public void onClick(View v) {
				NoticeActivity.this.finish();
			}
		},"返回","抢单须知",null,null);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		default:
			break;
		}
	}
}
