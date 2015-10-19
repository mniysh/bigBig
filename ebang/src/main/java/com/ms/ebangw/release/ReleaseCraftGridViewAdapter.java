package com.ms.ebangw.release;

import android.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.WorkType;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.dialog.SelectWorTypeDialog;
import com.ms.ebangw.event.OnCheckedWorkTypeEvent;
import com.ms.ebangw.utils.T;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 发布界面选择工种
 * User: WangKai(123940232@qq.com)
 * 2015-09-26 16:21
 */
public class ReleaseCraftGridViewAdapter extends BaseAdapter{

    private List<WorkType> list;
    private FragmentManager fm;


    private List<WorkType> selectedWorkTypes;

    public ReleaseCraftGridViewAdapter(FragmentManager fm, List<WorkType> list) {
        this.fm = fm;
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
                    showSelectWorkTypeDialog(cb, type);
                    selectedWorkTypes.add(type);
                } else {
                    selectedWorkTypes.remove(type);
                    EventBus.getDefault().post(new OnCheckedWorkTypeEvent(type, false));
                }
            }
        });
        cb.setTag(Constants.KEY_WORK_TYPE, workType);
        return cb;
    }

    public void showSelectWorkTypeDialog(final CheckBox cb, WorkType workType) {
        SelectWorTypeDialog dialog = SelectWorTypeDialog.newInstance(workType);
        dialog.setOnStaffSelectedListener(new SelectWorTypeDialog.OnStaffSelectedListener() {
            @Override
            public void onStaffSelected(WorkType workType, boolean isSelected) {
                if (isSelected) {
                    //T.show("adapter能收到");
                    EventBus.getDefault().post(new OnCheckedWorkTypeEvent(workType, true));
                }else {
                    cb.toggle();
                }
            }
        });
        dialog.show(fm, "workType");
    }

    public List<WorkType> getSelectedWorkTypes() {
        return selectedWorkTypes;
    }

}
