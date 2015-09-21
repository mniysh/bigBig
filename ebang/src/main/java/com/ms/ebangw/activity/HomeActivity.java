package com.ms.ebangw.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.fragment.AuthenticationFragment;
import com.ms.ebangw.fragment.DevelopersCenterFragment;
import com.ms.ebangw.fragment.FoundFragment;
import com.ms.ebangw.fragment.HeadmanCenterFragment;
import com.ms.ebangw.fragment.HomeFragment;
import com.ms.ebangw.fragment.InvestorCenterFragment;
import com.ms.ebangw.fragment.ReleaseFragment;
import com.ms.ebangw.fragment.WorkerCenterFragment;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;
import com.umeng.update.UmengUpdateAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;


/**
 * Home主页面
 * @author admin
 *
 */
public class HomeActivity extends BaseActivity {
	private FragmentManager fm;
	private long exitTime = 0;

	private FoundFragment foundFragment;
	private ReleaseFragment releasefragment;

	@Bind(R.id.radioGroup)
	RadioGroup radioGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		ButterKnife.bind(this);
		initView();
		initData();
		initUMengUpdate();
	}

	public void initView() {

	}

	@Override
	public void initData() {
		fm = getFragmentManager();
		foundFragment=new FoundFragment();
		releasefragment=new ReleaseFragment();

		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
					case R.id.rb_home:
						fm.beginTransaction().replace(R.id.fl_content, new HomeFragment()).commit();
						break;
					case R.id.rb_discovery:
						fm.beginTransaction().replace(R.id.fl_content ,foundFragment).commit();
						break;
					case R.id.rb_release:
						fm.beginTransaction().replace(R.id.fl_content,releasefragment).commit();
						break;
					case R.id.rb_server:

						break;
					case R.id.rb_mine:

						L.d("xxx", "返回值是" + isLogin());
						if (isLogin()) {
							User user = getUser();
							String category = user.getCategory();
							L.d("xxx", "user的内容主页部分的" + user.toString());
							goCenter(category);

						} else {
							Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
							startActivity(intent);
						}
						break;
				}
			}
		});

		radioGroup.getChildAt(0).performClick();
	}

	/**
	 * 根据人员类型跳转到相应的内容
	 * @param category	//用户已认证类型  worker(工人)/headman(工头)/developers(开发商)/investor(个人)  null（未认证）
	 */
	public void goCenter(String category) {
		if (TextUtils.isEmpty(category)) {		////用户未认证类型
			AuthenticationFragment authenticationfragment = new AuthenticationFragment();
			fm.beginTransaction().replace(R.id.fl_content, authenticationfragment).commit();
			return;
		}

		switch (category) {
			case Constants.INVESTOR:        //个人
				fm.beginTransaction().replace(R.id.fl_content, new InvestorCenterFragment()).commit();
				break;

			case Constants.HEADMAN:        //	工头
				fm.beginTransaction().replace(R.id.fl_content, new HeadmanCenterFragment()).commit();
				break;

			case Constants.WORKER:    //工人
				fm.beginTransaction().replace(R.id.fl_content, new WorkerCenterFragment()).commit();
				break;

			case Constants.DEVELOPERS:    //开发商
				fm.beginTransaction().replace(R.id.fl_content, new DevelopersCenterFragment()).commit();
				break;

		}
	}

	/**
	 * 友盟版本更新
	 */
	public void initUMengUpdate() {
		UmengUpdateAgent.update(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		JPushInterface.init(getApplicationContext());
		JPushInterface.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
	}

	/**
	 * 双击退出
	 */
	public void exitApp() {

		if ((System.currentTimeMillis() - exitTime) > 2000) {
			T.show("再按一次退出程序");
			exitTime = System.currentTimeMillis();
		} else {
			MyApplication.getInstance().quit();
			finish();
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitApp();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
