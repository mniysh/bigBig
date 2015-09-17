package com.ms.ebangw.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.view.ViewHolder;

/**
 * Created by admin on 2015/9/16.
 */
public class SpinnerAdapter extends BaseAdapter {
    private String[] datas;


    public SpinnerAdapter(String[] datas) {
        this.datas = datas;

    }

    @Override
    public int getCount() {
        return datas.length;
    }

    @Override
    public Object getItem(int position) {
        return datas[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView=View.inflate(parent.getContext(),R.layout.spin_style,null);
        }
        TextView tItem= ViewHolder.get(convertView,R.id.spin_sytle_content);
        tItem.setText(datas[position]);
        L.d("xxx","数据源长度是"+datas.length);

        return convertView;
    }
    public void changeData(String[] strings){
        datas=strings;
    }

}
