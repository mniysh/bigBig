package com.ms.ebangw.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ms.ebangw.MyApplication;

//字符串处理
public class StringUtils {
	//点赞的字符串处理，加1返回
	public static String string_DianZan(String str){

		char[] zan=str.toCharArray();

		StringBuffer sb=new StringBuffer();

		for (int i = 1; i < zan.length-1; i++) {
			sb.append(zan[i]);
		}
		Log.i("aaa", sb.toString());
		int number=Integer.valueOf(sb.toString());
		number++;
		Log.i("aaa","内容是"+ number);
		return "("+number+")";
	}
	public static boolean stringSubmit(Context context, String address, String money, String title, String content, String name, String phone){
		if(address==null||address.equals("")||money==null||money.equals("")||title==null||title.equals("")||
			content==null||content.equals("")||name==null||name.equals("")||phone==null||phone.equals("")){
			Toast.makeText(context, "内容不能为空", Toast.LENGTH_SHORT).show();
			MyApplication.instance.setFlag_sub(1);
			return false;
		}

		return true;
	}
}

