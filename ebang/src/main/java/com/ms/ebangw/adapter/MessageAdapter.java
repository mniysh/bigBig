package com.ms.ebangw.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.MessageCenterActivit;
import com.ms.ebangw.bean.Message;
import com.ms.ebangw.view.ViewHolder;

import java.util.List;

/**
 *
 * Created by admin on 2015/9/22.
 */
public class MessageAdapter extends BaseAdapter{
    private List<Message> datas;
    private TextView tTitle;
    private TextView tTime;
    private TextView tContent;
    private TextView tState;
    private ImageView iHean;
    private MessageCenterActivit act;

    public MessageAdapter(List<Message> datas, MessageCenterActivit act) {
        this.datas = datas;
        this.act = act;
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
            convertView=act.getLayoutInflater().inflate(R.layout.item_message,null);

        }
        tTitle= ViewHolder.get(convertView,R.id.tv_title);
        tTime=ViewHolder.get(convertView,R.id.tv_time);
        tContent=ViewHolder.get(convertView,R.id.tv_content);
        tState=ViewHolder.get(convertView,R.id.tv_state);
        iHean=ViewHolder.get(convertView,R.id.iv_head);
//        setdata(position);


        return convertView;
    }

    private void setdata(int p) {
        tTitle.setText(datas.get(p).getTitle());
        tTime.setText(datas.get(p).getTime());
        tContent.setText(datas.get(p).getContent());
        tState.setText(datas.get(p).getState());
        //还有一个头像的处理暂时不会



    }
}
