package com.ms.ebangw.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by admin on 2015/9/27.
 */
public class ReleaseFragmentAdapter extends FragmentPagerAdapter {


    private List<Fragment> datas;
    public ReleaseFragmentAdapter(FragmentManager fm, List<Fragment> datas ) {
        super(fm);
        this.datas=datas;
    }

    @Override
    public Fragment getItem(int position) {
        return datas.get(position);
    }

    @Override
    public int getCount() {
        return datas.size();
    }
}
