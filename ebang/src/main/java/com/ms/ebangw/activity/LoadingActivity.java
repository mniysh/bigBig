package com.ms.ebangw.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.utils.DensityUtils;
import com.ms.ebangw.view.Circle;
import com.ms.ebangw.web.WebActivity;

import butterknife.ButterKnife;

/**
 * 用户打开应用进入的第一个页面-----欢迎页面
 */
public class LoadingActivity extends BaseActivity {
	private AnimatorSet animation;

	private Handler mHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		initView();
		initData();
	}


	@Override
	public void initView() {

	}

	@Override
	public void initData() {
		mHandler = new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				int what = msg.what;

				if (200 == what) {
					animation.cancel();
//					Intent intent = new Intent(LoadingActivity.this, HomeActivity.class);
//					startActivity(intent);
					User user = MyApplication.getInstance().getUser();
					if (null != user) {
						Intent lotteryIntent = new Intent(LoadingActivity.this, WebActivity.class);
						lotteryIntent.putExtra(Constants.KEY_URL, getString(R.string.url_lottery));
						startActivity(lotteryIntent);
					}else {
						startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
					}


					finish();
				}
				return false;
			}
		});

		startScanAnimation();
		Message message = mHandler.obtainMessage(100);
		mHandler.sendMessageDelayed(message, 3000);		//3秒后跳转到主页

	}

	/**
	 * 开始扫描动画
	 */
	private void startScanAnimation() {
		// 设置头像

		final Circle circle1 = (Circle) findViewById(R.id.circle1);
		final Circle circle2 = (Circle) findViewById(R.id.circle2);
		final Circle circle3 = (Circle) findViewById(R.id.circle3);

		// 计算起始半径和结束半径
		int startRadius = DensityUtils.dp2px(this, 36);
		int endRadius = DensityUtils.dp2px(this, 150);

		PropertyValuesHolder pAlpha = PropertyValuesHolder.ofFloat("alpha",
			1.0f, 0.0f);
		PropertyValuesHolder pRadius = PropertyValuesHolder.ofInt("radius",
			startRadius, endRadius);

		ObjectAnimator anim1 = ObjectAnimator.ofPropertyValuesHolder(circle1,
			pAlpha, pRadius).setDuration(2000);
		anim1.setRepeatMode(ValueAnimator.RESTART);
		anim1.setRepeatCount(Integer.MAX_VALUE);
		anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				circle1.postInvalidate();
				circle1.invalidate();
			}
		});

		PropertyValuesHolder pAlpha2 = PropertyValuesHolder.ofFloat("alpha",
			1f, 0f);
		PropertyValuesHolder pRadius2 = PropertyValuesHolder.ofInt("radius",
			startRadius, endRadius);

		ObjectAnimator anim2 = ObjectAnimator.ofPropertyValuesHolder(circle2,
			pAlpha2, pRadius2).setDuration(3000);
		anim2.setRepeatMode(ValueAnimator.RESTART);
		anim2.setRepeatCount(Integer.MAX_VALUE);
		anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				circle2.postInvalidate();
				circle2.invalidate();
			}
		});

		PropertyValuesHolder pAlpha3 = PropertyValuesHolder.ofFloat("alpha",
			1f, 0f);
		PropertyValuesHolder pRadius3 = PropertyValuesHolder.ofInt("radius",
			startRadius, endRadius);

		ObjectAnimator anim3 = ObjectAnimator.ofPropertyValuesHolder(circle3,
			pAlpha3, pRadius3).setDuration(3000);
		anim3.setRepeatMode(ValueAnimator.RESTART);
		anim3.setRepeatCount(Integer.MAX_VALUE);
		anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				circle3.postInvalidate();
				circle3.invalidate();
			}
		});

		animation = new AnimatorSet();

		// 动画2在动画1开始后0.8秒开始，动画3在动画2开始后0.8秒开始
		AnimatorSet s1 = new AnimatorSet();
		s1.play(anim1);
		AnimatorSet s2 = new AnimatorSet();
		s2.play(anim2).after(anim1).after(800);
		AnimatorSet s3 = new AnimatorSet();
		s3.play(anim3).after(anim2).after(800);

		animation.playTogether(s1, s2, s3);
		animation.start();
		mHandler.sendEmptyMessageDelayed(200, 2000);
	}


}
