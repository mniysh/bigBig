package com.ms.ebangw.fragment;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ms.ebangw.R;

import butterknife.ButterKnife;

/**
 * 社区
 * @author wangkai
 */
public class CommunityFragment extends BaseFragment {
    private ViewGroup contentLayout;

    public CommunityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_community, container, false);
        ButterKnife.bind(this, contentLayout);
        initView();
        initData();
        return contentLayout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitle(null, null, "社区", "我的列表", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置跳转
            }
        });

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
