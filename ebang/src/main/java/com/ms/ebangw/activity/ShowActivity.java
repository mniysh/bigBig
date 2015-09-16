package com.ms.ebangw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.view.View.OnClickListener;
import com.ms.ebangw.R;
import com.ms.ebangw.adapter.ShowTableAdapter;
import com.ms.ebangw.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * 企业的展示页面
 * 现在布局还有最底面的列表，打算用listview来实现
 * @author admin
 *
 */
public class ShowActivity extends BaseActivity implements OnClickListener {
	private ListView lTable;
	private ShowTableAdapter adapter;
	private List<String[]> datas;
	private String[] str1={"张三","时间","状态"};
	private String[] str2={"李四","时间","状态"};
	private String[] str3={"王五","时间","状态"};


	private Button bQiangdan;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_show);
		initView();
		initViewOper();


	}

	private void initViewOper() {
		datas=new ArrayList<String[]>();
		datas.add(str1);
		datas.add(str2);
		datas.add(str3);
		adapter=new ShowTableAdapter(this,datas);
		lTable.setAdapter(adapter);
		setListView(lTable);
		bQiangdan.setOnClickListener(this);
		initTitle(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ShowActivity.this.finish();
			}
		},"返回","企业展示",null,null);

	}

	public void initView() {
		lTable= (ListView) findViewById(R.id.act_show_listview);
		bQiangdan= (Button) findViewById(R.id.act_show_qiangdan);
	}

	@Override
	public void initData() {


	}

	//重写listview的高度
	private void setListView(ListView listview2) {
		// TODO Auto-generated method stub
		ListAdapter listadapter=listview2.getAdapter();
		if(listadapter==null){
			return;

		}

		int aHeight=0;
		for (int i = 0; i < listadapter.getCount(); i++) {
			View listItem = listadapter.getView(i, null, listview2);
			listItem.measure(0, 0);
			aHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params=listview2.getLayoutParams();
		params.height=aHeight+listview2.getDividerHeight()*(listadapter.getCount()-1);
		listview2.setLayoutParams(params);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){

			//点击立刻抢单跳转
			case R.id.act_show_qiangdan:
				L.d("xxx", "跳转能不能进来");
				startActivity(new Intent(this,QiangDanActivity.class));

				break;


			default:
				break;
		}
	}
}
