package com.ms.ebangw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.HomeActivity;
import com.ms.ebangw.activity.MessageCenterActivit;
import com.ms.ebangw.activity.NextPageActivity;
import com.ms.ebangw.adapter.HomeListAdapter;
import com.ms.ebangw.bean.FoundBean;
import com.ms.ebangw.bean.HomeProjectInfo;
import com.ms.ebangw.bean.RecommendedDeveoper;
import com.ms.ebangw.bean.ReleaseProject;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.MyListView;
import com.ms.ebangw.utils.T;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * 主页的主页页面
 */
public class HomeFragment extends BaseFragment implements OnClickListener {
    private MyListView mylistview;
    private int[] images = {R.drawable.banner_aaa, R.drawable.banner_bb
    };// 滑动图片数据
    private int[] imgclass = {R.drawable.home_build, R.drawable.home_zxiu, R.drawable.home_life, R.drawable.home_business};
    private String[] txtclass = {"建筑", "装修", "生活", "商业"};
    private List<FoundBean> datas;
    private View mContentView;

    @Bind(R.id.home_search)
    EditText etSearch;
    @Bind(R.id.iv_building)
    ImageView buildingIv;
    @Bind(R.id.iv_decorater)
    ImageView decoratorIv;
    @Bind(R.id.iv_projectManage)
    ImageView projectManageIv;
    @Bind(R.id.iv_other)
    ImageView otherIv;


    /**
     * 消息按钮
     */
    @OnClick(R.id.tv_message)
    public void toastMessage() {
        Intent intent = new Intent(mActivity, MessageCenterActivit.class);

        startActivity(intent);
    }

    @OnClick(R.id.home_search)
    public void startSearch() {
        T.show("开始搜索");
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, mContentView);
        initView();
        initData();
        return mContentView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadHomeProjectInfo();
    }

    public void initView() {


        buildingIv.setOnClickListener(this);
        decoratorIv.setOnClickListener(this);
        projectManageIv.setOnClickListener(this);
        otherIv.setOnClickListener(this);
    }

    @Override
    public void initData() {
        List<View> pager = new ArrayList<>();
        datas = new ArrayList<FoundBean>();
        FoundBean fb = new FoundBean();
        fb.setTitle("不锈钢玻璃隔断");
        fb.setArea("1.4公里");
        fb.setContent("工程简介");
        fb.setMoney("200元/天");
        fb.setQiangdan("已有5人抢单");
        datas.add(fb);


    }

    public void initBanner() {
        mylistview.setAdapter(new HomeListAdapter((HomeActivity) mActivity, datas));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        butterknife.ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mActivity, NextPageActivity.class);
        Bundle bundle = new Bundle();
        switch (v.getId()) {
//            case R.id.fragment_home_lin_recommend01:
//            case R.id.fragment_home_lin_recommend02:
//            case R.id.fragment_home_lin_recommend03:
//                startActivity(new Intent(mActivity, RecommendActivity.class));
//                break;
//            case R.id.iv_building:
//                intent.putExtras(bundle);
//                startActivity(intent);
//                break;
//            case R.id.iv_decorater:
//                intent.putExtras(bundle);
//                startActivity(intent);
//                break;
//            case R.id.iv_projectManage:
//                intent.putExtras(bundle);
//                startActivity(intent);
//                break;
//            case R.id.iv_other:
//                intent.putExtras(bundle);
//                startActivity(intent);
//
//                break;
//            default:
//                break;
        }
    }

    public void loadHomeProjectInfo() {
        DataAccessUtil.homeProjectInfo(new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                showProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                dismissLoadingDialog();
                try {
                    HomeProjectInfo info = DataParseUtil.homeProjectInfo(response);
                    if (null != info) {
                        List<RecommendedDeveoper> deveoperList = info.getDevelopers();
                        List<ReleaseProject> projectList = info.getProject();
                        setRecommendedDeveploersInfo(deveoperList);
                    }
                } catch (ResponseException e) {
                    e.printStackTrace();
                    T.show(e.getMessage());
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                dismissLoadingDialog();
            }
        });
    }

    private void setRecommendedDeveploersInfo(List<RecommendedDeveoper> deveoperList) {
        if (null != deveoperList && deveoperList.size() > 0 && null != mActivity) {

//            RecommendedDeveloperAdapter developerAdapter = new RecommendedDeveloperAdapter(mActivity,deveoperList);
//            rvDevelopers.setAdapter(developerAdapter);

        }
    }



}
