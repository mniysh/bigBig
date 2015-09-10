package com.ms.ebangw.adapter;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.CommentActivity;
import com.ms.ebangw.bean.Comment;
import com.ms.ebangw.utils.StringUtils;
import com.ms.ebangw.view.ViewHolder;

import java.util.List;



public class CommentActivityAdapter extends BaseAdapter implements OnClickListener {
	private TextView tv_dianzan_number;
	private List<Comment> datas;
	private CommentActivity act;


	public CommentActivityAdapter(List<Comment> datas, CommentActivity act) {
		super();
		this.datas = datas;
		this.act = act;
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
			convertView=act.getLayoutInflater().inflate(R.layout.item_xlistview_comment, null);
		}
		//ImageView iv_head=ViewHolder.get(convertView, R.id.item_xlistview_iv_head);
		TextView tv_username=ViewHolder.get(convertView, R.id.item_xlistview_tv_username);
		TextView tv_content=ViewHolder.get(convertView, R.id.item_xlistview_tv_content);
		TextView tv_dianzan=ViewHolder.get(convertView, R.id.item_xlistview_tv_dianzan);
		tv_dianzan_number=ViewHolder.get(convertView, R.id.item_xlistview_tv_dianzan_number);
		LinearLayout lin_dianzan=ViewHolder.get(convertView, R.id.item_xlisview_lin_dianzan);
		tv_username.setText(datas.get(position).getUsername());
		tv_content.setText(datas.get(position).getContent());
		tv_dianzan.setText(datas.get(position).getClick_zan());
		tv_dianzan_number.setText(datas.get(position).getNumber());

		lin_dianzan.setOnClickListener(this);


		return convertView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.item_xlisview_lin_dianzan:
				Toast.makeText(act, "item的控件被点击", Toast.LENGTH_SHORT).show();
				//Log.i("aaa",tv_dianzan_number.getText().toString().trim() );
				Log.i("aaa", "adapter"+StringUtils.string_DianZan(tv_dianzan_number.getText().toString().trim()));
				tv_dianzan_number.setText(StringUtils.string_DianZan(tv_dianzan_number.getText().toString().trim()));
				break;

			default:
				break;
		}
	}


}
