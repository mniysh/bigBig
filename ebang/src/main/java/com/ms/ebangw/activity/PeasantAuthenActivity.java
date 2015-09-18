package com.ms.ebangw.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.ms.ebangw.R;
import com.ms.ebangw.fragment.PeasantBankFeagment;
import com.ms.ebangw.fragment.PeasantBaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 务工人认证的基本信息页面
 */
public class PeasantAuthenActivity extends BaseActivity implements OnClickListener, OnCheckedChangeListener {

	@Bind(R.id.bt_next)
	Button bNext;
	@OnClick(R.id.bt_next)
	public void next(){
		startActivity(new Intent(this,PeasantAuthenCardActivity.class));
		this.finish();
	}
	//private RadioGroup rGroup;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_peasant_authen);
		ButterKnife.bind(this);
		initView();
		initData();
		initViewOper();
		
	}

	private void initViewOper() {
		// TODO Auto-generated method stub
		//rGroup.setOnCheckedChangeListener(this);
		initTitle(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PeasantAuthenActivity.this.finish();
			}
		},"返回","务工认证",null,null);
		
	}

	public void initView() {
		// TODO Auto-generated method stub
		//rGroup=(RadioGroup) findViewById(R.id.act_pea_rg);

		
		
	}

	@Override
	public void initData() {

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {
//		case R.id.act_pea_rb_base:
//			break;
//		case R.id.act_pea_rb_bank:
//			break;

		default:
			break;
		}
	}
}
