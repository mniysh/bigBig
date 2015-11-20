package com.ms.ebangw.adapter;

import android.graphics.Color;
import android.provider.CalendarContract;
import android.text.TextUtils;
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
 * 发布的邀请工友
 * User: yangshaohua(2589704913@qq.com)
 * 2015-10-30 13:44
 */
public class InviteWorkersAdapter extends BaseAdapter {
    private List<Worker> list;
    private OnRemoveRelationListener onRemoveRelationListener;

    public InviteWorkersAdapter(List<Worker> list, OnRemoveRelationListener onRemoveRelationListener) {
        this.list = list;
        this.onRemoveRelationListener = onRemoveRelationListener;
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
            convertView = View.inflate(parent.getContext(), R.layout.invite_worker_item, null);
            holder.tv_py = (TextView) convertView.findViewById(R.id.tv_py);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_craft_desc = (TextView) convertView.findViewById(R.id.tv_craft_desc);
            holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
            holder.tv_remove_relation = (TextView) convertView.findViewById(R.id.tv_remove_relation);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        String firstChar = null;
        final Worker worker = (Worker) getItem(position);
        String isInvitetion = worker.getIsInvitation();

//        if (position == 0) {
//            firstChar = worker.getPinyin().substring(0, 1);
//        } else {
//            String py = worker.getPinyin().substring(0, 1);
//            String spy = list.get(position - 1).getPinyin().substring(0, 1);
//            if (!py.equals(spy)) {
//                firstChar = worker.getPinyin().substring(0, 1);
//            }
//        }
//        if (firstChar == null) {
//            holder.tv_py.setVisibility(View.GONE);
//        } else {
//            holder.tv_py.setVisibility(View.GONE);
//            holder.tv_py.setText(firstChar);
//        }
        holder.tv_name.setText(worker.getReal_name());
        holder.tv_craft_desc.setText(getWorkTypesDescriptions(worker.getCraft()));
        Picasso.with(parent.getContext()).load(DataAccessUtil.getImageUrl(worker.getHead_image())).
                placeholder(R.drawable.worker_avatar).into(holder.iv_avatar);
        if(TextUtils.equals(isInvitetion, "invite")){
            holder.tv_remove_relation.setText("邀请中");
            holder.tv_remove_relation.setBackgroundColor(Color.parseColor("#ADACAA"));
        }else if(TextUtils.equals(isInvitetion, "no")){
            holder.tv_remove_relation.setText("发送邀请");
            holder.tv_remove_relation.setBackgroundColor(Color.parseColor("#0076a9"));
        }else if(TextUtils.equals(isInvitetion,"agree")){
            holder.tv_remove_relation.setText("邀请成功");
            holder.tv_remove_relation.setBackgroundColor(Color.parseColor("#ADACAA"));
        }else if(TextUtils.equals(isInvitetion, "refuse")){
            holder.tv_remove_relation.setText("拒绝邀请");
            holder.tv_remove_relation.setBackgroundColor(Color.parseColor("#ADACAA"));
        }
        holder.tv_remove_relation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onRemoveRelationListener) {
//                    onRemoveRelationListener.onRemove(worker);
                    onRemoveRelationListener.onAdd(worker);
                }
            }
        });
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

    public interface OnRemoveRelationListener{
//        void onRemove(Worker worker);
        void onAdd(Worker worker);
    }



    public List<Worker> getList() {
        return list;
    }

    public void setList(List<Worker> list) {
        this.list = list;
    }
}
