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
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_comment);
		initView();
		initDatas();
		initViewOper();
	}
	public void initView() {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
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
//		ShareSDK.initSDK(this);
//		OnekeyShare oks = new OnekeyShare();
//		//关闭sso授权
//		oks.disableSSOWhenAuthorize();
//
//		// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
//		//oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
//		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//		oks.setTitle(getString(R.string.share));
//		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//		oks.setTitleUrl("http://sharesdk.cn");
//		// text是分享文本，所有平台都需要这个字段
//		oks.setText("我是分享文本");
//		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//		oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//		// url仅在微信（包括好友和朋友圈）中使用
//		oks.setUrl("http://sharesdk.cn");
//		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
//		oks.setComment("我是测试评论文本");
//		// site是分享此内容的网站名称，仅在QQ空间使用
//		oks.setSite(getString(R.string.app_name));
//		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
//		oks.setSiteUrl("http://sharesdk.cn");
//
//		// 启动分享GUI
//		oks.show(this);
	}
	//===========================================================
}

