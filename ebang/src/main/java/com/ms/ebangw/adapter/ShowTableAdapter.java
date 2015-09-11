package com.ms.ebangw.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.ShowActivity;
import com.ms.ebangw.view.ViewHolder;

import java.util.List;

/**
 *
 * 抢单列表的适配器
 * Created by admin on 2015/9/11.
 */
public class ShowTableAdapter  extends BaseAdapter{
    private ShowActivity act;
    private List<String[]> datas;

    public ShowTableAdapter(ShowActivity act, List<String[]> datas) {
        this.act = act;
        this.datas = datas;
    }

    @Override
    public int getCount() {

        if (datas == null) {
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
        if (convertView == null) {
            convertView=act.getLayoutInflater().inflate(R.layout.item_listview_qiangda_state,null);
        }
        TextView tName= ViewHolder.get(convertView,R.id.listview_name);
        TextView tTime=ViewHolder.get(convertView,R.id.listview_time);
        TextView tState=ViewHolder.get(convertView,R.id.listview_state);

        tName.setText(datas.get(position)[0]);
        tTime.setText(datas.get(position)[1]);
        tState.setText(datas.get(position)[2]);


        return convertView;
    }
}
