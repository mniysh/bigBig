package com.ms.ebangw.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ms.ebangw.R;

/**
 * 个人基本信息
 */
public class PersonBaseInfoFragment extends BaseFragment {

	private static final String CATEGORY = "category";

	private String category;


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
		return inflater.inflate(R.layout.fragment_person_base_info, null);
	}

	@Override
	public void initView() {

	}

	@Override
	public void initData() {

	}
}
