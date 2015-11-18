package com.ms.ebangw.release;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
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
import com.ms.ebangw.utils.T;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import de.greenrobot.event.EventBus;

/**
 * 发布页面
 */
public class SelectCraftFragment extends BaseFragment {
    private static final String CATEGROY = "categroy";
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
    private long totalMoney = 0 ;

    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.tv_total_money)
    TextView totalMoneyTv;
    @Bind(R.id.rg_select_type)
    RadioGroup radioGroup;
    private String categroy;
    @Bind(R.id.btn_next)
    Button nextBt;



    public static SelectCraftFragment newInstance(String categroy, String param2) {
        SelectCraftFragment fragment = new SelectCraftFragment();
        Bundle args = new Bundle();
        args.putString(CATEGROY, categroy);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SelectCraftFragment() {
        // Required empty public constructor
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        initViewPager(craft);
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if (getArguments() != null) {
            categroy = getArguments().getString(CATEGROY);
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
        if(TextUtils.equals(categroy, "developers") || TextUtils.equals(categroy, "investor")){

            totalMoneyTv.setVisibility(View.VISIBLE);
            nextBt.setVisibility(View.VISIBLE);
            radioGroup.setVisibility(View.VISIBLE);
            initView();
            initData();
        }else{
            T.show("您的身份不正确，个人和开发商用户可发布信息。");

            totalMoneyTv.setVisibility(View.GONE);
            nextBt.setVisibility(View.GONE);
            radioGroup.setVisibility(View.GONE);
        }
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
        viewPager.setCurrentItem(0);
        initViewPager(craft);

    }

    @Override
    public void initData() {
        craft = getWorkType();
        releaseInfo = new DeveloperReleaseInfo();
        workTypeSet = new HashSet<>();
    }

    @OnClick(R.id.btn_next)
    public void goNext() {
        if(releaseGoNext() ){
//            Bundle bundle = new Bundle();
//            bundle.putString(Constants.KEY_RELEASE_PROJECT,getStaff(workTypeSet));

            ReleaseActivity homeActivity = (ReleaseActivity) mActivity;
            homeActivity.goDeveloperRelease(getStaff(workTypeSet), categroy);
        }



    }

    /**
     * json类型的staff字符串
     * @param workTypes
     * @return
     */
    public String  getStaff(Set<WorkType> workTypes){
        List<Staff> staffs = new ArrayList<Staff>();
        Gson g = new Gson();
        if(workTypes == null ){
            return null;
        }
        Iterator<WorkType> iterator = workTypes.iterator();
        WorkType workType ;
        while(iterator.hasNext()){
            workType = iterator.next();
            Staff staff = workType.getStaff();
            staffs.add(staff);
        }
        String string = g.toJson(staffs);
        L.d(string);

        return string ;
    }

    public Craft getWorkType() {

            DataAccessUtil.publishCraft(new JsonHttpResponseHandler() {
                @Override
                public void onStart() {
                    super.onStart();
                    showProgressDialog();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        craft = DataParseUtil.publishCraft(response);

//                        MyApplication.getInstance().setCraft(craft);
                        initViewPager(craft);
                        dismissLoadingDialog();
                    } catch (ResponseException e) {
                        e.printStackTrace();
                        T.show(e.getMessage());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    L.d(responseString);
                    dismissLoadingDialog();
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


        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(position);
                radioButton.setChecked(true);
            }
        });
        viewPager.setAdapter(pagerAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewPager.removeAllViews();
    }

    /**
     *处理发布的工种
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

    public Set<WorkType> getWorkTypeSet(){
        return workTypeSet;
    }


    public boolean releaseGoNext(){
        if(workTypeSet == null || workTypeSet.size() == 0){
            T.show("至少选择一个工种");
            return false;
        }
        return true;
    }

    /**
     *
     */
    public void notifyWorkTypeChanged() {

        Iterator<WorkType> iterator = workTypeSet.iterator();
        totalMoney = 0;
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
