package com.ms.ebangw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ms.ebangw.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 帐户
 *
 * @author wangkai
 */
public class AccountActivity extends BaseActivity {


    @Bind(R.id.tv_year)
    TextView tvYear;
    @Bind(R.id.tv_month)
    TextView tvMonth;
    @Bind(R.id.rl_date_select)
    RelativeLayout rlDateSelect;
    @Bind(R.id.tv_expend)
    TextView tvExpend;
    @Bind(R.id.tv_income)
    TextView tvIncome;
    @Bind(R.id.ptr)
    PullToRefreshListView ptr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
        initView();
        initData();
    }


    @Override
    public void initView() {
        initTitle(null, "返回", "账单", "交易明细", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, AccountDetailActivity.class);
                startActivity(intent);
            }
        });

        rlDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateSelectPopup();
            }
        });
    }

    private void showDateSelectPopup() {


    }

    @Override
    public void initData() {

    }
}
