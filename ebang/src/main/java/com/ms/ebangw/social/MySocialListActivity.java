package com.ms.ebangw.social;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.fragment.SocialPartyStatusFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 社区--我的列表
 *
 * @author wangkai
 */
public class MySocialListActivity extends BaseActivity {

    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_social_list);
        ButterKnife.bind(this);

        initView();
        initData();
    }



    @Override
    public void initView() {
        initTitle(null, "返回", "我的列表", null, null);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < group.getChildCount(); i++) {
                    if (group.getChildAt(i).getId() == checkedId) {
                        viewPager.setCurrentItem(i);
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void initData() {
        List<Fragment> list = new ArrayList<>();

        SocialPartyStatusFragment fragmentAll = SocialPartyStatusFragment.newInstance(null);
        SocialPartyStatusFragment fragmentUnderWay = SocialPartyStatusFragment.newInstance
            (SocialPartyStatusFragment.PARTY_STATUS_UNDERWAY);
        SocialPartyStatusFragment fragmentUnderReview = SocialPartyStatusFragment.newInstance
            (SocialPartyStatusFragment.PARTY_STATUS_UNDER_REVIEW);
        SocialPartyStatusFragment fragmentComplete = SocialPartyStatusFragment.newInstance
            (SocialPartyStatusFragment.PARTY_STATUS_COMPLETE);

        list.add(fragmentAll);
        list.add(fragmentUnderWay);
        list.add(fragmentUnderReview);
        list.add(fragmentComplete);

        PartyStatusAdapter adapter = new PartyStatusAdapter(getFragmentManager(), list);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(position);
                radioButton.toggle();
            }
        });
        RadioButton radioButton = (RadioButton) radioGroup.getChildAt(0);
        radioButton.toggle();

    }

    private class PartyStatusAdapter extends FragmentPagerAdapter{
        private List<Fragment> list;

        public PartyStatusAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}
