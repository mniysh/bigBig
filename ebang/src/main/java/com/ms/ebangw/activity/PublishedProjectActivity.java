package com.ms.ebangw.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ms.ebangw.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 已发布的不同状态的 工程 ：待通过  进行中  已结束
 *
 * @author wangkai
 */
public class PublishedProjectActivity extends BaseActivity {


    @Bind(R.id.btn_waitting_pass)
    RadioButton btnWaittingPass;
    @Bind(R.id.rb_underway)
    RadioButton rbUnderway;
    @Bind(R.id.btn_finished)
    RadioButton btnFinished;
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_published_project);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void initView() {
        initTitle(null, "返回", "已发布", null, null);
    }

    @Override
    public void initData() {

    }

}
