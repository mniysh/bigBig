package com.ms.ebangw.activity;

import android.app.Application;
import android.app.DialogFragment;
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
import com.ms.ebangw.utils.T;

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
	public void showProgressDialog(String message) {
		if (null != mLoadingDialog) {
			mLoadingDialog.dismiss();
		}
		if (null == mLoadingDialog) {
			mLoadingDialog = LoadingDialog.newInstance(message) ;
		}
		mLoadingDialog.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
		mLoadingDialog.show(getFragmentManager(), TAG);
	}

	public void showProgressDialog() {
		showProgressDialog(null);
	}

	/**
	 * 关闭进度对话框
	 */
	public void dismissLoadingDialog() {
		if (null != mLoadingDialog && null != mLoadingDialog.getActivity()&& mLoadingDialog.isVisible()) {
			mLoadingDialog.dismiss();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (null != mLoadingDialog) {
			mLoadingDialog.dismiss();
		}
		MyApplication.unDestroyActivityList.remove(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (null != mLoadingDialog) {
			mLoadingDialog.dismiss();
		}
	}

	/**
	 * 双击退出
	 */
	public void exitApp() {

		if ((System.currentTimeMillis() - exitTime) > 2000) {
            T.show("再按一次退出程序");
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
