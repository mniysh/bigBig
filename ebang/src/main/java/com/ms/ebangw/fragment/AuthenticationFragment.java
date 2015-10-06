package com.ms.ebangw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.HomeActivity;
import com.ms.ebangw.activity.SettingActivity;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.userAuthen.developers.DevelopersAuthenActivity;
import com.ms.ebangw.userAuthen.headman.HeadmanAuthenActivity;
import com.ms.ebangw.userAuthen.investor.InvestorAuthenActivity;
import com.ms.ebangw.userAuthen.worker.WorkerAuthenActivity;
import com.ms.ebangw.utils.L;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 认证的页面
 * @author admin
 *
 */
public class AuthenticationFragment extends BaseFragment implements OnClickListener {

	private Button but_self,but_worker,but_foreman,but_factory;
	private View mContentView;
	private User user;

	@Bind(R.id.tv_name)
	TextView nameTv;
	@Bind(R.id.tv_phone)
	TextView phoneTv;
	@Bind(R.id.tv_rank)
	TextView rankTv;

	@Bind(R.id.tv_realName)
	TextView realNameTv;
	@Bind(R.id.tv_phone2)
	TextView phone2Tv;
	@Bind(R.id.tv_gender)
	TextView genderTv;
	@Bind(R.id.tv_native_place)
	TextView nativePlaceTv;
	@Bind(R.id.tv_work_type)
	TextView workTypeTv;
	@Bind(R.id.ll_authed)
	LinearLayout detailLayout;
	@Bind(R.id.ll_no_auth)
	LinearLayout noAuthLayout;

	@Override
	public View onCreateView(LayoutInflater inflater,
							 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.frag_authen, null);
		ButterKnife.bind(this, mContentView);
		return mContentView;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initData();
	}

	private void setNickName() {
		L.d("xxx", "现在的用户昵称是" + getUser().getNick_name());
		User user = getUser();
		if (null != user) {
			phoneTv.setText(user.getPhone());
			nameTv.setText(user.getNick_name());
		}
	}

	public void initView() {
		//initTitle("我的信息");
		initTitle(null, null, "我的信息", "设置", new OnClickListener() {
			@Override
			public void onClick(View v) {
				//设置跳转
				Intent intent=new Intent(mActivity, SettingActivity.class);

				mActivity.startActivityForResult(intent, Constants.REQUEST_EXIT);
			}
		});
		but_self=(Button) mContentView.findViewById(R.id.btn_investor);
		but_worker=(Button) mContentView.findViewById(R.id.btn_worker);
		but_foreman=(Button) mContentView.findViewById(R.id.btn_headman);
		but_factory=(Button) mContentView.findViewById(R.id.btn_developers);

		but_self.setOnClickListener(this);
		but_worker.setOnClickListener(this);
		but_foreman.setOnClickListener(this);
		but_factory.setOnClickListener(this);

	}

	public void initCompletedUser() {
		noAuthLayout.setVisibility(View.GONE);
		detailLayout.setVisibility(View.VISIBLE);
		User user = getUser();
		String real_name = user.getReal_name();
		String gender = user.getGender();
		String phone = user.getPhone();
		String area = user.getArea();
		String craft = user.getCraft();
		realNameTv.setText(real_name);
		genderTv.setText(gender);
		phone2Tv.setText(phone);
		nativePlaceTv.setText(area);
		workTypeTv.setText(craft);


	}

	public void initNoAuthUser() {

		noAuthLayout.setVisibility(View.VISIBLE);
		detailLayout.setVisibility(View.GONE);


	}

	@Override
	public void initData() {
		initHeadInfo();
		User user = getUser();
		String status = user.getStatus();		//认证状态
		switch (status) {
			case "guest":						//未申请
				initNoAuthUser();
				break;
			case "complete":				//认证审核通过
				initCompletedUser();
				break;
		}
	}

	/**
	 * 用户信息
	 */
	public void initHeadInfo() {
		User user = getUser();
		if (null != user) {
			String rank = user.getRank();
			setNickName();
			rankTv.setText("等级：" + rank + " 级");
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		setNickName();
	}

	@Override
	public void onClick(View v) {	////worker(工人)/headman(工头)/developers(开发商)/investor(个人)

		Intent intent;
		switch (v.getId()) {

			//个人认证
			case R.id.btn_investor:
				intent = new Intent(mActivity, InvestorAuthenActivity.class);
				break;
			case R.id.btn_worker:	//工人
				intent = new Intent(mActivity, WorkerAuthenActivity.class);
				break;
			case R.id.btn_headman:	//工头
				intent = new Intent(mActivity, HeadmanAuthenActivity.class);
				break;
			case R.id.btn_developers:	//开发商
				intent = new Intent(mActivity, DevelopersAuthenActivity.class);
				break;

			default:
				intent = new Intent(mActivity, InvestorAuthenActivity.class);
				break;
		}

		Bundle bundle = new Bundle();
		bundle.putSerializable(Constants.KEY_TOTAL_REGION, ((HomeActivity)mActivity).getTotalRegion());
		intent.putExtras(bundle);
		startActivity(intent);
	}

	/**
	 * 设置授权后的用户基本信息
	 */
	public void setAccreditedInfo() {




	}




}

