package com.ms.ebangw.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.HomeActivity;
import com.ms.ebangw.bean.FoundBean;
import com.ms.ebangw.view.ViewHolder;

import java.util.List;


public class FoundFragmentAdapter extends BaseAdapter {
	private HomeActivity act;
	private List<FoundBean> datas;
	
	
	
	public FoundFragmentAdapter(HomeActivity act, List<FoundBean> datas) {
		super();
		this.act = act;
		this.datas = datas;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(datas==null){
			return 0;
		}else{
			return datas.size();
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
		convertView=act.getLayoutInflater().inflate(R.layout.item_frag_found_xlistview, null);
		
		}
		
		ImageView iv_head= ViewHolder.get(convertView, R.id.item_frag_found_xlistview_head);
		Button but_qiandan=ViewHolder.get(convertView, R.id.item_frag_found_xlistview_qiangdan);
		TextView tv_title=ViewHolder.get(convertView, R.id.item_frag_found_xlistview_title);
		TextView tv_content=ViewHolder.get(convertView, R.id.item_frag_found_xlistview_content);
		TextView tv_qiangdan=ViewHolder.get(convertView, R.id.item_frag_found_xlistview_qiangdan);
		TextView tv_juli=ViewHolder.get(convertView, R.id.item_frag_found_xlistview_juli);
		TextView tv_count=ViewHolder.get(convertView, R.id.item_frag_found_xlistview_count);
		
		return convertView;
	}

}
