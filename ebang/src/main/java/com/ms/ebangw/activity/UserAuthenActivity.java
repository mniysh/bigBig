package com.ms.ebangw.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import com.ms.ebangw.R;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.fragment.CardVerifyFragment;
import com.ms.ebangw.fragment.PersonBaseInfoFragment;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 用户认证
 */
public class UserAuthenActivity extends BaseActivity{
	/**
	 * 要认证的用户类型
	 */
	private String category;

	private List<Fragment> list;
	private FragmentManager fm;

//	@Bind(R.id.viewPager)
//	NoScrollViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_headman_authen);
		ButterKnife.bind(this);
		initView();
		initData();
	}



	public void initView() {
		initTitle(null, "返回", "工长认证", null, null);

	}

	@Override
	public void initData() {
		fm = getFragmentManager();
		category = getIntent().getExtras().getString(Constants.KEY_CATEGORY, Constants.INVESTOR);
//		PersonBaseInfoFragment personBaseInfoFragment = PersonBaseInfoFragment.newInstance(category);
//		CardVerifyFragment cardVerifyFragment = new CardVerifyFragment();
//		BankVerifyFragment bankVerifyFragment = new BankVerifyFragment();
//
//		list = new ArrayList<>();
//		list.add(personBaseInfoFragment);
//		list.add(cardVerifyFragment);
//		list.add(bankVerifyFragment);
//
//		VerifyUserPagerAdapter pagerAdapter = new VerifyUserPagerAdapter(getFragmentManager(), list);
//
//		viewPager.setAdapter(pagerAdapter);

		switch (category) {
			case Constants.HEADMAN:
				getFragmentManager().beginTransaction().replace(R.id.fl_content,
					PersonBaseInfoFragment.newInstance(category)).commit();
				break;


		}

	}


	public void goNext() {

		switch (category) {
			case Constants.HEADMAN:
				getFragmentManager().beginTransaction().replace(R.id.fl_content,
					CardVerifyFragment.newInstance(category)).addToBackStack
					("CardVerifyFragment").commit();
				break;


		}





	}





}
