package com.ms.ebangw.activity;

import android.os.Bundle;

import com.ms.ebangw.R;
import com.ms.ebangw.fragment.LotteryFragment;

/**
 * 抽奖
 * @author wangkai
 */
public class LotteryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);
        initView();
        initData();

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        getFragmentManager().beginTransaction().replace(android.R.id.content, new LotteryFragment
            ()).commit();
    }
}
