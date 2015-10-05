package com.ms.ebangw.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.Bank;
import com.ms.ebangw.bean.TotalRegion;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.fragment.AuthenticationFragment;
import com.ms.ebangw.fragment.DevelopersCenterFragment;
import com.ms.ebangw.fragment.FoundFragment;
import com.ms.ebangw.fragment.HeadmanCenterFragment;
import com.ms.ebangw.fragment.InvestorCenterFragment;
import com.ms.ebangw.fragment.LotteryFragment;
import com.ms.ebangw.fragment.ServiceFragment;
import com.ms.ebangw.fragment.WorkerCenterFragment;
import com.ms.ebangw.fragment.WorkerHomeFragment;
import com.ms.ebangw.release.ReleaseFragment;
import com.ms.ebangw.release.ReleaseFrament01;
import com.ms.ebangw.release.SelectCraftFragment;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;
import com.umeng.update.UmengUpdateAgent;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

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
	private TotalRegion totalRegion;
	private ServiceFragment serviceFragment;
	private ReleaseFrament01 releaseFrament01;

	private WorkerHomeFragment workerHomeFragment, eMallFragment;

	private SelectCraftFragment selectCraftFragment;
	private LotteryFragment lotteryFragment;
	private List<Bank> banks;

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
		getAreaFromAssets();
	}

	@Override
	public void initData() {
		fm = getFragmentManager();
//		foundFragment=new FoundFragment();
//		releasefragment=new ReleaseFragment();
//		serviceFragment=new ServiceFragment();
//		releaseFrament01 = new ReleaseFrament01();
//		selectCraftFragment = new SelectCraftFragment();
		lotteryFragment = LotteryFragment.newInstance("lotteryFragment", "lotteryFragment");
		workerHomeFragment = WorkerHomeFragment.newInstance(R.drawable.worker_home, 2);
		eMallFragment = WorkerHomeFragment.newInstance(R.drawable.e_mall, 1);

		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
					case R.id.rb_home:
//						fm.beginTransaction().replace(R.id.fl_content, new HomeFragment()).commit();
						fm.beginTransaction().replace(R.id.fl_content, lotteryFragment).commit();
						break;
					case R.id.rb_discovery:
//						fm.beginTransaction().replace(R.id.fl_content, foundFragment).commit();
						fm.beginTransaction().replace(R.id.fl_content, workerHomeFragment).commit();
						break;
					case R.id.rb_release:
//						fm.beginTransaction().replace(R.id.fl_content, releaseFrament01).commit();
						fm.beginTransaction().replace(R.id.fl_content, eMallFragment).commit();
						break;
//					case R.id.rb_server:
//						fm.beginTransaction().replace(R.id.fl_content,serviceFragment).commit();
//						break;
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
		totalRegion = getAreaFromAssets();
		loadBanks();



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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Constants.REQUEST_EXIT) {
			radioGroup.getChildAt(0).performClick();
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




//	/**
//	 * 获取省市区信息
//	 */
//	private void loadTotalRegion() {
//		DataAccessUtil.provinceCityArea(new JsonHttpResponseHandler() {
//
//			@Override
//			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//
//				try {
////					String s = response.toString();
//
//					totalRegion = DataParseUtil.provinceCityArea(response);
//
//
//				} catch (ResponseException e) {
//					e.printStackTrace();
//				}
//
//			}
//
//			@Override
//			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//				super.onFailure(statusCode, headers, responseString, throwable);
//				L.d(responseString);
//			}
//		});
//	}

	private void loadBanks() {
		banks = getBanks();
		MyApplication.getInstance().setBanks(banks);

//		DataAccessUtil.bankList(new JsonHttpResponseHandler() {
//
//			@Override
//			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//
//				try {
//					List<Bank> banks = DataParseUtil.bankList(response);
//					MyApplication.getInstance().setBanks(banks);
//
//				} catch (ResponseException e) {
//					e.printStackTrace();
//				}
//			}
//
//			@Override
//			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//				super.onFailure(statusCode, headers, responseString, throwable);
//				L.d(responseString);
//			}
//		});

	}

	public TotalRegion getTotalRegion() {
		return totalRegion;
	}
}
