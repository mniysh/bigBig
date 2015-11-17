package com.ms.ebangw.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ms.ebangw.R;
import com.ms.ebangw.adapter.ProjectStatusPagerAdapter;
import com.ms.ebangw.fragment.PublishedProjectStatusFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 已发布的不同状态的 工程 ：待审核，待通过  进行中  已结束
 *
 * @author wangkai
 */
public class PublishedProjectActivity extends BaseActivity {


    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.btn_waiting)
    RadioButton btnWaiting;
    @Bind(R.id.rb_execute)
    RadioButton rbExecute;
    @Bind(R.id.btn_complete)
    RadioButton btnComplete;
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    @Bind(R.id.btn_waiting_audit)
    RadioButton btnWaitingAudit;

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

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btn_waiting_audit:
                        viewPager.setCurrentItem(0);
                        break;

                    case R.id.btn_waiting:
                        viewPager.setCurrentItem(1);
                        break;

                    case R.id.rb_execute:
                        viewPager.setCurrentItem(2);
                        break;

                    case R.id.btn_complete:
                        viewPager.setCurrentItem(3);
                        break;


                }
            }
        });
    }

    @Override
    public void initData() {
        PublishedProjectStatusFragment auditFragment = PublishedProjectStatusFragment.newInstance(PublishedProjectStatusFragment.AUDIT);
        PublishedProjectStatusFragment waitingFragment = PublishedProjectStatusFragment.newInstance(PublishedProjectStatusFragment.WAITING);
        PublishedProjectStatusFragment executeFragment = PublishedProjectStatusFragment.newInstance(PublishedProjectStatusFragment.EXECUTE);
        PublishedProjectStatusFragment completeFragment = PublishedProjectStatusFragment.newInstance(PublishedProjectStatusFragment.COMPLETE);

        List<Fragment> list = new ArrayList<>();
        list.add(auditFragment);
        list.add(waitingFragment);
        list.add(executeFragment);
        list.add(completeFragment);

        ProjectStatusPagerAdapter adapter = new ProjectStatusPagerAdapter(getFragmentManager(), list);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(position);
                radioButton.toggle();
            }
        });
        btnWaiting.toggle();
    }

}
