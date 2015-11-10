package com.ms.ebangw.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.PublishedProjectActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 个人中心
 *
 * @author wangkai
 */
public class InvestorCenterFragment extends BaseFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.fl_head_info)
    FrameLayout flHeadInfo;
    @Bind(R.id.tv_published)
    TextView tvPublished;
    @Bind(R.id.tv_trade)
    TextView tvTrade;
    @Bind(R.id.tv_evaluate)
    TextView tvEvaluate;
    @Bind(R.id.tv_jifen)
    TextView tvJifen;

    private String mParam1;
    private String mParam2;


    public static InvestorCenterFragment newInstance(String param1, String param2) {
        InvestorCenterFragment fragment = new InvestorCenterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public InvestorCenterFragment() {
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
        View view = inflater.inflate(R.layout.fragment_investor_center, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }


    @Override
    public void initView() {
        tvPublished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, PublishedProjectActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
