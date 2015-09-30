package com.ms.ebangw.release;

import android.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.WorkType;

import java.util.ArrayList;
import java.util.List;

/**
 * 发布页面工种选择ListView的adapter
 * User: WangKai(123940232@qq.com)
 * 2015-09-26 15:07
 */
public class ReleaseCraftAdapter extends BaseAdapter {
    private WorkType firstWorkType;
    private List<WorkType> list;
    private FragmentManager fm;


    private List<WorkType> selectedWorkTypes;

    public ReleaseCraftAdapter(FragmentManager fm, WorkType firstWorkType) {
        this.fm = fm;
        this.firstWorkType = firstWorkType;
        if (getType() == 1) {
            list = new ArrayList<>();
            list.add(firstWorkType);
        }else {
            list = firstWorkType.getWorkTypes();
        }
        selectedWorkTypes = new ArrayList<>();
    }

    public void setWorkType(WorkType firstWorkType) {
        this.firstWorkType = firstWorkType;
//        list = firstWorkType.getWorkTypes();

        if (getType() == 1) {
            list = new ArrayList<>();
            list.add(firstWorkType);
        }else {
            list = firstWorkType.getWorkTypes();
        }
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
        if (null == convertView) {
            convertView = View.inflate(parent.getContext(), R.layout.layout_craft_item, null);
            holder = new ViewHolder();
            holder.titleTv = (TextView) convertView.findViewById(R.id.tv_title);
//            holder.cb = (CheckBox) convertView.findViewById(R.id.cb);
            holder.gridView = (GridView) convertView.findViewById(R.id.gridView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        WorkType workType = list.get(position);
        List<WorkType> types = workType.getWorkTypes();
        if (getType() == 1) {
            holder.titleTv.setVisibility(View.GONE);
            ReleaseCraftGridViewAdapter craftGridViewAdapter = new ReleaseCraftGridViewAdapter(fm, types);
            holder.gridView.setAdapter(craftGridViewAdapter);

        }

        if (getType() == 2) {
            for (int i = 0; i < types.size(); i++) {
                holder.titleTv.setVisibility(View.VISIBLE);
                holder.titleTv.setText(workType.getName());
                ReleaseCraftGridViewAdapter craftGridViewAdapter = new ReleaseCraftGridViewAdapter(fm, types);
                holder.gridView.setAdapter(craftGridViewAdapter);
            }
        }
        return convertView;
    }

    /**
     * 工程管理与其他只有俩级，建筑类与装修类有三级  返回级数
     * @return
     */
    private int getType() {
        String id = firstWorkType.getId();
        if (TextUtils.equals("1", id) || TextUtils.equals("88", id)) {
            return 1;
        }else {
            return 2;
        }
    }

    public List<WorkType> getSelectedWorkTypes() {
        return selectedWorkTypes;
    }

    static class ViewHolder{
        TextView titleTv;
        GridView gridView;
    }
}
