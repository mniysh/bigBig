package com.ms.ebangw.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.UserAuthenActivity;


public class ManagerBaseFragment extends BaseFragment {
	private UserAuthenActivity act;
	private Button bNext;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		return inflater.inflate(R.layout.frag_managerbase, null);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		act= (UserAuthenActivity) activity;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initData();

	}

	@Override
	public void initView() {
	}

	@Override
	public void initData() {

	}
}
