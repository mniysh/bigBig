package com.ms.ebangw.adapter;



import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.PorterDuff;
import android.support.v13.app.FragmentPagerAdapter;


import java.util.List;

/**
 * 服务的pageradapter
 * Created by admin on 2015/9/24.
 */
public class ServicePagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> datas;

    public ServicePagerAdapter(FragmentManager fm,List<Fragment> datas) {
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
