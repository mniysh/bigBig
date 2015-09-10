package com.ms.ebangw.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

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
}
