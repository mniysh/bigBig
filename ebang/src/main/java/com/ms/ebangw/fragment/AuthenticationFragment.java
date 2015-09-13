package com.ms.ebangw.fragment;

import android.content.Context;
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
	private View mContentView;


	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		act=(HomeActivity) context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
							 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.frag_authen, null);
		return mContentView;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initViewOper();


	}
	private void initViewOper() {
		but_self.setOnClickListener(this);
		but_worker.setOnClickListener(this);
		but_foreman.setOnClickListener(this);
		but_factory.setOnClickListener(this);

	}
	private void initView() {
		but_self=(Button) mContentView.findViewById(R.id.frag_authen_but_self);
		but_worker=(Button) mContentView.findViewById(R.id.frag_authen_but_worker);
		but_foreman=(Button) mContentView.findViewById(R.id.frag_authen_but_foreman);
		but_factory=(Button) mContentView.findViewById(R.id.frag_authen_but_factory);
	}
	@Override
	public void onClick(View v) {
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

