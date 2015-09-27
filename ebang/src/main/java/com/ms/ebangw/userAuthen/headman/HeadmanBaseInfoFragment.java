package com.ms.ebangw.userAuthen.headman;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.AuthInfo;
import com.ms.ebangw.bean.City;
import com.ms.ebangw.bean.Province;
import com.ms.ebangw.bean.TotalRegion;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.fragment.BaseFragment;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.utils.VerifyUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人基本信息
 */
public class HeadmanBaseInfoFragment extends BaseFragment {

	private static final String CATEGORY = "category";

	private String category;
	private ViewGroup contentLayout;

	@Bind(R.id.et_phone)
	EditText phoneEt;
	@Bind(R.id.et_account_name)
	EditText readNameEt;
	@Bind(R.id.et_identify_card)
	EditText cardEt;
	@Bind(R.id.rg_gender)
	RadioGroup genderRg;
	@Bind(R.id.sp_a)
	Spinner provinceSp;
	@Bind(R.id.sp_b)
	Spinner citySp;
	@Bind(R.id.btn_next)
	Button nextBtn;

	private List<Province> provinces;
	private Province province;
	ArrayAdapter<Province> adapter01;
	ArrayAdapter<City> adapter02;


	public static HeadmanBaseInfoFragment newInstance(String category) {
		HeadmanBaseInfoFragment fragment = new HeadmanBaseInfoFragment();
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
		contentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_headman_base_info, null);
		ButterKnife.bind(this, contentLayout);
		initView();
		initData();
		return contentLayout;
	}

	@Override
	public void initView() {
		setStarRed();
	}

	@Override
	public void initData() {

		initSpinner();
	}

	public void initSpinner() {
		provinces = getProvinces();
		if (null == provinces) {
			return;
		}

		adapter01 = new ArrayAdapter<>(mActivity,
			R.layout.layout_spinner_item, provinces);


		provinceSp.setAdapter(adapter01);
		provinceSp.setSelection(0, true);

		adapter02 = new ArrayAdapter<>(mActivity, R.layout.layout_spinner_item, provinces
			.get(0).getCitys());
		citySp.setAdapter(adapter02);
		citySp.setSelection(0, true);

		provinceSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {
				province = provinces.get(position);

				adapter02 = new ArrayAdapter<>(mActivity,
					R.layout.layout_spinner_item, provinces.get(
					position).getCitys());

				citySp.setAdapter(adapter02);

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	@OnClick(R.id.btn_next)
	public void goNext() {
//		if (!isInfoCorrect()) {
//			return;
//		}
		AuthInfo authInfo = getAuthInfo();
		HeadmanAuthenActivity activity = (HeadmanAuthenActivity) mActivity;
		activity.setAuthInfo(authInfo);
		activity.goNext();
	}

	private boolean isInfoCorrect() {
		String realName = readNameEt.getText().toString().trim();
		String cardId = cardEt.getText().toString().trim();
		String phone = phoneEt.getText().toString().trim();
		if (TextUtils.isEmpty(realName)) {
			T.show("请输入真实姓名");
			return false;
		}

		if (!VerifyUtils.isIdentifyCard(cardId)) {
			T.show("请输入正确的身份证号码");
			return false;
		}

		if (!VerifyUtils.isPhone(phone)) {
			T.show("请输入手机号");
			return false;
		}

		return true;
	}



	public AuthInfo getAuthInfo() {
		String realName = readNameEt.getText().toString().trim();
		String cardId = cardEt.getText().toString().trim();
		String phone = phoneEt.getText().toString().trim();


		AuthInfo authInfo = new AuthInfo();

		//性别
		int checkId = genderRg.getCheckedRadioButtonId();
		String gender = Constants.MALE;
		if (checkId == R.id.rb_male) {
			gender =  Constants.MALE;
		}else {
			gender =  Constants.FEMALE;
		}

		//获取籍贯
		TextView provinceTv = (TextView) provinceSp.getSelectedView();
		TextView cityTv = (TextView) citySp.getSelectedView();

		String province = null;
		String city = null;
		if (provinceTv != null && cityTv != null) {

			province = provinceTv.getText().toString().trim();
			city = cityTv.getText().toString().trim();
		}
		String  provinceId = null;
		String cityId = null;

		List<Province> provinces = getProvinces();
		for (int i = 0; i < provinces.size(); i++) {
			Province p = provinces.get(i);
			if(TextUtils.equals(p.getName(), province)){
				provinceId = p.getId();
				List<City> citys = p.getCitys();
				for (int j = 0; j < citys.size(); j++) {
					City c = citys.get(j);
					if(TextUtils.equals(c.getName(), city)){
						cityId = c.getId();
						break;
					}
				}
				break;
			}
		}

		authInfo.setRealName(realName);
		authInfo.setIdentityCard(cardId);
		authInfo.setGender(gender);
		authInfo.setPhone(phone);
		authInfo.setProvinceId(provinceId);
		authInfo.setCityId(cityId);
		return authInfo;
	}

	/**
	 * 把*变成红色
	 */
	public void setStarRed() {
		int[] resId = new int[]{R.id.tv_a, R.id.tv_b, R.id.tv_c, R.id.tv_d, R.id.tv_e};
		for (int i = 0; i < resId.length; i++) {
			TextView a = (TextView) contentLayout.findViewById(resId[i]);
			String s = a.getText().toString();
			SpannableString spannableString = new SpannableString(s);
			spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			a.setText(spannableString);
		}
	}



	public List<Province> getProvinces() {
		TotalRegion totalRegion = ((HeadmanAuthenActivity) mActivity).getTotalRegion();
		if (totalRegion == null) {
			return null;
		}else {
			return totalRegion.getProvince();
		}
	}

}
