package com.ms.ebangw.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.HomeActivity;
import com.ms.ebangw.bean.HeadmanEven;
import com.ms.ebangw.bean.SelectHeadman;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.view.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by yangshaohua .
 * Created by on 2015/11/24
 */
public class SelectHeadmanAdapter extends BaseAdapter {
    private List<SelectHeadman> headmans;
//    private HomeActivity activity;
    private SelectHeadman headman;
    private List<SelectHeadman> dataLinshi;

    public SelectHeadmanAdapter(List<SelectHeadman> headmans) {
        this.headmans = headmans;
//        this.activity = activity;
    }

    @Override
    public int getCount() {
        if(headmans == null){
            return 0;
        }
        return headmans.size();
    }

    @Override
    public Object getItem(int position) {
        return headmans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SelectHeadman selectHeadman;
        String contend_status = headmans.get(position).getContend_status();

        if(dataLinshi == null){

            dataLinshi = new ArrayList<SelectHeadman>();
        }
        if(convertView == null){
            convertView = View.inflate(parent.getContext(), R.layout.item_show_headman, null);
        }
        final CheckBox cb = ViewHolder.get(convertView , R.id.gridView);
        if(TextUtils.equals(contend_status, Constants.CONTEND_STATUS_SUCCEED)){
            cb.setChecked(true);
            if(dataLinshi != null){
                dataLinshi.remove(0);
            }
            dataLinshi.add(headmans.get(position));
            EventBus.getDefault().post(new HeadmanEven(headmans.get(position), true));

        }else{
            cb.setChecked(false);
        }
        cb.setText(headmans.get(position).getReal_name());
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (dataLinshi.size() == 0) {
                        dataLinshi.add(headmans.get(position));
                        EventBus.getDefault().post(new HeadmanEven(headmans.get(position),true));

                    } else {
                        T.show("只能选择一个工长或劳务公司");
                        cb.setChecked(false);
                    }
                } else {
                    dataLinshi.remove(0);
                    EventBus.getDefault().post(new HeadmanEven(headmans.get(position),false));
                }
            }
        });

        return convertView;
    }
}
