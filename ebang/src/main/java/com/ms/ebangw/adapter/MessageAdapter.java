package com.ms.ebangw.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.SystemMessage;

import java.util.List;

/**
 *  系统消息
 * Created by admin on 2015/9/22.
 */
public class MessageAdapter extends BaseAdapter{
    private List<SystemMessage> list;

    public MessageAdapter(List<SystemMessage> list) {
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
        if(convertView==null){
            convertView = View.inflate(parent.getContext(), R.layout.item_message, null);

        }

        return convertView;
    }

}
