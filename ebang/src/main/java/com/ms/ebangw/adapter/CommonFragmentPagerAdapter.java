package com.ms.ebangw.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 通用的ViewPager 与 Fragment的adater
 * User: WangKai(123940232@qq.com)
 * 2015-12-07 08:42
 */
public class CommonFragmentPagerAdapter  extends FragmentPagerAdapter{
    private List<Fragment> list;

    public CommonFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
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
