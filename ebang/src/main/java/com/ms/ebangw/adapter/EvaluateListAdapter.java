package com.ms.ebangw.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.service.DataAccessUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-11-11 15:16
 */
public class EvaluateListAdapter extends BaseAdapter {

    private List<Evaluate> list;

    public EvaluateListAdapter(List<Evaluate> list) {
        this.list = list;
    }

    public void setList(List<Evaluate> list) {
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
        Evaluate evaluate = list.get(position);
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.evaluate_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String imageUrl = evaluate.getHead_image();
        String nick_name = evaluate.getNick_name();
        String content = evaluate.getContent();
        String time = evaluate.getCreated_at();

        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(parent.getContext()).load(DataAccessUtil.getImageUrl(imageUrl))
                .placeholder(R.drawable.head).
                into(holder.head);
        }else {
            holder.head.setImageResource(R.drawable.head);
        }

        holder.tvNickName.setText(nick_name);
        holder.tvDescription.setText(content);
        holder.tvTime.setText(time);

        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'evaluate_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.head)
        ImageView head;
        @Bind(R.id.tv_nickName)
        TextView tvNickName;
        @Bind(R.id.tv_description)
        TextView tvDescription;
        @Bind(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
