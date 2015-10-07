package com.ms.ebangw.userAuthen.worker;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.bean.AuthInfo;
import com.ms.ebangw.bean.TotalRegion;
import com.ms.ebangw.bean.WorkType;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.event.ReloadUserInfoEvent;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.userAuthen.InfoCommitSuccessFragment;
import com.ms.ebangw.utils.JsonUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 个人用户认证
 */
public class WorkerAuthenActivity extends BaseActivity {
	/**
	 * 要认证的用户类型
	 */
	private String category;
	private TotalRegion totalRegion;

	/**
	 * 认证的信息
	 */
	private AuthInfo authInfo = new AuthInfo();

	private List<Fragment> list;
	private FragmentManager fm;
	private WorkerIdentityCardFragment  identifyFragment;
	private WorkerBaseInfoFragment personBaseInfoFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_worker_authen);
		ButterKnife.bind(this);
		Bundle extras = getIntent().getExtras();
		category = extras.getString(Constants.KEY_CATEGORY, Constants.INVESTOR);
		totalRegion = (TotalRegion) extras.getSerializable(Constants.KEY_TOTAL_REGION);
		initView();
		initData();
	}

	public void initView() {

		initTitle(null, "返回", "务工认证", null, null);

	}

	@Override
	public void initData() {
		fm = getFragmentManager();
		personBaseInfoFragment = WorkerBaseInfoFragment.newInstance(category);

		getFragmentManager().beginTransaction().replace(R.id.fl_content, personBaseInfoFragment
		).commit();
		setStep(0);
	}

	public void goNext() {

		identifyFragment = WorkerIdentityCardFragment.newInstance(category);
		getFragmentManager().beginTransaction().replace(R.id.fl_content, identifyFragment)
			.addToBackStack("IdentityCardPhotoVerifyFragment").commit();
		setStep(1);
	}

	/**
	 * 身份证照片验证
	 */
	public void goVerifyBank() {

		getFragmentManager().beginTransaction().replace(R.id.fl_content,
			WorkerBankVerifyFragment.newInstance(category)).addToBackStack
			("BankVerifyFragment").commit();
		setStep(2);
	}

	public AuthInfo getAuthInfo() {
		return authInfo;
	}

	public void setAuthInfo(AuthInfo authInfo) {
		this.authInfo = authInfo;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 22 && resultCode == RESULT_OK) {						//获取选中的工种

			Bundle extras = data.getExtras();
			ArrayList<WorkType> workTypes = extras.getParcelableArrayList(Constants
				.KEY_SELECTED_WORKTYPES);
			processSelectedWorkTypes(workTypes);
		}
	}

	public void processSelectedWorkTypes(ArrayList<WorkType> workTypes) {
		int count = workTypes.size();
		String[] types = new String[count];
		for (int i = 0; i < count; i++) {
			types[i] = workTypes.get(i).getId();
		}
		String json = JsonUtil.createGsonString(types);
		authInfo.setCrafts(json);
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
		String bankCard = authInfo.getBankCard();
		String accountName = authInfo.getAccountName();
		String bankId = authInfo.getBankId();
		String bankProvinceId = authInfo.getBankProvinceId();
		String bankCityId = authInfo.getBankCityId();
		String crafts = authInfo.getCrafts();

		DataAccessUtil.workerIdentify(realName, identityCard, provinceId, cityId, frontImageId,
				backImageId, gender, bankCard, accountName, bankProvinceId, bankCityId,
				bankId, crafts, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						try {
							boolean b = DataParseUtil.processDataResult(response);
							if (b) {
								T.show(response.getString("message"));
								EventBus.getDefault().post(new ReloadUserInfoEvent());
								goResultFragment(Constants.WORKER);
							}
						} catch (ResponseException e) {
							e.printStackTrace();
							T.show(e.getMessage());
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
						super.onFailure(statusCode, headers, responseString, throwable);
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

}
