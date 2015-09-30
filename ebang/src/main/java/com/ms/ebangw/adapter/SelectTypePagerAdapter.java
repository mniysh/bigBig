package com.ms.ebangw.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 发现页面的 pagerAdapter
 * User: WangKai(123940232@qq.com)
 * 2015-09-28 15:40
 */
public class SelectTypePagerAdapter extends FragmentPagerAdapter {
    List<Fragment> list;

    public SelectTypePagerAdapter(FragmentManager fm, List<Fragment> list) {
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
