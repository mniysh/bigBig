package com.ms.ebangw.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.HomeActivity;
import com.ms.ebangw.bean.ServiceListbean;
import com.ms.ebangw.bean.WorkType;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.view.ViewHolder;

import java.util.List;

/**
 * 首页的服务模块的ListView的adapter
 * Created by admin on 2015/9/24.
 */
public class ServiceAdapter extends BaseAdapter {
    private HomeActivity act;
    private List<ServiceListbean> datas;



    public ServiceAdapter(HomeActivity act, List<ServiceListbean> datas) {
        this.act = act;
        this.datas = datas;

    }

    @Override
    public int getCount() {

        if(datas==null){
            return  0;
        }
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ServiceListbean serviceListbean = datas.get(position);

        if(convertView==null){
            convertView=act.getLayoutInflater().inflate(R.layout.item_service,null);
        }
        TextView tTitle= ViewHolder.get(convertView, R.id.title);
        GridView gContent = ViewHolder.get(convertView, R.id.gv_gridview);
        L.d("xxx","datas标题是"+serviceListbean.getTitle());
        tTitle.setText(serviceListbean.getTitle());
        gContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        List<WorkType> wordTypes = serviceListbean.getWordTypes();
        gContent.setAdapter(new ServiceGridViewAdapter(act,wordTypes));



        return convertView;
    }
}
