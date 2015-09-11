package com.ms.ebangw.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.ms.ebangw.R;
import com.ms.ebangw.adapter.NearAdapter;
import com.ms.ebangw.view.XListView;

/**
 * 查看周边的其他工作
 *
 *
 */
public class NearActivity extends BaseActivity implements XListView.IXListViewListener {

	private XListView xlistview;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_near);

		init();
		opera();
	}

	private void init() {
		xlistview = (XListView) findViewById(R.id.xlistview);

	}

	private void opera() {
		xlistview.setAdapter(new NearAdapter(this));//适配
		xlistview.setXListViewListener(this);//滑动监听
		xlistview.setPullLoadEnable(true);// 上拉加载
		handler = new Handler();
	}

	private void onload() {
		xlistview.stopRefresh();
		xlistview.stopLoadMore();
		xlistview.setRefreshTime("刚刚");
	}

	@Override
	public void onRefresh() {
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

				// xlistview.notify();
				onload();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

				// xlistview.notifyAll();
				onload();
			}
		}, 2000);
	}

}
