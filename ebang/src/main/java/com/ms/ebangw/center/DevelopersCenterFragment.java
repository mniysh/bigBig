package com.ms.ebangw.center;


import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.AccountActivity;
import com.ms.ebangw.activity.ProjectStatusActivity;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.fragment.BaseFragment;
import com.ms.ebangw.fragment.HeadInfoFragment;
import com.ms.ebangw.release.ReleaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 开发商中心
 *
 * @author wangkai
 */
public class DevelopersCenterFragment extends BaseFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.tv_grab)
    TextView tvPublished;
    @Bind(R.id.tv_trade)
    TextView tvTrade;
    @Bind(R.id.tv_publish)
    TextView tvPublish;

    private String mParam1;
    private String mParam2;
    private ViewGroup contentLayout;
    private FragmentManager fm;



    public static DevelopersCenterFragment newInstance(String param1, String param2) {
        DevelopersCenterFragment fragment = new DevelopersCenterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DevelopersCenterFragment() {
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
        contentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_developers_center, null);
        ButterKnife.bind(this, contentLayout);
        initView();
        initData();
        return contentLayout;
    }


    @Override
    public void initView() {
        fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.fl_head_info, HeadInfoFragment.newInstance("", ""))
            .commit();


        tvPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ReleaseActivity.class);
                startActivity(intent);
            }
        });

        tvPublished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ProjectStatusActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.KEY_PROJECT_TYPE, ProjectStatusActivity.TYPE_PUBLISH);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        tvTrade.setOnClickListener(new View.OnClickListener() {      //交易
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, AccountActivity.class);
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
