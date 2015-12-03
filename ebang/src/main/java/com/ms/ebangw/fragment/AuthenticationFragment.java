package com.ms.ebangw.fragment;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.User;
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
    Button btnInvestor;
    @Bind(R.id.btn_worker)
    Button btnWorker;
    @Bind(R.id.btn_headman)
    Button btnHeadman;
    @Bind(R.id.btn_developers)
    Button btnDevelopers;
    @Bind(R.id.btn_labour_company)
    Button btnLabourCompany;
    private FragmentManager fm;

    private View mContentView;

    @Bind(R.id.tv_realName)
    TextView realNameTv;
    @Bind(R.id.tv_phone2)
    TextView phone2Tv;
    @Bind(R.id.tv_gender)
    TextView genderTv;
    @Bind(R.id.tv_native_place)
    TextView nativePlaceTv;
    @Bind(R.id.tv_work_type)
    TextView workTypeTv;
    @Bind(R.id.ll_authed)
    LinearLayout detailLayout;
    @Bind(R.id.ll_no_auth)
    LinearLayout noAuthLayout;
    @Bind(R.id.ll_workType)
    LinearLayout LWorkType;

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
        fm.beginTransaction().replace(R.id.fl_head_info, HeadInfoFragment.newInstance("", "")).commit();

          btnInvestor.setOnClickListener(this);
        btnWorker.setOnClickListener(this);
        btnHeadman.setOnClickListener(this);
        btnDevelopers.setOnClickListener(this);
        btnLabourCompany.setOnClickListener(this);

    }

    public void initCompletedUser() {
        noAuthLayout.setVisibility(View.GONE);
        detailLayout.setVisibility(View.VISIBLE);
        LWorkType.setVisibility(View.GONE);
        User user = getUser();
        String real_name = user.getReal_name();
        String gender = user.getGender();
        String phone = user.getPhone();
        String area = user.getArea();
        String craft = user.getCraft();
        realNameTv.setText(real_name);
        genderTv.setText(gender);
        phone2Tv.setText(phone);
        nativePlaceTv.setText(area);

        if (TextUtils.equals(craft, "null") || TextUtils.isEmpty(craft) || TextUtils.equals(craft,
            "Null")) {
            LWorkType.setVisibility(View.GONE);

        } else {
            LWorkType.setVisibility(View.VISIBLE);
            workTypeTv.setText(craft);
        }
    }

    public void initNoAuthUser() {
        noAuthLayout.setVisibility(View.VISIBLE);
        detailLayout.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
        User user = getUser();
        String status = user.getStatus();        //认证状态
        switch (status) {
            case "guest":                        //未申请
                initNoAuthUser();
                break;
            case "complete":                //认证审核通过
                initCompletedUser();
                break;
        }
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

