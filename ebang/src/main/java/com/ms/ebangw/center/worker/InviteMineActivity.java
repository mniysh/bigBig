package com.ms.ebangw.center.worker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.activity.ProjectStatusActivity;
import com.ms.ebangw.commons.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InviteMineActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.tv_agree)
    TextView tvAgree;
    @Bind(R.id.tv_invite)
    TextView tvInvite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_mine);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void initView() {
        initTitle(null, "返回", "邀请我的", null, null);
        tvInvite.setOnClickListener(this);
        tvAgree.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_PROJECT_TYPE, ProjectStatusActivity.TYPE_INVITE);
        switch (v.getId()) {
            case R.id.tv_invite:
                bundle.putString(Constants.KEY_PROJECT_TYPE_INVITE, ProjectStatusActivity.INVITE_TYPE_INVITE);
                break;

            case R.id.tv_agree:
                bundle.putString(Constants.KEY_PROJECT_TYPE_INVITE, ProjectStatusActivity.INVITE_TYPE_AGREE);
                break;
        }
        Intent intent = new Intent(this, ProjectStatusActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
