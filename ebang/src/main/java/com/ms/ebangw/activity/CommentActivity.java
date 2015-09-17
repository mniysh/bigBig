package com.ms.ebangw.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.ms.ebangw.R;
import com.ms.ebangw.adapter.CommentActivityAdapter;
import com.ms.ebangw.bean.Comment;
import com.ms.ebangw.view.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 点评页面
 */
public class CommentActivity extends BaseActivity implements View.OnClickListener {
	private CommentActivityAdapter adapter;
	private List<Comment> datas;
	private XListView xlistview;
	private ImageView iv_share,iv_collect;
	private LinearLayout lin_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_comment);
		initView();
		initDatas();
		initViewOper();
	}
	public void initView() {

		xlistview=(XListView) findViewById(R.id.act_comment_xlistview);
		iv_collect=(ImageView) findViewById(R.id.act_comment_collect);
		iv_share=(ImageView) findViewById(R.id.act_comment_share);
		//lin_back=(LinearLayout) findViewById(R.id.act_comment_Lin_back);

	}

	@Override
	public void initData() {
		initTitle(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CommentActivity.this.finish();
			}
		},"返回","点评",null,null);


	}

	private void initViewOper() {
		adapter=new CommentActivityAdapter(datas, this);
		xlistview.setAdapter(adapter);
		setListView(xlistview);
		lin_back.setOnClickListener(this);
		iv_share.setOnClickListener(this);
	}
	//重写listview的高度
	private void setListView(XListView xlistview2) {
		// TODO Auto-generated method stub
		ListAdapter listadapter=xlistview.getAdapter();
		if(listadapter==null){
			return;

		}

		int aHeight=0;
		for (int i = 0; i < listadapter.getCount(); i++) {
			View listItem = listadapter.getView(i, null, xlistview);
			listItem.measure(0, 0);
			aHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params=xlistview.getLayoutParams();
		params.height=aHeight+xlistview.getDividerHeight()*(listadapter.getCount()-1);
		xlistview.setLayoutParams(params);
	}
	private void initDatas() {
		// TODO Auto-generated method stub
		datas=new ArrayList<Comment>();
		for (int i = 0; i < 5; i++) {
			datas.add(new Comment(i+"用户名", null, "测试用户评论信息", "点赞","(0)" ));
		}


	}
	//+++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++


	//====================================================
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
//			case R.id.act_comment_Lin_back:
//				finish();
//				break;
			case R.id.act_comment_share:
				showShare();



				break;

			default:
				break;
		}
	}
	//share的方法
	private void showShare() {

	}
	//===========================================================
}

