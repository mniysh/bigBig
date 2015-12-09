package com.ms.ebangw.utils;

import android.content.Context;
import android.util.Log;
import android.view.WindowManager;
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
//	public void backgroundAlpha(float bgAlpha,Context context)
//	{
//		WindowManager.LayoutParams lp = getWindow().getAttributes();
//		lp.alpha = bgAlpha; //0.0-1.0
//		getWindow().setAttributes(lp);
//	}

	/**
	 * 处理电话号码
	 * @param phone
	 * @return
	 */
	public static  String  setPhone(String phone){
		StringBuilder stringBuilder = new StringBuilder();
		String beginStr = phone.substring(0,3);
		String endStr = phone.substring(phone.length()-4,phone.length()-1);
		stringBuilder.append(beginStr).append("****").append(endStr);
		return stringBuilder.toString();
	}

	/**
	 * 处理姓名
	 * @param realName
	 * @return
	 */
	public static  String setRealName(String realName ){
//		StringBuilder sb = new StringBuilder();
//		String modifyNameStr = realName.substring(1,realName.length()-1);
//		sb.append("*").append(modifyNameStr);
		char[] a = realName.toCharArray();
		String aa = realName.replace(a[0], '*');
		return aa;

	}
	public static String setString(String time){
		String [] a = time.split("-");
		StringBuffer sb = new StringBuffer();
		for (String s: a) {
			sb.append(s);
		}
		return  sb.toString();
	}
}

