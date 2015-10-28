package com.ms.ebangw.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ms.ebangw.R;

import butterknife.ButterKnife;

/**
 * 工长中心
 * @author wangkai
 *
 */
public class HeadmanCenterFragment extends BaseFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ViewGroup contentLayout;


    public static HeadmanCenterFragment newInstance(String param1, String param2) {
        HeadmanCenterFragment fragment = new HeadmanCenterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HeadmanCenterFragment() {
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
            contentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_headman_center, null);
            ButterKnife.bind(this, contentLayout);
            initView();
            initData();
            return contentLayout;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }



}
