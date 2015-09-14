package com.ms.ebangw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ms.ebangw.R;

/**
 * 亿帮推荐页面
 * @author admin
 *
 */
public class RecommendActivity extends BaseActivity implements OnClickListener {

	private LinearLayout lin_intro,lin_addr,lin_back,lin_know,lin_serivice,lin_zhoubian,lin_comment;
	private TextView tv_more;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_recommend);
		initView();
		initViewOper();


	}

	private void initViewOper() {
		lin_intro.setOnClickListener(this);
		lin_back.setOnClickListener(this);
		lin_know.setOnClickListener(this);
		lin_serivice.setOnClickListener(this);
		lin_zhoubian.setOnClickListener(this);
		lin_comment.setOnClickListener(this);
		tv_more.setOnClickListener(this);
	}

	public void initView() {
		lin_intro=(LinearLayout) findViewById(R.id.act_recomment_Lin_intro);
		lin_back=(LinearLayout) findViewById(R.id.act_recommend_Lin_back);
		lin_know=(LinearLayout) findViewById(R.id.act_recommend_lin_know);
		lin_serivice=(LinearLayout) findViewById(R.id.act_recommend_lin_serivice);
		lin_zhoubian=(LinearLayout) findViewById(R.id.act_recommend_lin_zhoubian);
		lin_comment=(LinearLayout) findViewById(R.id.act_recommend_lin_comment);
		tv_more=(TextView) findViewById(R.id.act_recommend_tv_more);

	}

	@Override
	public void initData() {

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.act_recomment_Lin_intro:
				startActivity(new Intent(this,IntroActivity.class));
				break;
			case R.id.act_recommend_Lin_back:
				this.finish();
				break;
			case R.id.act_recommend_lin_know:
				startActivity(new Intent(this,NoticeActivity.class));
				break;
			case R.id.act_recommend_lin_serivice:
				startActivity(new Intent(this,ChatActivity.class));
				break;
			case R.id.act_recommend_lin_zhoubian:
				startActivity(new Intent(this,NearActivity.class));
				break;
			case R.id.act_recommend_tv_more:
				startActivity(new Intent(this,CommentActivity.class));
				break;
			case R.id.act_recommend_lin_comment:
				startActivity(new Intent(this,PublishCommentActivity.class));
				break;

			default:
				break;
		}
	}
}
