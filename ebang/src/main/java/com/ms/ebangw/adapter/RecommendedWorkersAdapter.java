package com.ms.ebangw.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.WorkType;
import com.ms.ebangw.bean.Worker;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.service.DataAccessUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-10-30 13:44
 */
public class RecommendedWorkersAdapter extends BaseAdapter {
    private List<Worker> list;

    public RecommendedWorkersAdapter(List<Worker> list) {
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
            holder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.recommend_worker_item, null);
            holder.tv_py = (TextView) convertView.findViewById(R.id.tv_py);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_craft_desc = (TextView) convertView.findViewById(R.id.tv_craft_desc);
            holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
            holder.tv_remove_relation = (TextView) convertView.findViewById(R.id.tv_remove_relation);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String firstChar = null;
        Worker worker = (Worker) getItem(position);
        if (position == 0) {
            firstChar = worker.getPinyin().substring(0, 1);
        } else {
            String py = worker.getPinyin().substring(0, 1);
            String spy = list.get(position - 1).getPinyin().substring(0, 1);
            if (!py.equals(spy)) {
                firstChar = worker.getPinyin().substring(0, 1);
            }
        }
        if (firstChar == null) {
            holder.tv_py.setVisibility(View.GONE);
        } else {
            holder.tv_py.setVisibility(View.VISIBLE);
            holder.tv_py.setText(firstChar);
        }
        holder.tv_name.setText(worker.getReal_name());
        holder.tv_craft_desc.setText(getWorkTypesDescriptions(worker.getWorkTypes()));
        Picasso.with(parent.getContext()).load(DataAccessUtil.getImageUrl(worker.getHead_image())
        ).placeholder(R.drawable.worker_avatar).into(holder.iv_avatar);
        convertView.setTag(Constants.KEY_WORKER, worker);

        return convertView;
    }

    public String getWorkTypesDescriptions(List<WorkType> workTypes) {

        if (workTypes != null && workTypes.size() > 0) {
            StringBuilder builder = new StringBuilder();
            int count = workTypes.size();
            WorkType type;

            for (int i = 0; i < count; i++) {
                type = workTypes.get(i);
                if (i == count - 1) {
                    builder.append(type.getName());
                }else {
                    builder.append(type.getName() + ", ");
                }
            }

            return builder.toString();
        }

        return "";
    }

    static class ViewHolder {
        TextView tv_py, tv_name, tv_craft_desc, tv_remove_relation;
        ImageView iv_avatar;

    }
}
