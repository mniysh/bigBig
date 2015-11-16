package com.ms.ebangw.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.Staff;
import com.ms.ebangw.commons.Constants;
import com.tencent.open.utils.Global;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xupeng 2015/11/11.
 */
public class ProjectItemdetailAdapter extends BaseAdapter {

    private List<Staff> list;

    public ProjectItemdetailAdapter(List<Staff> projectList) {
        this.list = projectList;
    }

    public void setList(List<Staff> list) {
        this.list = list;
    }

    public List<Staff> getList() {
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
        final Staff project = list.get(position);
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.home_project_detail_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//                "id": 49,
//                "craft_id": 1,
//                "money": 100,
//                "status": "prepare",
//                "project_id": 7,
//                "staff_account": 2,
//                "staff_description": null,
//                "start_time": "2015-10-28 00:00:00",
//                "end_time": "2015-10-29 00:00:00",
//                "account_days": 2,
//                "craft_name": "工程管理"
//        String imageUrl = project.getImages();
        String money = project.getMoney();
        String staff_account = project.getStaff_account();
        String craft_name = project.getCraft_name();
        String start_time = project.getStart_time();
        String end_time = project.getEnd_time();

        holder.tvMoney.setText(money);
        holder.tvStaffAccount.setText(staff_account);
        holder.tvCraftName.setText(craft_name);
        holder.tvStartTime.setText(start_time);
        holder.tvEndTime.setText(end_time);
        holder.tHead.setText((position+1)+"");
        holder.tHead.setTag(position);
//        SharedPreferences sharedPreferences = Global.getSharedPreferences("test",Context.MODE_PRIVATE);
//        String IsContend = sharedPreferences.getString("IsContend","");
//        if (IsContend!="complete"){
//            holder.tvXuanRen.setVisibility(View.GONE);
//        }else{
            holder.tvXuanRen.setVisibility(View.VISIBLE);
            holder.tvXuanRen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onGrabClickListener) {
                        onGrabClickListener.onGrabClick(v, project);
                    }
                }
            });
//        }

        convertView.setTag(Constants.KEY_RELEASED_PROJECT, project);
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_money)
        TextView tvMoney;
        @Bind(R.id.tv_staff_account)
        TextView tvStaffAccount;
        @Bind(R.id.tv_craft_name)
        TextView tvCraftName;
        @Bind(R.id.tv_start_time)
        TextView tvStartTime;
        @Bind(R.id.tv_end_time)
        TextView tvEndTime;
        @Bind(R.id.bt_xuan_ren)
        Button tvXuanRen;
        @Bind(R.id.tv_head)
        TextView tHead;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    public interface OnGrabClickListener {
        void onGrabClick(View view, Staff staff);
    }

    private OnGrabClickListener onGrabClickListener;

    public void setOnGrabClickListener(OnGrabClickListener onGrabClickListener) {
        this.onGrabClickListener = onGrabClickListener;
    }
}
