package com.ms.ebangw.adapter;

import android.content.ClipData;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.HomeActivity;
import com.ms.ebangw.bean.WorkType;
import com.ms.ebangw.view.ViewHolder;

import java.util.List;

/**
 * 主页服务的gridview的adapter
 * Created by admin on 2015/9/24.
 */
public class ServiceGridViewAdapter extends BaseAdapter {
    private HomeActivity act;
    private List<WorkType> datas;

    public ServiceGridViewAdapter(HomeActivity act, List<WorkType> datas) {
        this.act = act;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        if(datas==null){
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
        if(convertView==null){
            convertView=act.getLayoutInflater().inflate(R.layout.item_service_gridview,null);
        }
        TextView tItem= ViewHolder.get(convertView,R.id.tv_item);
        tItem.setText(datas.get(position).getName());

        return convertView;
    }
}
