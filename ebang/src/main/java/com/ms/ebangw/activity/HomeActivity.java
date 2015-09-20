package com.ms.ebangw.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.fragment.AuthenticationFragment;
import com.ms.ebangw.fragment.FoundFragment;
import com.ms.ebangw.fragment.HomeFragment;
import com.ms.ebangw.fragment.ReleaseFragment;
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
	private AuthenticationFragment authenticationfragment;

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
						if (isLogin()) {
							authenticationfragment = new AuthenticationFragment();
							fm.beginTransaction().replace(R.id.fl_content, authenticationfragment).commit();
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
