package com.ms.ebangw.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.HomeActivity;
import com.ms.ebangw.activity.NextPageActivity;
import com.ms.ebangw.activity.RecommendActivity;
import com.ms.ebangw.adapter.HomeListAdapter;
import com.ms.ebangw.adapter.HomeViewpagerAdapter;
import com.ms.ebangw.bean.FoundBean;
import com.ms.ebangw.utils.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * 主页的主页页面
 */
public class HomeFragment extends BaseFragment implements OnClickListener   {
	private HomeActivity act;
	private ViewPager viewpager;// 滑动条
	private LinearLayout ldot;// 点布局
	private EditText search;
	private MyListView mylistview;
	FragmentManager fm;
	private int[] images = { R.drawable.linshi, R.drawable.linshi,
		R.drawable.linshi };// 滑动图片数据
	private int[] imgclass={R.drawable.home_build,R.drawable.home_zxiu,R.drawable.home_life,R.drawable.home_business};
	private String[] txtclass = {"建筑","装修","生活","商业"};
	private List<View> pager;
	private HomeViewpagerAdapter adapter;
	private View v1,v2,v3;
	private LinearLayout lin01,lin02,lin03;
	private List<FoundBean> datas;
	private View mContentView;
	@Bind(R.id.lin_build)
	LinearLayout lBuild;
	@Bind(R.id.lin_decorate)
	LinearLayout lDecorate;
	@Bind(R.id.lin_projectManage)
	LinearLayout lProjectManage;
	@Bind(R.id.lin_other)
	LinearLayout lOther;
	@OnClick({R.id.lin_build,R.id.lin_other,R.id.lin_decorate,R.id.lin_projectManage})
	void click(View view){
		startActivity(new Intent(act, NextPageActivity.class));
	}




	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		act = (HomeActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
							 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.fragment_home, container, false);
		ButterKnife.bind(this,mContentView);
		return mContentView;
	}


	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initDatas();
		initViewOper();
	}
	private void initViewOper() {
		adapter=new HomeViewpagerAdapter(pager);
		viewpager.setAdapter(adapter);
		//下面设置adapter，暂时没数据
		mylistview.setAdapter(new HomeListAdapter(act,datas));
		dot();
		viewpager.addOnPageChangeListener(new viewpagechangelistener());
//		act_home_class.setAdapter(new HomeClassAdapter(fm, act, imgclass, txtclass));


	}
	//获取值并设置点击事件
	private void initDatas() {
		pager = new ArrayList<View>();
		datas=new ArrayList<FoundBean>();
		FoundBean fb=new FoundBean("标题","公里数","简单的内同介绍","价格","价格","现在已有多少人抢单了");
		datas.add(fb);


		v1=act.getLayoutInflater().inflate(R.layout.item_pager_baner, null);
		((ImageView)v1.findViewById(R.id.item_pager_bager_iv)).setImageResource(R.drawable.linshi);
		v2=act.getLayoutInflater().inflate(R.layout.item_pager_baner, null);
		((ImageView)v2.findViewById(R.id.item_pager_bager_iv)).setImageResource(R.drawable.linshi);
		v3=act.getLayoutInflater().inflate(R.layout.item_pager_baner, null);
		((ImageView)v3.findViewById(R.id.item_pager_bager_iv)).setImageResource(R.drawable.linshi);

		pager.add(v1);
		pager.add(v2);
		pager.add(v3);


	}
	@Override
	public void onResume() {
		super.onResume();
		handler.sendEmptyMessageDelayed(1, 2000);// 延迟两秒发送message请求，改变viewpager图片
	}

	@Override
	public void onStop() {
		super.onStop();
		handler.removeMessages(1);// 停止viewpager图片切换
	}

	public void initView() {
		fm = act.getSupportFragmentManager();
		mylistview = (MyListView) getView().findViewById(R.id.frag_home_listV);
		ldot = (LinearLayout) getView().findViewById(R.id.act_home_ldot);
		viewpager = (ViewPager) getView().findViewById(R.id.act_home_viewpager);
//		act_home_class = (ViewPager)getView().findViewById(R.id.act_home_class);
		lin01=(LinearLayout) mContentView.findViewById(R.id.fragment_home_lin_recommend01);
		lin02=(LinearLayout) mContentView.findViewById(R.id.fragment_home_lin_recommend02);
		lin03=(LinearLayout) mContentView.findViewById(R.id.fragment_home_lin_recommend03);
		lin01.setOnClickListener(this);
		lin02.setOnClickListener(this);
		lin03.setOnClickListener(this);

	}

	@Override
	public void initData() {

	}

	// viewpager滑动改变的监听
	class viewpagechangelistener implements OnPageChangeListener {// 监听滑动

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {// 改变图片的指示“点”位置
			int len = ldot.getChildCount();
			ldot.removeAllViews();// 清空
			LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(10, 10);
			ll.setMargins(20, 0, 20, 0);
			for (int i = 0; i < len; i++) {
				ImageView iv = new ImageView(act);
				if (arg0 == i) {
					// ldot.getChildAt(arg0).setBackgroundResource(
					// R.drawable.point_red);
					iv.setImageResource(R.drawable.point_able);
					iv.setLayoutParams(ll);
				} else {
					iv.setImageResource(R.drawable.point_normal);
					iv.setLayoutParams(ll);
				}

				ldot.addView(iv);
				// ldot.getChildAt(i).setBackgroundResource(R.drawable.point_down);
			}

		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case 1:
					int index = viewpager.getCurrentItem();// 当期第几个图
					int totalcount = viewpager.getAdapter().getCount();// 全部个数
					int nextnum = index + 1 == totalcount ? 0 : index + 1;
					viewpager.setCurrentItem(nextnum);
					this.sendEmptyMessageDelayed(1, 2000);// 每两秒，改变viewpager的图片
			}
		}
	};

	public void dot() {
		for (int i = 0; i < images.length; i++) {
			ImageView mldot = new ImageView(act);
			LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(10, 10);
			ll.setMargins(20, 0, 20, 0);
			if (i == 0) {
				mldot.setImageResource(R.drawable.point_able);
			} else {
				mldot.setImageResource(R.drawable.point_normal);
			}
			mldot.setLayoutParams(ll);
			ldot.addView(mldot);
		}
	}
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();


	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.fragment_home_lin_recommend01:
			case R.id.fragment_home_lin_recommend02:
			case R.id.fragment_home_lin_recommend03:
				startActivity(new Intent(act,RecommendActivity.class));
			default:
				break;
		}
	}
}
