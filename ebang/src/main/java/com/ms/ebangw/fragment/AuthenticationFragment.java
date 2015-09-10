package com.ms.ebangw.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.FactoryAuthenActivity;
import com.ms.ebangw.activity.HomeActivity;
import com.ms.ebangw.activity.ManagerAuthenActivity;
import com.ms.ebangw.activity.PeasantAuthenActivity;
import com.ms.ebangw.activity.SelfAuthenticationActivity;

/**
 * 认证的页面
 * @author admin
 *
 */
public class AuthenticationFragment extends BaseFragment implements OnClickListener {

	private HomeActivity act;
	private Button but_self,but_worker,but_foreman,but_factory;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		act=(HomeActivity) activity;
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
							 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.frag_authen, null);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
		initViewOper();


	}
	private void initViewOper() {
		// TODO Auto-generated method stub
		but_self.setOnClickListener(this);
		but_worker.setOnClickListener(this);
		but_foreman.setOnClickListener(this);
		but_factory.setOnClickListener(this);

	}
	private void initView() {
		// TODO Auto-generated method stub
		but_self=(Button) findviewbyid(R.id.frag_authen_but_self);
		but_worker=(Button) findviewbyid(R.id.frag_authen_but_worker);
		but_foreman=(Button) findviewbyid(R.id.frag_authen_but_foreman);
		but_factory=(Button) findviewbyid(R.id.frag_authen_but_factory);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			//个人认证
			case R.id.frag_authen_but_self:
				startActivity(new Intent(act,SelfAuthenticationActivity.class));
				break;
			case R.id.frag_authen_but_worker:
				startActivity(new Intent(act,PeasantAuthenActivity.class));
				break;
			case R.id.frag_authen_but_foreman:
				startActivity(new Intent(act,ManagerAuthenActivity.class));
				break;
			case R.id.frag_authen_but_factory:
				startActivity(new Intent(act,FactoryAuthenActivity.class));
				break;

			default:
				break;
		}
	}
}

