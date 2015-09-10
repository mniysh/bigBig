package com.ms.ebangw;

import android.app.Application;

public class MyApplication extends Application {
	
	
	public static MyApplication instance;
	private int flag_sub;
	private String phone;
	private String password;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance=this;
	}
	public int getFlag_sub() {
		return flag_sub;
	}
	public void setFlag_sub(int flag_sub) {
		this.flag_sub = flag_sub;
	}
	
	
}












