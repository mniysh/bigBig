package com.ms.ebangw.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;

import com.ms.ebangw.R;
import com.ms.ebangw.utils.DensityUtils;
import com.ms.ebangw.utils.NetUtils;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.view.Circle;

/**
 * 用户打开应用进入的第一个页面-----欢迎页面
 */
public class MainActivity extends BaseActivity implements OnClickListener, AnimationListener {
	//private ImageView iv01,iv02;
	private SharedPreferences sp;
	private LinearLayout lin;
//	private AlphaAnimation animation;
	private AnimatorSet animation;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();

		//登录之前判断用户是否注册，注册了就直接登录进来
		sp=getSharedPreferences("shuju", MODE_PRIVATE);
		Log.i("xxx", sp.getString("phoneNumber", "aa"));
//		if(sp.getString("phoneNumber", "aa")!=null&&!sp.getString("phoneNumber", "aa")
//				.equals("aa")&&sp.getString("password", "bb")!=null&&!sp
//				.getString("password", "bb").equals("bb")){
//			Log.i("aaa", "sp的内容能进来");
//			startActivity(new Intent(this,HomeActivity.class));
//			this.finish();
//		}

		getDatas();
//		initViewOper();
		startScanAnimation();
	}
	private void getDatas() {

	}
//	private void initViewOper() {
//		//两个图片的点击事件
////		iv01.setOnClickListener(this);
////		iv02.setOnClickListener(this);
//		//启动动画
//		lin.setAnimation(animation);
//		animation.setAnimationListener(this);
//	}
public void initView() {
//		iv01=(ImageView) findViewById(R.id.act_main_iv01);
//		iv02=(ImageView) findViewById(R.id.act_main_iv02);
		lin=(LinearLayout) findViewById(R.id.main_Lin);
//		animation=(AlphaAnimation) AnimationUtils.loadAnimation(this, R.anim.alpha_shouye);
	}

	@Override
	public void initData() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.act_main_iv01:
			case R.id.act_main_iv02:
				if(NetUtils.isConnected(this)){
//				startActivity(new Intent(this,HomeActivity.class));
					startActivity(new Intent(this,LoginActivity.class));
					finish();
				}else{
					T.show("当前设备网络不稳，请检查是否接入网络。");
				}
				break;
			default:
				break;
		}
	}
	//=================================
	@Override
	public void onAnimationStart(Animation animation) {

	}
	@Override
	public void onAnimationEnd(Animation animation) {
		startActivity(new Intent(this,HomeActivity.class));
		this.finish();
	}
	@Override
	public void onAnimationRepeat(Animation animation) {

	}
	//=========================================

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

	}


}
