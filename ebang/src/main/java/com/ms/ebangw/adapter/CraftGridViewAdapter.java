package com.ms.ebangw.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.WorkType;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.userAuthen.worker.WorkTypeActivity;
import com.ms.ebangw.utils.T;

import java.util.ArrayList;
import java.util.List;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-09-26 16:21
 */
public class CraftGridViewAdapter extends BaseAdapter{

    private List<WorkType> list;

    private Activity activity;


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
        final CheckBox cb = (CheckBox) View.inflate(parent.getContext(), R.layout
            .layout_craft_gridview_item, null);
        cb.setText(workType.getName());
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                WorkType type = (WorkType) buttonView.getTag(Constants.KEY_WORK_TYPE);
                if (isChecked) {
                    if (!isNumBeyondFive(cb)) {
                        selectedWorkTypes.add(type);
                    }else {
                        return;
                    }
                } else {
                    selectedWorkTypes.remove(type);
                }
            }
        });
        cb.setTag(Constants.KEY_WORK_TYPE, workType);
        return cb;
    }

    /**
     * 判断选择的工种是否超过5种
     * @param cb
     * @return
     */
    public boolean isNumBeyondFive(CheckBox cb) {
        if (activity != null && activity instanceof WorkTypeActivity) {
            WorkTypeActivity workTypeActivity = (WorkTypeActivity) activity;
            ArrayList<WorkType> selectedWorkTypes = workTypeActivity.getSelectedWorkTypes();
            if (selectedWorkTypes != null && selectedWorkTypes.size() >= 5) {
                T.show("最多能选择五个工种");
                cb.toggle();
                return true;
            }
        }

        return false;
    }

    public List<WorkType> getSelectedWorkTypes() {
        return selectedWorkTypes;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

}
