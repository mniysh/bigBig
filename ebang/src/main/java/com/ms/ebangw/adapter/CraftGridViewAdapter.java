package com.ms.ebangw.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.WorkType;

import java.util.List;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-09-26 16:21
 */
public class CraftGridViewAdapter extends BaseAdapter{

    private List<WorkType> list;

    public CraftGridViewAdapter(List<WorkType> list) {
        this.list = list;
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

        CheckBox cb = (CheckBox) View.inflate(parent.getContext(), R.layout
            .layout_craft_gridview_item, null);
        cb.setText(list.get(position).getName());

        return cb;
    }
}
