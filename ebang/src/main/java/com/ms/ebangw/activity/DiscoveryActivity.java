package com.ms.ebangw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baidu.location.LocationClient;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.adapter.ProjectItemAdapter;
import com.ms.ebangw.bean.ReleaseProject;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.T;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * 发现
 *
 * @author wangkai
 */
public class DiscoveryActivity extends BaseActivity {

    @Bind(R.id.listView)
    PullToRefreshListView listView;

    public LocationClient mLocationClient = null;
    private int currentPage = 0;
    private ProjectItemAdapter projectItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        this.mLocationClient = MyApplication.getInstance().mLocationClient;
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                load();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadMore();
            }
        });

        projectItemAdapter = new ProjectItemAdapter(new ArrayList<ReleaseProject>());
        projectItemAdapter.setOnGrabClickListener(new ProjectItemAdapter.OnGrabClickListener() {
            @Override
            public void onGrabClick(View view, ReleaseProject releaseProject) {
                Intent intent = new Intent(DiscoveryActivity.this, QiangDanActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.KEY_RELEASED_PROJECT_STR, releaseProject);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        listView.setAdapter(projectItemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReleaseProject project = (ReleaseProject) view.getTag(Constants.KEY_RELEASED_PROJECT);
                Intent intent = new Intent(DiscoveryActivity.this, ShowActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.KEY_RELEASED_PROJECT_STR, project);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        load();
    }

    private void load() {
        double latitude;
        double longitude;
        try {
            latitude = mLocationClient.getLastKnownLocation().getLatitude();
            longitude = mLocationClient.getLastKnownLocation().getLongitude();
        } catch (Exception e) {
            latitude = 0;
            longitude = 0;
        }
        DataAccessUtil.founds(null, null, "1", latitude + "", longitude + "", new
            JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    currentPage++;
                    try {
                        List<ReleaseProject> releaseProjects = DataParseUtil.founds(response);
                        if (null != releaseProjects && releaseProjects.size() > 0) {
                            setRecommendedDevelopersProjects(releaseProjects);
                        }
                    } catch (ResponseException e) {
                        e.printStackTrace();
                        T.show(e.getMessage());
                    }
                }

                @Override
                public void onStart() {
                    super.onStart();
                    showProgressDialog();
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    listView.onRefreshComplete();
                    dismissLoadingDialog();
                }
            });


    }

    private void loadMore() {
        double latitude;
        double longitude;
        try {
            latitude = mLocationClient.getLastKnownLocation().getLatitude();
            longitude = mLocationClient.getLastKnownLocation().getLongitude();
        } catch (Exception e) {
            latitude = 0;
            longitude = 0;
        }
        DataAccessUtil.founds(null, null, currentPage + "", latitude + "", longitude + "", new
            JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    currentPage ++ ;
                    try {
                        List<ReleaseProject> projectList = DataParseUtil.founds(response);
                        projectItemAdapter.getList().addAll(projectList);
                        projectItemAdapter.notifyDataSetChanged();

                    } catch (ResponseException e) {
                        e.printStackTrace();
                        T.show(e.getMessage());
                    }
                }

                @Override
                public void onStart() {
                    super.onStart();
                    showProgressDialog();
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    listView.onRefreshComplete();
                    dismissLoadingDialog();
                }
            });
    }

    private void setRecommendedDevelopersProjects(List<ReleaseProject> projectList) {
        if (projectItemAdapter != null && projectList != null && projectList.size() > 0) {
            projectItemAdapter.setList(projectList);
            projectItemAdapter.notifyDataSetChanged();
        }
    }
}
