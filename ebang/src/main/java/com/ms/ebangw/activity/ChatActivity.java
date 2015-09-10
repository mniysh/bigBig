package com.ms.ebangw.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ms.ebangw.R;

/**
 * 企业聊天页面
 *
 *
 */
public class ChatActivity extends BaseActivity {
	private ImageView iv_send;
	private LinearLayout lin_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_chat);
		initView();

	}
	private void initView() {
		// TODO Auto-generated method stub
		iv_send=(ImageView) findViewById(R.id.act_chat_send);

	}
}

