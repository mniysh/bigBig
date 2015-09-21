package com.ms.ebangw.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.UserAuthenActivity;
import com.ms.ebangw.commons.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 个人基本信息
 */
public class PersonBaseInfoFragment extends BaseFragment {

	private static final String CATEGORY = "category";

	private String category;
	private View contentLayout;

	@Bind(R.id.et_phone)
	EditText phoneEt;
	@Bind(R.id.rg_gender)
	RadioGroup genderRg;
	@Bind(R.id.sp_a)
	Spinner provinceSp;
	@Bind(R.id.sp_b)
	Spinner citySp;
	@Bind(R.id.sp_c)
	Spinner areaSp;
	@Bind(R.id.btn_next)
	Button nextBtn;


	public static PersonBaseInfoFragment newInstance(String category) {
		PersonBaseInfoFragment fragment = new PersonBaseInfoFragment();
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
		contentLayout = inflater.inflate(R.layout.fragment_person_base_info, null);
		ButterKnife.bind(this, contentLayout);
		initView();
		initData();
		return contentLayout;
	}

	@Override
	public void initView() {

	}

	@Override
	public void initData() {
		nextBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((UserAuthenActivity)mActivity).goNext();
			}
		});

	}



	/**
	 * 获取籍贯
	 * @return
	 */
	public String getNativePlace() {
		StringBuilder builder = new StringBuilder();


		return builder.toString();
	}

	public String getGender() {
		int checkId = genderRg.getCheckedRadioButtonId();
		if (checkId == R.id.rb_male) {
			return Constants.MALE;
		}else {
			return Constants.FEMALE;
		}
	}
}
