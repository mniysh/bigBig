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
import android.widget.ListView;
import android.widget.RadioGroup;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.adapter.CraftAdapter;
import com.ms.ebangw.adapter.ReleaseFragmentAdapter;
import com.ms.ebangw.adapter.ReleaseListAdapter;
import com.ms.ebangw.bean.Craft;
import com.ms.ebangw.bean.WorkType;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.Utility;

import org.apache.http.Header;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * homeactivity跳转过来的第一个页面
 */
public class ReleaseFrament01 extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ReleaseBuildingFragment releaseBuildingFragment;
    private ReleaseDecorateFragment releaseDecorateFragment;
    private ReleaseProjectManageFragment releaseProjectManageFragment;
    private ReleaseOtherFragment releaseOtherFragment;
    private FragmentManager fragmentManager;
    private List<Fragment> datas;
    private ReleaseFragmentAdapter releaseFragmentAdapter;
    //private ViewPager viewPager;
    private RadioGroup radioGroup;
    private ListView listView;
    private CraftAdapter craftAdapter;

    private Craft craft;


    public static ReleaseFrament01 newInstance(String param1, String param2) {
        ReleaseFrament01 fragment = new ReleaseFrament01();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ReleaseFrament01() {
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

        return inflater.inflate(R.layout.fragment_release_frament01, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void initView() {
        initTitle(null, "", "发布", null, null);

        radioGroup= (RadioGroup) getView().findViewById(R.id.rg_but);
        listView= (ListView) getView().findViewById(R.id.lv_listview);


    }

    @Override
    public void initData() {
        getWorkType();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (craftAdapter == null || craft == null) {
                    return;
                }
                switch (checkedId) {
                    case R.id.rb_build:
                        craftAdapter.setWorkType(craft.getBuilding());
                        break;
                    case R.id.rb_decorate:
                        craftAdapter.setWorkType(craft.getFitment());
                        break;
                    case R.id.rb_projectManager:
                        craftAdapter.setWorkType(craft.getProjectManage());
                        break;

                }
                Utility.setlistview(listView);
                craftAdapter.notifyDataSetChanged();
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

    public Craft getWorkType() {
        DataAccessUtil.publishCraft(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    craft = DataParseUtil.publishCraft(response);
                    craftAdapter = new CraftAdapter(craft.getBuilding());
                    listView.setAdapter(craftAdapter);
                    Utility.setlistview(listView);

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
}
