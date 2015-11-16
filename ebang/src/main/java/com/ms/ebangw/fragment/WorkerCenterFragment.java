package com.ms.ebangw.fragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ms.ebangw.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 务工人中心
 * @author wangkai
 *
 */
public class WorkerCenterFragment extends BaseFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ViewGroup contentLayout;
    private FragmentManager fm;






    public static WorkerCenterFragment newInstance(String param1, String param2) {
        WorkerCenterFragment fragment = new WorkerCenterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public WorkerCenterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        fm = getFragmentManager();

    }

    @Override
    public void initView() {
        fm.beginTransaction().replace(R.id.fl_head_info, HeadInfoFragment.newInstance("", ""));

    }

    @Override
    public void initData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_worker_center, null);
        ButterKnife.bind(this, contentLayout);
        initView();
        initData();
        return contentLayout;
    }


}
