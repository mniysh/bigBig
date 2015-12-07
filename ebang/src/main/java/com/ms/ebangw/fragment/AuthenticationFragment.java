package com.ms.ebangw.fragment;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.userAuthen.developers.DevelopersAuthenActivity;
import com.ms.ebangw.userAuthen.headman.HeadmanAuthenActivity;
import com.ms.ebangw.userAuthen.investor.InvestorAuthenActivity;
import com.ms.ebangw.userAuthen.labourCompany.LabourCompanyAuthenActivity;
import com.ms.ebangw.userAuthen.worker.WorkerAuthenActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 认证的页面
 *
 * @author admin
 */
public class AuthenticationFragment extends BaseFragment implements OnClickListener {
    @Bind(R.id.btn_investor)
    TextView btnInvestor;
    @Bind(R.id.btn_worker)
    TextView btnWorker;
    @Bind(R.id.btn_headman)
    TextView btnHeadman;
    @Bind(R.id.btn_developers)
    TextView btnDevelopers;
    @Bind(R.id.btn_labour_company)
    TextView btnLabourCompany;
    private FragmentManager fm;

    private View mContentView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.frag_authen, null);
        ButterKnife.bind(this, mContentView);
        initView();
        initData();
        return mContentView;
    }


    public void initView() {
        fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.fl_head_info, HeadInfoFragment.newInstance("", "")).commit();

          btnInvestor.setOnClickListener(this);
        btnWorker.setOnClickListener(this);
        btnHeadman.setOnClickListener(this);
        btnDevelopers.setOnClickListener(this);
        btnLabourCompany.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {    ////worker(工人)/headman(工头)/developers(开发商)/investor(个人)

        Intent intent;
        switch (v.getId()) {

            //个人认证
            case R.id.btn_investor:
                intent = new Intent(mActivity, InvestorAuthenActivity.class);
                break;
            case R.id.btn_worker:    //工人
                intent = new Intent(mActivity, WorkerAuthenActivity.class);
                break;
            case R.id.btn_headman:    //工头
                intent = new Intent(mActivity, HeadmanAuthenActivity.class);
                break;
            case R.id.btn_developers:    //开发商
                intent = new Intent(mActivity, DevelopersAuthenActivity.class);
                break;
            case R.id.btn_labour_company:    //劳务公司
                intent = new Intent(mActivity, LabourCompanyAuthenActivity.class);
                break;

            default:
                intent = new Intent(mActivity, InvestorAuthenActivity.class);
                break;
        }

        startActivity(intent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

