package com.ms.ebangw.userAuthen.labourCompany;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.bean.AuthInfo;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 开发商认证
 */
public class LabourCompanyAuthenActivity extends BaseActivity {
	/**
	 * 要认证的用户类型
	 */
	private static final String category = Constants.COMPANY;
	@Bind(R.id.tv_cardBind)
	TextView cardBindTv;
	private int currentStep;
	/**
	 * 认证的信息
	 */
	private AuthInfo authInfo;

	private FragmentManager fm;
	private LabourCompanyIdentityCardFragment identifyFragment;
	private LabourCompanyBaseInfoFragment baseInfoFragment;
	private LabourCompanyBankVerifyFragment bankVerifyFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_developers_authen);
		ButterKnife.bind(this);
		fm = getFragmentManager();
		L.d("LabourCompanyAuthenActivity onCreate");
		if (savedInstanceState != null) {
			authInfo = savedInstanceState.getParcelable(Constants.KEY_AUTHINFO);
			currentStep = savedInstanceState.getInt(Constants.KEY_CURRENT_STEP, 0);
			initTitle(null, "返回", "开发商认证", null, null);
			cardBindTv.setText("企业完善");
		}else {
			initView();
			initData();
		}

	}

	public void initView() {
		initTitle(null, "返回", "劳务公司认证", null, null);
		cardBindTv.setText("企业完善");
	}

	@Override
	public void initData() {
		fm = getFragmentManager();
		baseInfoFragment = LabourCompanyBaseInfoFragment.newInstance(category);

		getFragmentManager().beginTransaction().replace(R.id.fl_content, baseInfoFragment
		).commit();
		setStep(0);
	}

	public void goNext() {

		identifyFragment = LabourCompanyIdentityCardFragment.newInstance(category);
		getFragmentManager().beginTransaction().replace(R.id.fl_content, identifyFragment)
			.addToBackStack("IdentityCardPhotoVerifyFragment").commit();
		setStep(1);
		currentStep = 1;
	}

	/**
	 * 身份证照片验证
	 */
	public void goVerifyBank() {
		bankVerifyFragment = LabourCompanyBankVerifyFragment.newInstance(category);
		getFragmentManager().beginTransaction().replace(R.id.fl_content, bankVerifyFragment).addToBackStack
			("BankVerifyFragment").commit();
		setStep(2);
		currentStep = 2;
	}

	public AuthInfo getAuthInfo() {
		return authInfo;
	}

	public void setAuthInfo(AuthInfo authInfo) {
		this.authInfo = authInfo;
	}


	/**
	 * 提交认证信息
	 */
	public void commit() {
		String realName = authInfo.getRealName();
		String identityCard = authInfo.getIdentityCard();
		String frontImageId = authInfo.getFrontImageId();
		String backImageId = authInfo.getBackImageId();
		String linkmanPhone = authInfo.getPhone();
		String linkman_province = authInfo.getProvinceId();
		String linkman_city = authInfo.getCityId();
		String company_name = authInfo.getCompanyName();
		String business_province = authInfo.getPermitProvinceId();
		String business_city = authInfo.getPermitCityId();
		String oftenAddress = authInfo.getOftenAddress();
		String businessAge = authInfo.getBusinessAge();
		String timeState = authInfo.getTimeState();
		String companyNumber = authInfo.getCompanyNumber();
		String companyPhone = authInfo.getCompanyPhone();
		String introduce = authInfo.getIntroduce();
		String publicAccountName = authInfo.getPublicAccountName();
		String account_province = authInfo.getPublicAccountProvinceId();
		String publicAccount = authInfo.getPublicAccount();
		String organizationCertificate = authInfo.getOrganizationCertificate();
		String businessLicenseNumber = authInfo.getBusinessLicenseNumber();
		String businessScope = authInfo.getBusinessScope();
		String bankId = authInfo.getBankId();
		String gender = authInfo.getGender();
		String accountCityId = authInfo.getPublicAccountCityId();

		DataAccessUtil.companyIdentify(realName, identityCard, frontImageId,
			backImageId, linkmanPhone, linkman_province,
			linkman_city, company_name, business_province, business_city,
			oftenAddress, businessAge, timeState, companyNumber,
			companyPhone, introduce, publicAccountName,
			account_province, publicAccount, organizationCertificate,
			businessLicenseNumber, businessScope, bankId, gender, accountCityId, new
				JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

						try {
							boolean b = DataParseUtil.processDataResult(response);
							if (b) {
								T.show(response.getString("message"));
								saveAuthStatusInLocal();
								EventBus.getDefault().post(new RefreshUserEvent(Constants.DEVELOPERS));
								goResultFragment(Constants.DEVELOPERS);
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
				}
		);
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
		user.setStatus(Constants.AUTH_DEVELOPERS);
		UserDao userDao = new UserDao(this);
		userDao.update(user);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(Constants.KEY_AUTHINFO, authInfo);
		outState.putInt(Constants.KEY_CURRENT_STEP, currentStep);
		L.d("LabourCompanyAuthenActivity onSaveInstanceState");
		super.onSaveInstanceState(outState);
	}
}
