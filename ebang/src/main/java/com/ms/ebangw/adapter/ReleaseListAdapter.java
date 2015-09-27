package com.ms.ebangw.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.HomeActivity;
import com.ms.ebangw.bean.Craft;
import com.ms.ebangw.bean.ReleaseWorkType;
import com.ms.ebangw.bean.WorkType;
import com.ms.ebangw.fragment.ReleaseFragment;
import com.ms.ebangw.view.ViewHolder;

import java.util.List;

/**
 *
 * Created by admin on 2015/9/27.
 */
public class ReleaseListAdapter extends BaseAdapter {
    private HomeActivity act;

    private List<WorkType> datas;

    public ReleaseListAdapter(HomeActivity act, List<WorkType> datas) {
        this.act = act;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        if( datas == null){
            return 0;
        }

        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = act.getLayoutInflater().inflate(R.layout.itemreleaselist , null);
        }
        TextView name = ViewHolder.get(convertView, R.id.tv_name );


        return convertView;
    }
}
