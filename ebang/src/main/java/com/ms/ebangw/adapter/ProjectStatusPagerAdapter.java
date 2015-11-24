package com.ms.ebangw.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.TextView;

import com.ms.ebangw.R;

import java.util.List;

/**
 * 工程状态Viewpager
 * User: WangKai(123940232@qq.com)
 * 2015-11-11 10:53
 */
public class ProjectStatusPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Fragment> list;
    private String [] titles ;

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

    public void setContext(Context context) {
        this.context = context;
    }

    public View getTabView(int position) {
        TextView textView = new TextView(context);
//        textView.setText(titles[position]);
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams
//            .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        textView.setLayoutParams(params);
        textView.setText("审核");
        textView.setTextColor(context.getResources().getColorStateList(R.color.project_status_tab_text));
        textView.setBackgroundResource(R.drawable.project_status_tab_selector);
        return textView;

    }
}
