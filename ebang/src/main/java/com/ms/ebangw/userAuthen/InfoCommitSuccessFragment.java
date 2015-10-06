package com.ms.ebangw.userAuthen;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.fragment.BaseFragment;
import com.ms.ebangw.userAuthen.developers.DevelopersAuthenActivity;
import com.ms.ebangw.userAuthen.headman.HeadmanAuthenActivity;
import com.ms.ebangw.userAuthen.investor.InvestorAuthenActivity;
import com.ms.ebangw.userAuthen.worker.WorkerAuthenActivity;

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
        View layout = getActivity().findViewById(R.id.layout_step);
//        View layout_title = getActivity().findViewById(R.id.layout_title);


        layout.setVisibility(View.GONE);

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

    }

    @Override
    public void initData() {
        goHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b = true;
                MyApplication.getInstance().setFlag_home(b);
                mActivity.finish();

            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String title;
        switch (category) {
            case Constants.INVESTOR:
                title = "个人认证";
                ((InvestorAuthenActivity)getActivity()).initTitle(null, "返回", title, null, null);
                break;

            case Constants.WORKER:

                title = "务工人认证";
                ((WorkerAuthenActivity)getActivity()).initTitle(null, "返回", title, null, null);
                break;

            case Constants.HEADMAN:

                title = "工长认证";
                ((HeadmanAuthenActivity)getActivity()).initTitle(null, "返回", title, null, null);

                break;

            case Constants.DEVELOPERS:
                title = "开发商认证";
                ((DevelopersAuthenActivity)getActivity()).initTitle(null, "返回", title, null, null);
                break;
            default:

                title = "";
                break;
        }


    }


}
