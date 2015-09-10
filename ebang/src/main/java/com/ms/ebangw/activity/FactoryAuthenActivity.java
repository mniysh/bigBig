package com.ms.ebangw.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.ms.ebangw.R;
import com.ms.ebangw.fragment.FactoryAutherCompleteFragment;
import com.ms.ebangw.fragment.FactoryAutherFragment;


public class FactoryAuthenActivity extends FragmentActivity implements OnCheckedChangeListener {
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
	}

	private void initView() {
		// TODO Auto-generated method stub
		rGroup=(RadioGroup) findViewById(R.id.act_fac_rg);
		fManager=getSupportFragmentManager();
		fFactory=new FactoryAutherFragment();
		fComplete=new FactoryAutherCompleteFragment();
		
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
