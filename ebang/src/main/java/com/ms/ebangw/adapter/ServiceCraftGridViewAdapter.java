package com.ms.ebangw.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.HomeActivity;
import com.ms.ebangw.bean.WorkType;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.view.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务的gridview的adapter
 * Created by admin on 2015/10/14.
 */
public class ServiceCraftGridViewAdapter extends BaseAdapter {
    private List<WorkType> list;
    private FragmentManager fm;
    private List<WorkType> selectedWorkType;
    private HomeActivity activity;

    public ServiceCraftGridViewAdapter(List<WorkType> list, HomeActivity activity) {
        this.list = list;

        selectedWorkType = new ArrayList<WorkType>();
        this.activity = activity;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(R.layout.item_service_gridview, null);
        }
        TextView tItem = ViewHolder.get(convertView, R.id.tv_item);
        WorkType workType = list.get(position);
        tItem.setText(workType.getName());
        tItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.show("被点击");
            }
        });

        return convertView;
    }
}
