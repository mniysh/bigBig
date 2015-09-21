package com.ms.ebangw.adapter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.List;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-09-21 10:55
 */
public class VerifyUserPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> list;

    public VerifyUserPagerAdapter(FragmentManager fm, List<Fragment> list) {
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
