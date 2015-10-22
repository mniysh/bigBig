package com.ms.ebangw.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.NextPageActivity;
import com.ms.ebangw.bean.WorkType;
import com.ms.ebangw.view.ViewHolder;

import java.util.List;

/**
 * 适配二级页面的gridview
 * Created by admin on 2015/9/20.
 */
public class GridAdapter extends BaseAdapter {
    private List<WorkType> datas;
    private NextPageActivity act;

    public GridAdapter(List<WorkType> datas, NextPageActivity act) {
        this.datas = datas;
        this.act = act;
    }

    @Override
    public int getCount() {
        if(null==datas){
            return 0;
        }else{
            return datas.size();

        }
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
            convertView=act.getLayoutInflater().inflate(R.layout.item_gr,null);

        }
        TextView bItem= ViewHolder.get(convertView,R.id.bt_pre);
        bItem.setText(datas.get(position).getName());

        return convertView;
    }
}
