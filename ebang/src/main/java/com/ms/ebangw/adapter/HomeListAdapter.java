package com.ms.ebangw.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.HomeActivity;
import com.ms.ebangw.activity.QiangDanActivity;
import com.ms.ebangw.activity.ShowActivity;
import com.ms.ebangw.bean.FoundBean;
import com.ms.ebangw.view.ViewHolder;

import java.util.List;

import cn.sharesdk.onekeyshare.PicViewer;


public class HomeListAdapter extends BaseAdapter implements View.OnClickListener {
	private List<FoundBean> datas;
	private HomeActivity act;

	public HomeListAdapter(HomeActivity act, List<FoundBean> datas) {
		this.act = act;
		this.datas = datas;
	}

	@Override
	public int getCount() {
		if(datas==null){
			return  0;
		}
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=act.getLayoutInflater().inflate(R.layout.home_listview,null);
		}
		ImageView iv_head= ViewHolder.get(convertView,R.id.home_item_head);
		TextView tTitle=ViewHolder.get(convertView,R.id.home_item_title);
		TextView tjuli=ViewHolder.get(convertView,R.id.home_item_juli);
		TextView tcontent=ViewHolder.get(convertView,R.id.home_item_content);
		TextView tMoney=ViewHolder.get(convertView,R.id.home_item_money);
		TextView tAlready=ViewHolder.get(convertView,R.id.home_item_already);
		Button bQiangdan=ViewHolder.get(convertView,R.id.home_item_qiangdan);

		tTitle.setText(datas.get(position).getTitle());
		tjuli.setText(datas.get(position).getArea());
		tcontent.setText(datas.get(position).getContent());
		tMoney.setText(datas.get(position).getMoney());
		tAlready.setText(datas.get(position).getQiangdan());

		bQiangdan.setOnClickListener(this);


		return convertView;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.home_item_qiangdan:
				act.startActivity(new Intent(act, ShowActivity.class));

				break;
			default:
				break;
		}
	}
//	Context context;
//	public HomeListAdapter(Context context) {
//		// TODO Auto-generated constructor stub
//		this.context = context;
//	}
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return 15;
//	}
//
//	@Override
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}
//
//	@Override
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		convertView = LayoutInflater.from(context).inflate(
//				R.layout.home_listview, null);
//		return convertView;
//	}
}