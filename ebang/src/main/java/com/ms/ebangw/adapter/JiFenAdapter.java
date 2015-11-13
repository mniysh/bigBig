package com.ms.ebangw.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.JiFen;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 积分Adapter
 * User: WangKai(123940232@qq.com)
 * 2015-11-12 09:09
 */
public class JiFenAdapter extends BaseAdapter {
    private Context context;
    private List<JiFen> list;

    public JiFenAdapter(Context context, List<JiFen> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<JiFen> list) {
        this.list = list;
    }

    public List<JiFen> getList() {
        return list;
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
        JiFen jiFen = list.get(position);
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.jifen_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String action_title = jiFen.getAction_title();
        String action_describe = jiFen.getAction_describe();
        String score_value = jiFen.getScore_value();
        String time = jiFen.getCreated_at();

        holder.tvTitle.setText(action_title);
        holder.tvDescription.setText(action_describe);
        holder.tvScore.setText(score_value);
        holder.tvTime.setText(time);

        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'jifen_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_description)
        TextView tvDescription;
        @Bind(R.id.tv_score)
        TextView tvScore;
        @Bind(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
