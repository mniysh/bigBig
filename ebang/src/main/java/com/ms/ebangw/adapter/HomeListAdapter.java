package com.ms.ebangw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ms.ebangw.R;


public class HomeListAdapter extends BaseAdapter {

	Context context;
	public HomeListAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 15;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = LayoutInflater.from(context).inflate(
				R.layout.home_listview, null);
		return convertView;
	}
}