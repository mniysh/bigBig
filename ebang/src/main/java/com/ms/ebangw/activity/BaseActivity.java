package com.ms.ebangw.activity;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.ms.ebangw.MyApplication;
import com.ms.ebangw.dialog.LoadingDialog;

public class BaseActivity extends AppCompatActivity {
	private final String TAG = getClass().getSimpleName();
	private Toast toast;
	private Application mApplication;
	private BaseActivity mActivity;
	public Context mContext;
	private LoadingDialog mLoadingDialog;
	private long exitTime = 0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApplication = MyApplication.getInstance();
		mContext = getApplicationContext();
		MyApplication.unDestroyActivityList.add(this);

	}

	/**
	 * 显示进度对话框
	 * @param message
	 */
	public void showPoressDialog(String message) {
		if (null != mLoadingDialog) {
			mLoadingDialog.dismiss();
			mLoadingDialog = null;
		}
		if (null == mLoadingDialog) {
			mLoadingDialog = LoadingDialog.newInstance(message) ;
		}
		mLoadingDialog.show(getFragmentManager(), TAG);
	}

	/**
	 * 关闭进度对话框
	 */
	public void dismissmLoadingDialog() {
		// && mLoadingDialog.isAdded()
		if (null != mLoadingDialog && null != mLoadingDialog.getActivity()&& mLoadingDialog.isVisible()) {
			mLoadingDialog.dismiss();
		}
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		MyApplication.unDestroyActivityList.remove(this);
	}

	/**
	 * 双击退出
	 */
	public void exitApp() {

		if ((System.currentTimeMillis() - exitTime) > 2000) {
			showToast("再按一次退出程序");
			exitTime = System.currentTimeMillis();
		} else {
			MyApplication.getInstance().quit();
			finish();
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitApp();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}


}
