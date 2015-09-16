package com.ms.ebangw.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.ms.ebangw.R;
import com.ms.ebangw.fragment.FactoryAutherCompleteFragment;
import com.ms.ebangw.fragment.FactoryAutherFragment;


public class FactoryAuthenActivity extends BaseActivity implements OnCheckedChangeListener {
	private FragmentManager fManager;
	private FactoryAutherFragment fFactory;
	private FactoryAutherCompleteFragment fComplete;
	private RadioGroup rGroup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_factoryauthen);
		initView();
		initViewOper();
		
	}

	private void initViewOper() {
		// TODO Auto-generated method stub
		fManager.beginTransaction().replace(R.id.act_facauthen_frame, fFactory).commit();
		rGroup.setOnCheckedChangeListener(this);
		initTitle(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		},"返回","企业认证",null,null);
	}

	public void initView() {
		// TODO Auto-generated method stub
		rGroup=(RadioGroup) findViewById(R.id.act_fac_rg);
		fManager=getFragmentManager();
		fFactory=new FactoryAutherFragment();
		fComplete=new FactoryAutherCompleteFragment();
		
	}

	@Override
	public void initData() {

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {
		case R.id.act_fac_rb_base:
			fManager.beginTransaction().replace(R.id.act_facauthen_frame, fFactory).commit();
			break;
		case R.id.act_fac_rb_bank:
			fManager.beginTransaction().replace(R.id.act_facauthen_frame, fComplete).commit();
			break;
		default:
			break;
		}
	}
}
