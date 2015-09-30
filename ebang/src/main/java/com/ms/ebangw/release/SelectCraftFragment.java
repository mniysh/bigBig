package com.ms.ebangw.release;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.HomeActivity;
import com.ms.ebangw.adapter.SelectTypePagerAdapter;
import com.ms.ebangw.bean.Craft;
import com.ms.ebangw.bean.DeveloperReleaseInfo;
import com.ms.ebangw.bean.Staff;
import com.ms.ebangw.bean.WorkType;
import com.ms.ebangw.event.OnCheckedWorkTypeEvent;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.fragment.BaseFragment;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.L;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 发布页面
 */
public class SelectCraftFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ViewGroup contentLayout;

    public DeveloperReleaseInfo getReleaseInfo() {
        return releaseInfo;
    }

    private DeveloperReleaseInfo releaseInfo;
    private Set<WorkType> workTypeSet;

    private Craft craft;

    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.tv_total_money)
    TextView totalMoneyTv;
    @Bind(R.id.rg_select_type)
    RadioGroup radioGroup;


    public static SelectCraftFragment newInstance(String param1, String param2) {
        SelectCraftFragment fragment = new SelectCraftFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SelectCraftFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_select_craft, container,
            false);
        ButterKnife.bind(this, contentLayout);
        initView();
        initData();
        return contentLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initTitle("发布");
    }

    @Override
    public void initView() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int childCount = group.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    if (group.getChildAt(i).getId() == checkedId) {
                        viewPager.setCurrentItem(i);
                    }
                }
            }
        });

        RadioButton radioButton = (RadioButton) radioGroup.getChildAt(0);
        radioButton.setChecked(true);
    }

    @Override
    public void initData() {
        getWorkType();
        releaseInfo = new DeveloperReleaseInfo();
        workTypeSet = new HashSet<>();
    }

    @OnClick(R.id.btn_next)
    public void goNext() {

        HomeActivity homeActivity = (HomeActivity) mActivity;
        homeActivity.goDeveloperRelease();


    }

    public Craft getWorkType() {
        DataAccessUtil.publishCraft(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    craft = DataParseUtil.publishCraft(response);
                    initViewPager(craft);

                } catch (ResponseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

        return craft;
    }

    public void initViewPager(Craft craft) {
        if (craft == null) {
            return;
        }

        List<Fragment> list = new ArrayList<>();
        ReleaseWorkTypeFragment buildingFragment = ReleaseWorkTypeFragment.newInstance(craft.getBuilding());
        ReleaseWorkTypeFragment fitmentFragment = ReleaseWorkTypeFragment.newInstance(craft.getFitment());
        ReleaseWorkTypeFragment projectManageFragment = ReleaseWorkTypeFragment.newInstance(craft.getProjectManage
            ());
        ReleaseWorkTypeFragment otherFragment = ReleaseWorkTypeFragment.newInstance(craft.getOther());

        list.add(buildingFragment);
        list.add(fitmentFragment);
        list.add(projectManageFragment);
        list.add(otherFragment);

        SelectTypePagerAdapter pagerAdapter = new SelectTypePagerAdapter(getFragmentManager(),
            list);
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(position);
                radioButton.setChecked(true);
            }
        });

    }

    /**
     *
     * @param event
     */
    public void onEvent(OnCheckedWorkTypeEvent event) {
        L.d("OnCheckedWorkTypeEvent");
        boolean selected = event.isSelected();
        WorkType workType = event.getWorkType();

        if (selected) {
            workTypeSet.add(workType);
        } else {
            workTypeSet.remove(workType);
        }
        notifyWorkTypeChanged();
    }

    /**
     *
     */
    public void notifyWorkTypeChanged() {

        Iterator<WorkType> iterator = workTypeSet.iterator();
        long totalMoney = 0;
        while (iterator.hasNext()) {
            WorkType next = iterator.next();
            Staff staff = next.getStaff();
            if (null != staff) {
                int money = Integer.parseInt(staff.getMoney());
                int count  = Integer.parseInt(staff.getStaff_account());
                totalMoney += money * count;
            }
        }
        totalMoneyTv.setText("总金额：" + totalMoney + " 元");
    }

}
