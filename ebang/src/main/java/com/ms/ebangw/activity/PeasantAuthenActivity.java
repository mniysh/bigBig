package com.ms.ebangw.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.ms.ebangw.R;
import com.ms.ebangw.fragment.PeasantBankFeagment;
import com.ms.ebangw.fragment.PeasantBaseFragment;


public class PeasantAuthenActivity extends FragmentActivity implements OnClickListener, OnCheckedChangeListener {
	private FragmentManager fm;
	private PeasantBaseFragment pBase;
	private PeasantBankFeagment pBank;
	private RadioGroup rGroup;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_peasant_authen);
		initView();
		initViewOper();
		
	}

	private void initViewOper() {
		// TODO Auto-generated method stub
		fm.beginTransaction().replace(R.id.act_pea_frame, pBase).commit();
		rGroup.setOnCheckedChangeListener(this);
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		fm=getSupportFragmentManager();
		pBase=new PeasantBaseFragment();
		pBank=new PeasantBankFeagment();
		rGroup=(RadioGroup) findViewById(R.id.act_pea_rg);
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {
		case R.id.act_pea_rb_base:
			fm.beginTransaction().replace(R.id.act_pea_frame, pBase).commit();
			break;
		case R.id.act_pea_rb_bank:
			fm.beginTransaction().replace(R.id.act_pea_frame, pBank).commit();
			break;

		default:
			break;
		}
	}
}
