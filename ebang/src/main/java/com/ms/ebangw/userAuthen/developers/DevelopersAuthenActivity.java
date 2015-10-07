package com.ms.ebangw.userAuthen.developers;

import android.app.Fragment;
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
import com.ms.ebangw.bean.TotalRegion;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.event.ReloadUserInfoEvent;
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
public class DevelopersAuthenActivity extends BaseActivity {
	/**
	 * 要认证的用户类型
	 */
	private String category;
	private File imageFile;
	private TotalRegion totalRegion;
	@Bind(R.id.tv_cardBind)
	TextView cardBindTv;
	private int currentStep;
	/**
	 * 认证的信息
	 */
	private AuthInfo authInfo;

	private List<Fragment> list;
	private FragmentManager fm;
	private DevelopersIdentityCardFragment  identifyFragment;
	private DevelopersBaseInfoFragment personBaseInfoFragment;
	private DevelopersBankVerifyFragment developersBankVerifyFragment;






	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_developers_authen);
		ButterKnife.bind(this);
		Bundle extras = getIntent().getExtras();
		category = extras.getString(Constants.KEY_CATEGORY, Constants.INVESTOR);
		totalRegion = (TotalRegion) extras.getSerializable(Constants.KEY_TOTAL_REGION);
		initView();
		initData();
	}

	public void initView() {
		initTitle(null, "返回", "开发商认证", null, null);
		cardBindTv.setText("企业完善");
	}

	@Override
	public void initData() {
		fm = getFragmentManager();
		personBaseInfoFragment = DevelopersBaseInfoFragment.newInstance(category);

		getFragmentManager().beginTransaction().replace(R.id.fl_content,personBaseInfoFragment
		).commit();
		setStep(0);
	}

	public void goNext() {

		identifyFragment = DevelopersIdentityCardFragment.newInstance(category);
		getFragmentManager().beginTransaction().replace(R.id.fl_content, identifyFragment)
			.addToBackStack("IdentityCardPhotoVerifyFragment").commit();
		setStep(1);
		currentStep = 1;
	}

	/**
	 * 身份证照片验证
	 */
	public void goVerifyBank() {
		developersBankVerifyFragment = DevelopersBankVerifyFragment.newInstance(category);
		getFragmentManager().beginTransaction().replace(R.id.fl_content,developersBankVerifyFragment).addToBackStack
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

	public TotalRegion getTotalRegion() {
		return totalRegion;
	}

	/**
	 * 提交认证信息
	 */
	public void commit() {
		String linkman = authInfo.getRealName();
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

		DataAccessUtil.developerIdentify(linkman, identityCard, frontImageId,
		backImageId, linkmanPhone, linkman_province,
			linkman_city, company_name, business_province, business_city,
			oftenAddress, businessAge, timeState, companyNumber,
			companyPhone, introduce, publicAccountName,
			account_province, publicAccount, organizationCertificate,
			businessLicenseNumber, businessScope, bankId, new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					try {
						boolean b = DataParseUtil.processDataResult(response);
						if(b){
							T.show(response.getString("message"));
							EventBus.getDefault().post(new ReloadUserInfoEvent());
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
}
