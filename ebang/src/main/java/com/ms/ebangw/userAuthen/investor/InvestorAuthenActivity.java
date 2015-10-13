package com.ms.ebangw.userAuthen.investor;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.bean.AuthInfo;
import com.ms.ebangw.bean.TotalRegion;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.db.UserDao;
import com.ms.ebangw.event.RefreshUserEvent;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.userAuthen.InfoCommitSuccessFragment;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 个人用户认证
 */
public class InvestorAuthenActivity extends BaseActivity {
	/**
	 * 要认证的用户类型
	 */
	private static final String category = Constants.INVESTOR;
	private File imageFile;
	private TotalRegion totalRegion;


	/**
	 * 认证的信息
	 */
	private AuthInfo authInfo;

	private List<Fragment> list;
	private FragmentManager fm;
	private InvestorIdentityCardFragment  identifyFragment;
	private InvestorBaseInfoFragment personBaseInfoFragment;
	@Bind(R.id.tv_baseInfo)
	TextView baseInfoTv;
	@Bind(R.id.tv_nameAuth)
	TextView nameAuthTv;
	@Bind(R.id.tv_cardBind)
	TextView carBindTv;
	@Bind(R.id.tv_one)
	TextView oneTv;
	@Bind(R.id.tv_two)
	TextView twoTv;
	@Bind(R.id.tv_three)
	TextView threeTv;
//	@Bind(R.id.layout_title)
//	View view_title;
//	@Bind(R.id.layout_step)
//	View view_step;
	private int currentStep;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_investor_authen);
		ButterKnife.bind(this);
		fm = getFragmentManager();

		L.d("InvestorAuthenActivity onCreate");
		if (savedInstanceState != null) {
			authInfo = savedInstanceState.getParcelable(Constants.KEY_AUTHINFO);
			currentStep = savedInstanceState.getInt(Constants.KEY_CURRENT_STEP, 0);
			L.d("currentStep" + currentStep);

			initTitle(null, "返回", "个人认证", null, null);

		}else {
			initView();
			initData();

		}
	}

	public void initView() {
		initTitle(null, "返回", "个人认证", null, null);
		findViewById(R.id.step_line).setVisibility(View.GONE);
		findViewById(R.id.tv_three).setVisibility(View.GONE);
		findViewById(R.id.tv_cardBind).setVisibility(View.GONE);

		setStep(0);
		currentStep = 0;
	}


	@Override
	public void initData() {
		personBaseInfoFragment = InvestorBaseInfoFragment.newInstance(category);
		getFragmentManager().beginTransaction().replace(R.id.fl_content,
			personBaseInfoFragment).addToBackStack(null).commit();

	}

	public void goNext() {

		identifyFragment = InvestorIdentityCardFragment.newInstance(category);
//		identifyFragment.setRetainInstance(true);
		getFragmentManager().beginTransaction().replace(R.id.fl_content, identifyFragment)
			.addToBackStack("IdentityCardPhotoVerifyFragment").commit();
		setStep(1);
		currentStep = 1;
	}

	/**
	 * 身份证照片验证
	 */
	public void goVerifyBank() {

		getFragmentManager().beginTransaction().replace(R.id.fl_content,
			InvestorBankVerifyFragment.newInstance(category)).addToBackStack
			("BankVerifyFragment").commit();

//		commit();

	}

	public AuthInfo getAuthInfo() {
		return authInfo;
	}

	public void setAuthInfo(AuthInfo authInfo) {
		this.authInfo = authInfo;
	}

	public TotalRegion getTotalRegion() {
		return totalRegion;
	}

	/**
	 * 提交认证信息
	 */
	public void commit() {
		String realName = authInfo.getRealName();
		String gender = authInfo.getGender();
		String identityCard = authInfo.getIdentityCard();
		String provinceId = authInfo.getProvinceId();
		String cityId = authInfo.getCityId();
		String frontImageId = authInfo.getFrontImageId();
		String backImageId = authInfo.getBackImageId();
		L.d("xxx","进来了吗");
		DataAccessUtil.personIdentify(realName, gender, identityCard, provinceId, cityId,
			frontImageId,
			backImageId, null, new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					try {
						boolean b = DataParseUtil.processDataResult(response);
						L.d("xxx","boolean值"+b);
						if (b) {
							T.show(response.getString("message"));
							saveAuthStatusInLocal();
							EventBus.getDefault().post(new RefreshUserEvent(Constants.INVESTOR));
							goResultFragment(Constants.INVESTOR);
						}
					} catch (ResponseException e) {
						e.printStackTrace();
						T.show(e.getMessage());
					} catch (JSONException e) {
						e.printStackTrace();
						T.show(e.getMessage());
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
					L.d(responseString);
				}
			});
	}

	/**
	 * 第几个步骤 0 ，1 ，2
	 * @param step
	 */
	public void setStep(int step) {

		LinearLayout stepLayout = (LinearLayout) findViewById(R.id.ll_step);
		LinearLayout descLayout = (LinearLayout) findViewById(R.id.ll_step_desc);
		List<TextView> stepViews = new ArrayList<>();
		stepViews.add((TextView) stepLayout.getChildAt(0));
		stepViews.add((TextView) stepLayout.getChildAt(2));
		stepViews.add((TextView) stepLayout.getChildAt(4));

		for (int i = 0; i < stepViews.size(); i++) {
			TextView stepTv = stepViews.get(i);
			TextView descTv = (TextView) descLayout.getChildAt(i);
			if (i == step) {
				stepTv.setTextColor(Color.WHITE);
				stepTv.setBackgroundResource(R.drawable.rotundity_one);
				descTv.setTextColor(getResources().getColor(R.color.titleBar_bg));
			}else {
				stepTv.setTextColor(Color.BLACK);
				stepTv.setBackgroundResource(R.drawable.rotundity);
				descTv.setTextColor(Color.BLACK);
			}
		}
	}

	public void goResultFragment(String category) {

		InfoCommitSuccessFragment fragment = InfoCommitSuccessFragment.newInstance(category);
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.replace(R.id.fl_content, fragment).commit();
	}

	public void saveAuthStatusInLocal() {
		User user = getUser();
		user.setStatus(Constants.AUTH_INVESTOR);
		UserDao userDao = new UserDao(this);
		userDao.update(user);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(Constants.KEY_AUTHINFO, authInfo);
		outState.putInt(Constants.KEY_CURRENT_STEP, currentStep);
		L.d("InvestorAuthenActivity onSaveInstanceState");
		super.onSaveInstanceState(outState);
	}
}
