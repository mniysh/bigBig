package com.ms.ebangw.userAuthen;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.HomeActivity;
import com.ms.ebangw.activity.SettingActivity;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InfoCommitSuccessFragment extends BaseFragment {
    private static final String CATEGORY = "category";
    private ViewGroup contentLayout;
    private String category;
    @Bind(R.id.btn_goHome)
    Button goHomeBtn;



    public static InfoCommitSuccessFragment newInstance(String category) {
        InfoCommitSuccessFragment fragment = new InfoCommitSuccessFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    public InfoCommitSuccessFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(CATEGORY);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_info_commit_success,
            container, false);
        ButterKnife.bind(this, contentLayout);
        initView();
        initData();

        return contentLayout;
    }


    @Override
    public void initView() {
        if (TextUtils.isEmpty(category)) {
            return;
        }

    }

    @Override
    public void initData() {
        goHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mActivity instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) mActivity;
                    homeActivity.lotteryRb.performClick();
                } else {
//                    EventBus.getDefault().post(new PerformEvent());
                    MyApplication.getInstance().setFlag_home(true);
                    mActivity.finish();
                }
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        View layout = mActivity.findViewById(R.id.layout_step);
        View titleLayout = mActivity.findViewById(R.id.layout_title);

        if (null != layout) {
            layout.setVisibility(View.GONE);
        }

        if (titleLayout != null) {
            titleLayout.setVisibility(View.GONE);
        }

        String title = getTitleByCategory(category);
//        initTitle(null, "返回", title, null, null);
        if (mActivity instanceof HomeActivity) {

            initTitle(null, null, "我的信息", "设置", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //设置跳转
                    Intent intent=new Intent(mActivity, SettingActivity.class);

                    mActivity.startActivityForResult(intent, Constants.REQUEST_EXIT);
                }
            });
        }else {
            initTitle(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.finish();
                }
            }, "返回", getTitleByCategory(category), null, null);
        }

    }

    public String getTitleByCategory(String category) {
        if (TextUtils.isEmpty(category)) {
            return "";
        }

        String title;
        switch (category) {
            case Constants.INVESTOR:
                title = "个人认证";
                break;

            case Constants.WORKER:

                title = "务工人认证";
                break;

            case Constants.HEADMAN:

                title = "工长认证";

                break;

            case Constants.DEVELOPERS:
                title = "开发商认证";
                break;
            default:
                title = "";
                break;
        }

        return title;
    }


}
