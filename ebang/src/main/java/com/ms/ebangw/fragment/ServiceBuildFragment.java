package com.ms.ebangw.fragment;


import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.HomeActivity;
import com.ms.ebangw.adapter.ServiceAdapter;
import com.ms.ebangw.adapter.ServiceGridViewAdapter;
import com.ms.ebangw.bean.ServiceListbean;
import com.ms.ebangw.bean.WorkType;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务的建筑类
 */
public class ServiceBuildFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private List<ServiceListbean> datas;
    private ServiceAdapter serviceAdapter;
    private ServiceGridViewAdapter serviceGridViewAdapter;
    private List<String> datas_gv;
    private List<WorkType> datas_work;
    private ListView listView;


    /**
     * 实例化方法
     * @param param1
     * @param param2
     * @return
     */
    public static ServiceBuildFragment newInstance(String param1, String param2) {
        ServiceBuildFragment fragment = new ServiceBuildFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ServiceBuildFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service_build, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();

    }

    @Override
    public void initView() {
        datas=new ArrayList<ServiceListbean>();
        datas_gv=new ArrayList<String>();
        datas_work=new ArrayList<WorkType>();
        WorkType workType=new WorkType();
        WorkType workType1=new WorkType();
        WorkType workType2=new WorkType();
        WorkType workType3=new WorkType();
        listView= (ListView) getView().findViewById(R.id.lv_listview);
        workType.setName("木工");
        workType1.setName("水工");
        workType2.setName("火攻工");
        workType3.setName("工");
        datas_work.add(workType);
        datas_work.add(workType1);
        datas_work.add(workType2);
        datas_work.add(workType3);

        ServiceListbean serviceListbean =new ServiceListbean();
        serviceListbean.setTitle("标题");
        serviceListbean.setWordTypes(datas_work);
        datas.add(serviceListbean);
        serviceAdapter=new ServiceAdapter((HomeActivity) mActivity,datas);
        listView.setAdapter(serviceAdapter);

    }

    @Override
    public void initData() {

    }
}
