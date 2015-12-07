package com.ms.ebangw.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.SystemMessage;
import com.ms.ebangw.commons.Constants;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 系统消息
 * User: WangKai(123940232@qq.com)
 * 2015-12-07 17:16
 */
public class SystemMessageAdapter extends BaseAdapter {
    private List<SystemMessage> list;

    public SystemMessageAdapter(List<SystemMessage> list) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_message, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        SystemMessage message = list.get(position);

        String is_read = message.getIs_read();
        if (TextUtils.equals(is_read, "1")) {
            holder.ivRedDot.setVisibility(View.INVISIBLE);
        }else {
            holder.ivRedDot.setVisibility(View.VISIBLE);
        }

        holder.tvTitle.setText(message.getTitle());
        holder.tvContent.setText(message.getContent());
        holder.tvTime.setText(message.getCreated_at());
        convertView.setTag(Constants.KEY_SYS_MESSAGE, message);
        return convertView;
    }

    public List<SystemMessage> getList() {
        return list;
    }

    public void setList(List<SystemMessage> list) {
        this.list = list;
    }


    static class ViewHolder {
        @Bind(R.id.iv_red_dot)
        ImageView ivRedDot;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
