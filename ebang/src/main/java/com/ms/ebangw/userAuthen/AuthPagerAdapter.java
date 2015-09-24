package com.ms.ebangw.userAuthen;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 用户身份验证
 * User: WangKai(123940232@qq.com)
 * 2015-09-24 14:34
 */
public class AuthPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> list;


    public AuthPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
