package com.ms.ebangw.activity;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ms.ebangw.MyApplication;

public class BaseActivity extends AppCompatActivity {
	private Toast toast;
	private Application mApplication;
	private BaseActivity mActivity;
	public Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApplication = MyApplication.getInstance();
		mContext = getApplicationContext();

	}

	public void toast(String str){
		Toast.makeText(this, str, Toast.LENGTH_LONG).show();
	}

	/**
	 * 显示Toast提示，短时间
	 *
	 * @param message
	 */
	public void showToast(String message) {
		if (toast == null) {
			toast = Toast.makeText(mApplication, message, Toast.LENGTH_SHORT);
		} else {
			toast.setText(message);
			toast.setDuration(Toast.LENGTH_SHORT);
		}
		toast.show();
	}

	/**
	 * 显示Toast提示，短时间
	 *
	 * @param sid
	 */
	public void showToast(int sid) {
		Toast.makeText(mActivity, sid, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 显示Toast提示，长时间
	 *
	 * @param message
	 */
	public void showToastLong(String message) {
		if (toast == null) {
			toast = Toast.makeText(mActivity, message, Toast.LENGTH_LONG);
		} else {
			toast.setText(message);
			toast.setDuration(Toast.LENGTH_LONG);
		}
		toast.show();
	}

	/**
	 * 显示Toast提示，长时间
	 *
	 * @param sid
	 */
	public void showToastLong(int sid) {
		Toast.makeText(mActivity, sid, Toast.LENGTH_LONG).show();
	}
}
