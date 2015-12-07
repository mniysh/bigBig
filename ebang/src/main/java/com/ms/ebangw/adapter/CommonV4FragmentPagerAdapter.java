package com.ms.ebangw.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 通用的ViewPager 与v4 Fragment的adater
 * User: WangKai(123940232@qq.com)
 * 2015-12-07 08:42
 */
public class CommonV4FragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;

    public CommonV4FragmentPagerAdapter(android.support.v4.app.FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
