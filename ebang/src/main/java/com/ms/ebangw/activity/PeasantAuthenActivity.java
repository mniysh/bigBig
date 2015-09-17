package com.ms.ebangw.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.ms.ebangw.R;
import com.ms.ebangw.fragment.PeasantBankFeagment;
import com.ms.ebangw.fragment.PeasantBaseFragment;

/**
 * 农民工认证的基本信息页面
 */
public class PeasantAuthenActivity extends BaseActivity implements OnClickListener, OnCheckedChangeListener {
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
		initTitle(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PeasantAuthenActivity.this.finish();
			}
		},"返回","农民工认证",null,null);
		
	}

	public void initView() {
		// TODO Auto-generated method stub
		fm=getFragmentManager();
		pBase=new PeasantBaseFragment();
		pBank=new PeasantBankFeagment();
		rGroup=(RadioGroup) findViewById(R.id.act_pea_rg);
		
		
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
