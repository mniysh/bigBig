package com.ms.ebangw.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class SysUtils {
	public static boolean check(Context context){
		ConnectivityManager cm=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo=cm.getActiveNetworkInfo();
		if(networkinfo==null){
			return false;
		}
		return networkinfo.isConnected();
	}
	
}
