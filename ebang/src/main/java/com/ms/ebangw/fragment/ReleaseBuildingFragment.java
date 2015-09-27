package com.ms.ebangw.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.text.GetChars;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.adapter.CraftAdapter;
import com.ms.ebangw.adapter.ReleaseListAdapter;
import com.ms.ebangw.bean.Craft;

/**
 * 发布的建筑页面
 */
public class ReleaseBuildingFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Craft craft;
    private ListView listView;
    private CraftAdapter craftAdapter;


    public static ReleaseBuildingFragment newInstance(String param1, String param2) {
        ReleaseBuildingFragment fragment = new ReleaseBuildingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ReleaseBuildingFragment() {
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

        return inflater.inflate(R.layout.fragment_release_building, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();

    }

    @Override
    public void initView() {
        listView= (ListView) getView().findViewById(R.id.lv_listview);
        craft= MyApplication.getInstance().getCraft();
        if(craft != null){
            craftAdapter = new CraftAdapter(craft.getBuilding());
            listView.setAdapter(craftAdapter);
        }
        if (craftAdapter == null || craft == null) {
            return;
        }else{
            craftAdapter.setWorkType(craft.getBuilding());
        }
//        switch (checkedId) {
//            case R.id.rb_build:
//
//
//                break;
//            case R.id.rb_fitment:
//                craftAdapter.setWorkType(craft.getFitment());
//                break;
//            case R.id.rb_projectManager:
//                craftAdapter.setWorkType(craft.getProjectManage());
//                break;
//        }
        craftAdapter.notifyDataSetChanged();

    }

    @Override
    public void initData() {

    }
}
