package com.ms.ebangw.center.worker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.activity.PeopleCategoryActivity;
import com.ms.ebangw.commons.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 工人中心-->邀请我的-->工长或劳务公司列表
 * @author wangkai
 */
public class InviteMineUserActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.tv_headman)
    TextView tvHeadman;
    @Bind(R.id.tv_labour_company)
    TextView tvLabourCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_mine_user);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void initView() {
        tvHeadman.setOnClickListener(this);
        tvLabourCompany.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
//        bundle.putString(Constants.KEY_PROJECT_TYPE, ProjectStatusActivity.TYPE_INVITE);
        switch (v.getId()) {
            case R.id.tv_headman:
                bundle.putString(Constants.KEY_CATEGORY_LIST_TYPE, PeopleCategoryActivity.LIST_TYPE_HEADMAN);
                break;

            case R.id.tv_labour_company:
                bundle.putString(Constants.KEY_CATEGORY_LIST_TYPE, PeopleCategoryActivity.LIST_TYPE_COMPANY);
                break;
        }
        Intent intent = new Intent(this, PeopleCategoryActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
