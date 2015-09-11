package com.ms.ebangw.activity;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.dialog.LoadingDialog;

public abstract class BaseActivity extends AppCompatActivity {
	private final String TAG = getClass().getSimpleName();
	private Toast toast;
	private Application mApplication;
	private BaseActivity mActivity;
	public Context mContext;
	private LoadingDialog mLoadingDialog;
	private long exitTime = 0;
	private View.OnClickListener mLeftClickListener, mBackClickListener, mRightClickListener;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApplication = MyApplication.getInstance();
		mContext = getApplicationContext();
		MyApplication.unDestroyActivityList.add(this);

	}

	/**
	 * @param leftClickLister  返回箭的点击监听
	 * @param left	左边标题
	 * @param title	中间标题
	 * @param right	右边标题
	 * @param rightClickListener 右边标题的点击监听
	 */
	public void initTitle(View.OnClickListener leftClickLister, String left, String title, String
		right, View.OnClickListener rightClickListener) {
		View backView = findViewById(R.id.iv_back);
		TextView leftTv = (TextView) findViewById(R.id.tv_left);
		TextView titleTv = (TextView) findViewById(R.id.tv_center);
		TextView rightTv = (TextView) findViewById(R.id.tv_right);
		//设置返回箭头
		if (null != leftClickLister && backView != null) {
			backView.setOnClickListener(leftClickLister);
		}else {
			backView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onBackPressed();
				}
			});
		}
		//设置左标题
		if (leftTv != null && !TextUtils.isEmpty(left)) {
			leftTv.setText(left);
			leftTv.setVisibility(View.VISIBLE);
		}

		//设置中间的标题
		if (titleTv != null && !TextUtils.isEmpty(title)) {
			titleTv.setText(title);
			titleTv.setVisibility(View.VISIBLE);
		}

		//设置右边文字
		if (rightTv != null && !TextUtils.isEmpty(right)) {
			rightTv.setText(right);
			if (null != rightClickListener) {
				rightTv.setOnClickListener(rightClickListener);
			}
			rightTv.setVisibility(View.VISIBLE);
		}
	}

	public void initTitle(String title) {
		initTitle(null, null, title, null, null);
	}

	public void initTitle(String title, String right, View.OnClickListener rightClickListener) {
		initTitle(null, null, title, right, rightClickListener);
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
