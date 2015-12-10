package com.ms.ebangw.userAuthen.labourCompany;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.AuthInfo;
import com.ms.ebangw.bean.City;
import com.ms.ebangw.bean.Province;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.fragment.BaseFragment;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.utils.VerifyUtils;
import com.ms.ebangw.view.ProvinceAndCityView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人基本信息
 */
public class LabourCompanyBaseInfoFragment extends BaseFragment {

	private static final String CATEGORY = "category";

	private String category;
	private ViewGroup contentLayout;

	@Bind(R.id.et_account_name)
	EditText readNameEt;
	@Bind(R.id.et_identify_card)
	EditText cardEt;
	@Bind(R.id.rg_gender)
	RadioGroup genderRg;
	@Bind(R.id.pac)
	ProvinceAndCityView pac;
	@Bind(R.id.btn_next)
	Button nextBtn;
	@Bind(R.id.et_introduce)
	EditText introduceEt;

	private List<Province> provinces;

	private Province province;
	ArrayAdapter<Province> adapter01;
	ArrayAdapter<City> adapter02;


	public static LabourCompanyBaseInfoFragment newInstance(String category) {
		LabourCompanyBaseInfoFragment fragment = new LabourCompanyBaseInfoFragment();
		Bundle args = new Bundle();
		args.putString(CATEGORY, category);
		fragment.setArguments(args);
		return fragment;
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			category = getArguments().getString(CATEGORY);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		contentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_investor_base_info, null);
		ButterKnife.bind(this, contentLayout);
		initView();
		initData();
		return contentLayout;
	}

	@Override
	public void initView() {
		setStarRed();
		contentLayout.findViewById(R.id.ll_introduce).setVisibility(View.VISIBLE);
	}

	@Override
	public void initData() {
		provinces = getProvinces();
		if (null == provinces) {
			return;
		}
		pac.setProvinces(provinces);	//设置籍贯
	}


	@OnClick(R.id.btn_next)
	public void goNext() {
		if (!isInfoCorrect()) {
			return;
		}
		AuthInfo authInfo = getAuthInfo();
		LabourCompanyAuthenActivity activity = (LabourCompanyAuthenActivity) mActivity;
		activity.setAuthInfo(authInfo);
		activity.goNext();
	}

	private boolean isInfoCorrect() {
		String realName = readNameEt.getText().toString().trim();
		String cardId = cardEt.getText().toString().trim();
		String introduce = introduceEt.getText().toString().trim();
		if (TextUtils.isEmpty(realName)) {
			T.show("请输入真实姓名");
			return false;
		}
		if(!VerifyUtils.isChinese(realName)){
			T.show("请输入中文姓名");
			return false;
		}

		if (!VerifyUtils.isIdentifyCard(cardId)) {
			T.show("请输入正确的身份证号码");
			return false;
		}

		if (TextUtils.isEmpty(introduce)) {
			T.show("请输入企业介绍");
			return false;
		}

		return true;
	}



	public AuthInfo getAuthInfo() {
		String realName = readNameEt.getText().toString().trim();
		String cardId = cardEt.getText().toString().trim();
		String introduce = introduceEt.getText().toString().trim();

		AuthInfo authInfo = new AuthInfo();

		//性别
		int checkId = genderRg.getCheckedRadioButtonId();
		String gender;
		if (checkId == R.id.rb_male) {
			gender =  Constants.MALE;
		}else {
			gender =  Constants.FEMALE;
		}

		//获取籍贯
		String provinceId = pac.getProvinceId();
		String cityId = pac.getCityId();

		authInfo.setRealName(realName);
		authInfo.setGender(gender);
		authInfo.setIdentityCard(cardId);
		authInfo.setProvinceId(provinceId);
		authInfo.setCityId(cityId);
		authInfo.setIntroduce(introduce);
		return authInfo;
	}

	/**
	 * 把*变成红色
	 */
	public void setStarRed() {
		int[] resId = new int[]{R.id.tv_a, R.id.tv_b, R.id.tv_c, R.id.tv_d, R.id.tv_e, R.id
			.tv_f, R.id.tv_g};
		for (int i = 0; i < resId.length; i++) {
			TextView a = (TextView) contentLayout.findViewById(resId[i]);
			if (null != a) {
				String s = a.getText().toString();
				SpannableString spannableString = new SpannableString(s);
				spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				a.setText(spannableString);
			}
		}
	}

}
