package com.ms.ebangw.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class HomeViewpagerAdapter extends PagerAdapter {
	
	private List<View> mData;
    public HomeViewpagerAdapter(List<View> mData) {
        this.mData = mData;
    }
    @Override
    public int getCount() {
        return mData.size();
    }
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = mData.get(position);
        container.addView(v);
        return v;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mData.get(position));
        
        
    }
	
}
