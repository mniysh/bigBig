package com.ms.ebangw.adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.ms.ebangw.fragment.ClassFragment;

public class HomeClassAdapter extends FragmentPagerAdapter {

	private Context context;
	private int[] imgclass;
	private String[] txtclass;

	public HomeClassAdapter(FragmentManager fm,Context context,int[] imgclass,String[] txtclass) {
		super(fm);
		this.context = context;
		this.imgclass = imgclass;
		this.txtclass = txtclass;
	}
	@Override
	public Fragment getItem(int arg0) {
		Log.i("xxx", "当前的数据是"+arg0);
		Bundle b = new Bundle();
		b.putInt("img", imgclass[arg0]);
		b.putString("txt", txtclass[arg0]);
		ClassFragment f = new ClassFragment();
		f.setArguments(b);
		return f;
	}
	@Override
	public int getCount() {
		return 2;
	}


}
