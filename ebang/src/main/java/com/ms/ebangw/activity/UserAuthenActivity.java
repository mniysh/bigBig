package com.ms.ebangw.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import com.ms.ebangw.Photo.SelectPhotosDirActivity;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.AuthInfo;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.fragment.BankVerifyFragment;
import com.ms.ebangw.fragment.IdentityCardPhotoVerifyFragment;
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



	/**
	 * 认证的信息
	 */
	private AuthInfo authInfo;

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
//		IdentityCardPhotoVerifyFragment cardVerifyFragment = new IdentityCardPhotoVerifyFragment();
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
			case Constants.WORKER:
			case Constants.INVESTOR:
				getFragmentManager().beginTransaction().replace(R.id.fl_content,
					PersonBaseInfoFragment.newInstance(category)).commit();
				break;
		}

	}


	public void goNext() {

		switch (category) {
			case Constants.HEADMAN:
			case Constants.WORKER:
			case Constants.INVESTOR:
				getFragmentManager().beginTransaction().replace(R.id.fl_content,
					IdentityCardPhotoVerifyFragment.newInstance(category)).addToBackStack
					("IdentityCardPhotoVerifyFragment").commit();
				break;


		}

	}

	/**
	 * 身份证照片验证
	 */
	public void goVerifyBank() {

		switch (category) {
			case Constants.HEADMAN:
			case Constants.WORKER:
				getFragmentManager().beginTransaction().replace(R.id.fl_content,
					BankVerifyFragment.newInstance(category)).addToBackStack
					("BankVerifyFragment").commit();
				break;
		}
	}


	public AuthInfo getAuthInfo() {
		return authInfo;
	}

	public void setAuthInfo(AuthInfo authInfo) {
		this.authInfo = authInfo;
	}




}
