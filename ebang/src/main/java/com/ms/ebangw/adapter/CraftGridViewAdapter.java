package com.ms.ebangw.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.HomeActivity;
import com.ms.ebangw.bean.WorkType;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.event.WorkTypeEvent;
import com.ms.ebangw.userAuthen.worker.WorkTypeActivity;
import com.ms.ebangw.utils.T;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 工人认证的adapter
 * User: WangKai(123940232@qq.com)
 * 2015-09-26 16:21
 */
public class CraftGridViewAdapter extends BaseAdapter{

    private List<WorkType> list;

    private Activity activity;
    List<WorkType> selectedWorkTypes;


    public CraftGridViewAdapter(List<WorkType> list) {
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
        final WorkType workType = list.get(position);
        final CheckBox cb = (CheckBox) View.inflate(parent.getContext(), R.layout.layout_craft_gridview_item, null);
        cb.setText(workType.getName());
        activity = getActivity();
        if(activity != null && activity instanceof  WorkTypeActivity){
            selectedWorkTypes = getSelectedWorkTypes();
            if (selectedWorkTypes.contains(workType)) {
                cb.setChecked(true);
            }else {
                cb.setChecked(false);
            }
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    WorkType type = (WorkType) buttonView.getTag(Constants.KEY_WORK_TYPE);
                    if (isChecked) {
                        if (!isNumBeyondFive()) {
                            EventBus.getDefault().post(new WorkTypeEvent(workType, true));

                        }else {
                            cb.toggle();
                            return;
                        }
                    } else {
                        EventBus.getDefault().post(new WorkTypeEvent(workType, false));
                    }
                }
            });
            cb.setTag(Constants.KEY_WORK_TYPE, workType);
            return cb;
        }else  if (activity != null && activity instanceof HomeActivity){


                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        WorkType type = (WorkType) buttonView.getTag(Constants.KEY_WORK_TYPE);
                        if (isChecked) {

                            EventBus.getDefault().post(new WorkTypeEvent(workType, true));
                            showWindow();
                        } else {
                            cb.toggle();
                            return;
                        }

                    }

                });
                return cb;
        }
        return null;




    }

    /**
     * 判断选择的工种是否超过5种
     * @return
     */
    public boolean isNumBeyondFive() {
        if (activity != null && activity instanceof WorkTypeActivity) {
            WorkTypeActivity workTypeActivity = (WorkTypeActivity) activity;
            ArrayList<WorkType> selectedWorkTypes = workTypeActivity.getSelectedWorkTypes();
            if (selectedWorkTypes != null && selectedWorkTypes.size() >= 5) {
                T.show("最多能选择五个工种");
                return true;
            }
        }

        return false;
    }

    /**
     * 获取全部已选中的工种
     * @return
     */
    public List<WorkType> getSelectedWorkTypes() {
        if (activity != null && activity instanceof WorkTypeActivity) {
            WorkTypeActivity workTypeActivity = (WorkTypeActivity) activity;
            ArrayList<WorkType> selectedWorkTypes = workTypeActivity.getSelectedWorkTypes();
            return selectedWorkTypes;
        }
        if(activity != null && activity instanceof  HomeActivity){
            HomeActivity homeActivity = (HomeActivity) activity;
            ArrayList<WorkType> selecteWorkTypes = (ArrayList<WorkType>) homeActivity.getSelectWorkType();
            return selecteWorkTypes;
        }

        return null;

    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void showWindow(){
        PopupWindow pw = new PopupWindow();

    }

}
