package com.ms.ebangw.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ms.ebangw.view.XListView;

/**
 * 重构listview每个item的高度
 */
public class Utility {
	public static void setlistview(ListView lv){
		ListAdapter listadapter=lv.getAdapter();
		if(listadapter==null){
			return;
		}
		
		int aHeight=0;
		for (int i = 0; i < listadapter.getCount(); i++) {
			 View listItem = listadapter.getView(i, null, lv);
		        listItem.measure(0, 0); 
		        aHeight += listItem.getMeasuredHeight(); 
		}
		
		ViewGroup.LayoutParams params=lv.getLayoutParams();
		params.height=aHeight+lv.getDividerHeight()*(listadapter.getCount()-1);
		lv.setLayoutParams(params);
		
	}
	//重写listview的高度
	public static void setListView(XListView xlistview) {
		// TODO Auto-generated method stub
		ListAdapter listadapter=xlistview.getAdapter();
		if(listadapter==null){
			return;

		}

		int aHeight=0;
		for (int i = 0; i < listadapter.getCount(); i++) {
			View listItem = listadapter.getView(i, null, xlistview);
			listItem.measure(0, 0);
			aHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params=xlistview.getLayoutParams();
		params.height=aHeight+xlistview.getDividerHeight()*(listadapter.getCount()-1);
		xlistview.setLayoutParams(params);
	}
}
