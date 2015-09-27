package com.ms.ebangw.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.WorkType;
import com.ms.ebangw.commons.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-09-26 16:21
 */
public class CraftGridViewAdapter extends BaseAdapter{

    private List<WorkType> list;


    private List<WorkType> selectedWorkTypes;

    public CraftGridViewAdapter(List<WorkType> list) {
        this.list = list;
        selectedWorkTypes = new ArrayList<>();
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
        WorkType workType = list.get(position);
        CheckBox cb = (CheckBox) View.inflate(parent.getContext(), R.layout
            .layout_craft_gridview_item, null);
        cb.setText(workType.getName());
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                WorkType type = (WorkType) buttonView.getTag(Constants.KEY_WORK_TYPE);
                if (isChecked) {
                    selectedWorkTypes.add(type);
                } else {
                    selectedWorkTypes.remove(type);
                }
            }
        });
        cb.setTag(Constants.KEY_WORK_TYPE, workType);
        return cb;
    }

    public List<WorkType> getSelectedWorkTypes() {
        return selectedWorkTypes;
    }

}
