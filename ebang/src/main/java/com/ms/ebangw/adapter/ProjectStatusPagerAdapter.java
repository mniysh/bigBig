package com.ms.ebangw.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 工程状态Viewpager
 * User: WangKai(123940232@qq.com)
 * 2015-11-11 10:53
 */
public class ProjectStatusPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    public ProjectStatusPagerAdapter(FragmentManager fm, List<Fragment> list) {
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
