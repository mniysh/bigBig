package com.ms.ebangw.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.baidu.location.LocationClient;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.ReleaseProject;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.T;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 已发布的工程状态 待通过 进行中 已结束
 *
 * @author wangkai
 */
public class PublishedProjectStatusFragment extends BaseFragment {
    /**
     * 已发布工程的状态
     */
    private static final String PROJECT_STATUS = "project_status";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.ptr)
    PullToRefreshListView ptr;

    private String mParam1;
    private String mParam2;
    private View contentLayout;
    public LocationClient mLocationClient = null;
    private double latitude;
    private double longitude;
    private int currentPage = 1;


    public PublishedProjectStatusFragment() {
        // Required empty public constructor
    }

    public static PublishedProjectStatusFragment newInstance(String param1, String param2) {
        PublishedProjectStatusFragment fragment = new PublishedProjectStatusFragment();
        Bundle args = new Bundle();
        args.putString(PROJECT_STATUS, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(PROJECT_STATUS);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentLayout = inflater.inflate(R.layout.fragment_published_project_status, container, false);
        ButterKnife.bind(this, contentLayout);
        initView();
        initData();
        return contentLayout;
    }

    @Override
    public void initView() {
        ptr.setMode(PullToRefreshBase.Mode.BOTH);
        ptr.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
    }

    @Override
    public void initData() {
        this.mLocationClient = MyApplication.getInstance().mLocationClient;

        try {
            latitude = mLocationClient.getLastKnownLocation().getLatitude();
            longitude = mLocationClient.getLastKnownLocation().getLongitude();
        } catch (Exception e) {
            latitude = 0;
            longitude = 0;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void loadProjects(String projectStatus) {

        DataAccessUtil.projectStatus(currentPage + "", latitude + "", longitude + "", new
            JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    currentPage++;
                    try {
                        List<ReleaseProject> list = DataParseUtil.projectStatus(response);


                    } catch (ResponseException e) {
                        e.printStackTrace();
                        T.show(e.getMessage());
                    }

                }

                @Override
                public void onFinish() {
                    super.onFinish();
                }
            });
    }

    public void loadMoreProjects(String projectStatus) {

        DataAccessUtil.projectStatus(currentPage + "", latitude + "", longitude + "", new
            JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    currentPage ++ ;
                    try {
                        List<ReleaseProject> list = DataParseUtil.projectStatus(response);



                    } catch (ResponseException e) {
                        e.printStackTrace();
                        T.show(e.getMessage());
                    }

                }

                @Override
                public void onFinish() {
                    super.onFinish();
                }
            });
    }
}
