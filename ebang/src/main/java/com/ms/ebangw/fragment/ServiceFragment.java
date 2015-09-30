package com.ms.ebangw.fragment;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;




import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;


import com.ms.ebangw.R;
import com.ms.ebangw.adapter.ServicePagerAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 主页的服务页面的一级页面
 */
public class ServiceFragment extends BaseFragment {

    private ServiceBuildFragment serviceBuildFragment;
    private ServiceDecorateFragment serviceDecorateFragment;
    private ServiceProjectManageFragment serviceProjectManageFragment;
    private ServiceOtherFragment serviceOtherFragment;
    private FragmentManager fragmentManager;
    private List<Fragment> datas;
    private ViewPager vPager;
    private RadioGroup radioGroup;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ServicePagerAdapter adapter;

    /**
     * 实例化方法
     * @param param1
     * @param param2
     * @return
     */
    public static ServiceFragment newInstance(String param1, String param2) {
        ServiceFragment fragment = new ServiceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ServiceFragment() {
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
        ButterKnife.bind(mActivity);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(mActivity);
        initView();
        initData();

    }


    @Override
    public void initView() {
        initTitle(null,null,"服务",null,null);
        serviceBuildFragment=new ServiceBuildFragment();
        serviceDecorateFragment=new ServiceDecorateFragment();
        serviceOtherFragment=new ServiceOtherFragment();
        serviceProjectManageFragment=new ServiceProjectManageFragment();
        datas=new ArrayList<Fragment>();


    }




    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void initData() {
        vPager= (ViewPager) getView().findViewById(R.id.vp_con);
        datas.add(serviceBuildFragment);
        datas.add(serviceDecorateFragment);
        datas.add(serviceOtherFragment);
        datas.add(serviceProjectManageFragment);
        adapter = new ServicePagerAdapter(getChildFragmentManager(), datas);
        vPager.setAdapter(adapter);
        radioGroup= (RadioGroup) getView().findViewById(R.id.rg_but);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_build:
                        vPager.setCurrentItem(0);
                        break;
                    case R.id.rb_decorate:
                        vPager.setCurrentItem(1);
                        break;
                    case R.id.rb_projectManager:
                        vPager.setCurrentItem(2);
                        break;
                    case R.id.rb_other:
                       vPager.setCurrentItem(3);
                        break;


                }
            }
        });
        radioGroup.getChildAt(0).performClick();

    }
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
