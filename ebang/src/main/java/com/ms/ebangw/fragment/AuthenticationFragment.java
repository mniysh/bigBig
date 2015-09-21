package com.ms.ebangw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.UserAuthenActivity;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.commons.Constants;

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
	@Bind(R.id.tv_name)
	TextView nameTv;
	@Bind(R.id.tv_phone)
	TextView phoneTv;
	@Bind(R.id.tv_rank)
	TextView rankTv;


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
		initViewOper();
		initData();
	}
	private void initViewOper() {
		but_self.setOnClickListener(this);
		but_worker.setOnClickListener(this);
		but_foreman.setOnClickListener(this);
		but_factory.setOnClickListener(this);

	}
	public void initView() {

		initTitle("我的信息");
		but_self=(Button) mContentView.findViewById(R.id.btn_investor);
		but_worker=(Button) mContentView.findViewById(R.id.btn_worker);
		but_foreman=(Button) mContentView.findViewById(R.id.btn_headman);
		but_factory=(Button) mContentView.findViewById(R.id.btn_developers);
	}

	@Override
	public void initData() {
		initHeadInfo();
	}

	/**
	 * 用户信息
	 */
	public void initHeadInfo() {
		User user = getUser();
		String nick_name = user.getNick_name();
		String phone = user.getPhone();
		String rank = user.getRank();
		nameTv.setText(nick_name);
		phoneTv.setText(phone);
		rankTv.setText("等级：" + rank + " 级");
	}

	@Override
	public void onClick(View v) {	////worker(工人)/headman(工头)/developers(开发商)/investor(个人)

//		switch (v.getId()) {
//			//个人认证
//			case R.id.btn_investor:
//				bundle.putString(Constants.KEY_CATEGORY, Constants.INVESTOR);
//				intent = new Intent(mActivity,InvestorAuthenticationActivity.class);
//				intent.putExtras(bundle);
//				startActivity(intent);
//				break;
//			case R.id.btn_worker:	//工人
//				bundle.putString(Constants.KEY_CATEGORY, Constants.WORKER);
//
//				startActivity(new Intent(mActivity,WorkerAuthenActivity.class));
//				break;
//			case R.id.btn_headman:	//工头
//				startActivity(new Intent(mActivity,UserAuthenActivity.class));
//				break;
//			case R.id.btn_developers:	//开发商
//				startActivity(new Intent(mActivity,DevelopersAuthenActivity.class));
//				break;
//
//			default:
//				break;
//		}
		String category = Constants.INVESTOR;
		switch (v.getId()) {
			//			//个人认证
			case R.id.btn_investor:
				category = Constants.INVESTOR;
				break;
			case R.id.btn_worker:	//工人
				category = Constants.WORKER;
				break;
			case R.id.btn_headman:	//工头
				category = Constants.HEADMAN;
				break;
			case R.id.btn_developers:	//开发商
				category = Constants.DEVELOPERS;
				break;
//
		}
		Bundle bundle = new Bundle();
		bundle.putString(Constants.KEY_CATEGORY, category);

		Intent intent = new Intent(mActivity, UserAuthenActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);


	}
}

