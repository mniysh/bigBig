package com.ms.ebangw.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import com.ms.ebangw.R;

/**
 * 企业聊天页面
 *
 *
 */
public class ChatActivity extends BaseActivity {
	private ImageView iv_send,iBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_chat);
		initView();
		initData();

	}
	public void initView() {
		// TODO Auto-generated method stub
		iv_send=(ImageView) findViewById(R.id.act_chat_send);
		iBack= (ImageView) findViewById(R.id.iv_back);
	}

	@Override
	public void initData() {
		initTitle(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ChatActivity.this.finish();
			}
		},"返回","企业客服",null,null);


	}
}

