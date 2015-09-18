package com.ms.ebangw.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.ms.ebangw.R;
import com.ms.ebangw.fragment.FactoryAutherCompleteFragment;
import com.ms.ebangw.fragment.FactoryAutherFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 开发商页面
 */

public class FactoryAuthenActivity extends BaseActivity implements OnCheckedChangeListener {

//	@Bind(R.id.iv_back)
//	ImageView iBack;
//	@OnClick(R.id.iv_back)
//	public void iBack(View v){
//		this.finish();
//	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_factoryauthen);
		ButterKnife.bind(this);
		initView();
		initViewOper();
		
	}

	private void initViewOper() {
		// TODO Auto-generated method stub
//		fManager.beginTransaction().replace(R.id.act_facauthen_frame, fFactory).commit();
//		rGroup.setOnCheckedChangeListener(this);
		initTitle(null,"返回","企业认证",null,null);
	}

	public void initView() {
		// TODO Auto-generated method stub
//		rGroup=(RadioGroup) findViewById(R.id.act_fac_rg);
//		fManager=getFragmentManager();
//		fFactory=new FactoryAutherFragment();
//		fComplete=new FactoryAutherCompleteFragment();
		
	}

	@Override
	public void initData() {

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {
//		case R.id.act_fac_rb_base:
//			fManager.beginTransaction().replace(R.id.act_facauthen_frame, fFactory).commit();
//			break;
//		case R.id.act_fac_rb_bank:
//			fManager.beginTransaction().replace(R.id.act_facauthen_frame, fComplete).commit();
//			break;
		default:
			break;
		}
	}
}
