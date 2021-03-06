package com.ms.ebangw.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.ms.ebangw.R;
import com.ms.ebangw.fragment.ManagerBankFragment;
import com.ms.ebangw.fragment.ManagerBaseFragment;


public class ManagerAuthenActivity extends BaseActivity implements OnCheckedChangeListener {
	private FragmentManager fManager;
	private ManagerBankFragment mBank;
	private ManagerBaseFragment mBase;
	private RadioGroup rGroup;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_managerauthen);
		initView();
		initViewOper();
	}

	private void initViewOper() {
		// TODO Auto-generated method stub
		fManager.beginTransaction().replace(R.id.act_managerauthen_frame, mBase).commit();
		rGroup.setOnCheckedChangeListener(this);
		initTitle(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ManagerAuthenActivity.this.finish();
			}
		},"返回","工长认证",null,null);
	}

	public void initView() {
		// TODO Auto-generated method stub
		fManager=getFragmentManager();
		mBank=new ManagerBankFragment();
		mBase=new ManagerBaseFragment();
		rGroup=(RadioGroup) findViewById(R.id.act_mana_rg);
		
	}

	@Override
	public void initData() {

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {
		case R.id.act_mana_rb_base:
			fManager.beginTransaction().replace(R.id.act_managerauthen_frame, mBase).commit();
			break;
		case R.id.act_mana_rb_bank:
			fManager.beginTransaction().replace(R.id.act_managerauthen_frame, mBank).commit();
			break;

		default:
			break;
		}
	}
}
