package com.ms.ebangw.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.ms.ebangw.R;
import com.ms.ebangw.fragment.ManagerBankFragment;
import com.ms.ebangw.fragment.ManagerBaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 工长认证的基本信息页面
 */
public class ManagerAuthenActivity extends BaseActivity implements OnCheckedChangeListener {
	@Bind(R.id.act_pea_submit)
	Button bBack;

	@OnClick(R.id.act_pea_submit)
	public void back(){
		startActivity(new Intent(this, CertificationManagerActivity.class));
		this.finish();
	}

	private RadioGroup rGroup;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_managerauthen);
		ButterKnife.bind(this);
		initTitle(null,"返回","工长认证",null,null);
		initView();
		initViewOper();
	}

	private void initViewOper() {
		// TODO Auto-generated method stub
		rGroup.setOnCheckedChangeListener(this);

	}

	public void initView() {
		// TODO Auto-generated method stub
		rGroup=(RadioGroup) findViewById(R.id.act_mana_rg);
		
	}

	@Override
	public void initData() {

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {
		case R.id.act_mana_rg:
			break;

		default:
			break;
		}
	}
}
