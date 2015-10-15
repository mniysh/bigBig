package com.ms.ebangw.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.HomeActivity;
import com.ms.ebangw.bean.WorkType;
import com.ms.ebangw.view.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务的listview的adapter
 * Created by admin on 2015/10/14.
 */
public class ServiceCraftAdapter extends BaseAdapter {
    private WorkType workType;
    private List<WorkType> data;
    private HomeActivity activity;



    public ServiceCraftAdapter(WorkType workType, HomeActivity activity){
        this.workType = workType;
        this.activity = activity;
        if(getType() == 1){
            data = new ArrayList<WorkType>();
            data.add(workType);
        }else{
            data = workType.getWorkTypes();
        }
    }
    public void setWorkType(WorkType workType, HomeActivity activity){
        this.workType = workType;
        this.activity = activity;
        if(getType() == 1){
            data = new ArrayList<WorkType>();
            data.add(workType);

        }else{
            data = workType.getWorkTypes();
        }
    }


    @Override
    public int getCount() {
        if(data == null ){
            return 0;
        }else{
            return data.size();
        }

    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(R.layout.layout_craft_item, null);
        }
        TextView tTitle = ViewHolder.get(convertView, R.id.tv_title);
        GridView gridView = ViewHolder.get(convertView, R.id.gridView);
        WorkType workTypes = data.get(position);
        List<WorkType> workTypeList = workTypes.getWorkTypes();
        if(getType() == 1){
            tTitle.setVisibility(View.GONE);
            ServiceCraftGridViewAdapter serviceCraftGridViewAdapter = new ServiceCraftGridViewAdapter(workTypeList, activity);
            gridView.setAdapter(serviceCraftGridViewAdapter);
        }
        if(getType() == 2){

            for(int i = 0; i < data.size(); i++ ){
                //List<WorkType> workType = workTypeList.get(i).getWorkTypes();
                tTitle.setVisibility(View.VISIBLE);
                tTitle.setText(workTypes.getName());
                ServiceCraftGridViewAdapter serviceCraftGridViewAdapter = new ServiceCraftGridViewAdapter(workTypeList, activity);
                gridView.setAdapter(serviceCraftGridViewAdapter);
            }

        }


        return convertView;
    }

    /**
     * 返回级数
     * @return
     */
    public int getType(){
        String id = workType.getId();
        if(TextUtils.equals(id, "1") || TextUtils.equals(id, "88")){
            return 1;
        }else{
            return 2;
        }
    }
}
