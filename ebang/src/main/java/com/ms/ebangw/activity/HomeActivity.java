package com.ms.ebangw.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.fragment.AuthenticationFragment;
import com.ms.ebangw.fragment.FoundFragment;
import com.ms.ebangw.fragment.HomeFragment;
import com.ms.ebangw.fragment.ReleaseFragment;


/**
 * Home主页面
 * @author admin
 *
 */
public class HomeActivity extends BaseActivity implements OnClickListener {
	private RadioGroup act_home_radiog;
	private FragmentManager fm;

	private FoundFragment foundFragment;
	private ReleaseFragment releasefragment;
	private LinearLayout lin_main;
	private SharedPreferences sp;
	private AuthenticationFragment authenticationfragment;

	private int[] iv_chick={R.drawable.homepager_click,R.drawable.foundpager_click,R.drawable.releasepager_clic,R.drawable.serivicepager_click,R.drawable.mainpager_click};
	private int[] iv_unchick={R.drawable.homepager_unclick,R.drawable.foundpager_unclick,R.drawable.releasepager_unclick,R.drawable.serivicepager_unclick,R.drawable.mainpager_unclick};
	private LinearLayout[] lin_click=new LinearLayout[5];


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initView();
		fm.beginTransaction().replace(R.id.act_home_frag,new HomeFragment()).commit();
		initFrament();
		operation();
	}

	public void initView() {
		// TODO Auto-generated method stub

		lin_click[0]=(LinearLayout) findViewById(R.id.act_home_homebutn);
		lin_click[1]=(LinearLayout) findViewById(R.id.act_home_foundbutn);
		lin_click[2]=(LinearLayout) findViewById(R.id.act_home_releasebut);
		lin_click[3]=(LinearLayout) findViewById(R.id.act_home_serivicebut);
		lin_click[4]=(LinearLayout) findViewById(R.id.act_home_mainbut);
		fm = getFragmentManager();




	}

	@Override
	public void initData() {

	}

	private void initFrament() {
		// TODO Auto-generated method stub
		foundFragment=new FoundFragment();
		releasefragment=new ReleaseFragment();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(null != sp && sp.getInt("flag_int", -1)==1){
			lin_click[4].performClick();
		}
	}

	private void operation() {
		fm = getFragmentManager();
		for (int i = 0; i < lin_click.length; i++) {
			lin_click[i].setOnClickListener(this);
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.act_home_homebutn:
				changeState(0);
				fm.beginTransaction().replace(R.id.act_home_frag,new HomeFragment()).commit();
				break;
			case R.id.act_home_foundbutn:
				changeState(1);
				fm.beginTransaction().replace(R.id.act_home_frag,foundFragment).commit();
				break;
			case R.id.act_home_releasebut:
				changeState(2);
				fm.beginTransaction().replace(R.id.act_home_frag,releasefragment).commit();
				break;
			case R.id.act_home_serivicebut:
				changeState(3);
				break;
			case R.id.act_home_mainbut:
				changeState(4);
				//判断现在是否是注册完的状态，没注册就跳转到注册的页面,暂时先放放
				sp=getSharedPreferences("shuju", MODE_PRIVATE);
				Log.i("aaa", "flag_int的值是"+sp.getInt("flag_int", -1));
				if(null == sp && sp.getInt("flag_int", -1)==1){
					authenticationfragment=new AuthenticationFragment();
					fm.beginTransaction().replace(R.id.act_home_frag, authenticationfragment).commit();
				}else{

					startActivity(new Intent(this,LoginActivity.class));
				}
				break;
			default:
				break;
		}
	}

	private void changeState(int a) {
		// TODO Auto-generated method stub
		for (int i = 0; i < lin_click.length; i++) {
			if(i==a){
				((ImageView)(lin_click[i].getChildAt(0))).setImageResource(iv_chick[i]);
				((TextView)(lin_click[i].getChildAt(1))).setTextColor(Color.parseColor("#0075AA"));
			}else{
				((ImageView)(lin_click[i].getChildAt(0))).setImageResource(iv_unchick[i]);
				((TextView)(lin_click[i].getChildAt(1))).setTextColor(Color.parseColor("#626262"));

			}
		}
	}
}
