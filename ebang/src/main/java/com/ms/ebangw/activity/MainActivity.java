package com.ms.ebangw.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.ms.ebangw.R;
import com.ms.ebangw.utils.SysUtils;

/**
 * 用户打开应用进入的第一个页面-----欢迎页面
 */
public class MainActivity extends BaseActivity implements OnClickListener, AnimationListener {
	//private ImageView iv01,iv02;
	private SharedPreferences sp;
	private LinearLayout lin;
	private AlphaAnimation animation;


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
		initViewOper();
	}
	private void getDatas() {

	}
	private void initViewOper() {
		// TODO Auto-generated method stub
		//两个图片的点击事件
//		iv01.setOnClickListener(this);
//		iv02.setOnClickListener(this);
		//启动动画
		lin.setAnimation(animation);
		animation.setAnimationListener(this);
	}
	private void initView() {
		// TODO Auto-generated method stub
//		iv01=(ImageView) findViewById(R.id.act_main_iv01);
//		iv02=(ImageView) findViewById(R.id.act_main_iv02);
		lin=(LinearLayout) findViewById(R.id.main_Lin);
		animation=(AlphaAnimation) AnimationUtils.loadAnimation(this, R.anim.alpha_shouye);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.act_main_iv01:
			case R.id.act_main_iv02:
				if(SysUtils.check(this)){
//				startActivity(new Intent(this,HomeActivity.class));
					startActivity(new Intent(this,LoginActivity.class));
					finish();
				}else{
					toast("当前设备网络不稳，请检查是否接入网络。");
				}
				break;
			default:
				break;
		}
	}
	//=================================
	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		startActivity(new Intent(this,HomeActivity.class));
		this.finish();
	}
	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}
	//=========================================

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//			exitApp();
//		}
//		return false;
//	}


}
