package com.ms.ebangw.release;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.WorkType;
import com.ms.ebangw.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 工种展示
 */
public class ReleaseWorkTypeFragment extends BaseFragment {
    private static final String WORK_TYPE = "work_type";

    private WorkType workType;
    private ViewGroup contentLayout;
    private ReleaseCraftAdapter craftAdapter;
    @Bind(R.id.listView)
    ListView listView;

    public static ReleaseWorkTypeFragment newInstance(WorkType workType) {
        ReleaseWorkTypeFragment fragment = new ReleaseWorkTypeFragment();
        Bundle args = new Bundle();
        args.putParcelable(WORK_TYPE, workType);
        fragment.setArguments(args);
        return fragment;
    }

    public ReleaseWorkTypeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            workType = getArguments().getParcelable(WORK_TYPE);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        contentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_work_type, container,
            false);
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

        craftAdapter = new ReleaseCraftAdapter(getFragmentManager(), workType);
        listView.setAdapter(craftAdapter);

    }
}
