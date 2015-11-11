package com.ms.ebangw.fragment;


import android.os.Bundle;
import android.support.annotation.StringDef;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.adapter.PublishedProjectStatusAdapter;
import com.ms.ebangw.bean.ReleaseProject;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.T;

import org.apache.http.Header;
import org.json.JSONObject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
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
     * 已发布工程的状态 待通过， 进行中  已结束
     */
    public static final String WAITTING = "waiting";
    public static final String EXECUTE = "execute";
    public static final String COMPLETE = "complete";

    private static final String PROJECT_STATUS = "project_status";

    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.ptr)
    PullToRefreshListView ptr;

    private String status = WAITTING;
    private View contentLayout;
    private int currentPage = 1;


    public PublishedProjectStatusFragment() {
        // Required empty public constructor
    }

    @StringDef({WAITTING, EXECUTE, COMPLETE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ProjectStatus {
    }

    public static PublishedProjectStatusFragment newInstance(String projectStatus) {
        PublishedProjectStatusFragment fragment = new PublishedProjectStatusFragment();
        Bundle args = new Bundle();
        args.putString(PROJECT_STATUS, projectStatus);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            status = getArguments().getString(PROJECT_STATUS);
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
                loadProjects();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadMoreProjects();
            }
        });
    }

    @Override
    public void initData() {
        new PublishedProjectStatusAdapter(new ArrayList<ReleaseProject>(), status);


        loadProjects();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void loadProjects() {
        switch (status) {
            case WAITTING:
                DataAccessUtil.projectStatusWaiting(currentPage + "", handler);
                break;
            
            case EXECUTE:
                DataAccessUtil.projectStatusExecute(handler);
                break;
            
            case COMPLETE:
                DataAccessUtil.projectStatusComplete(currentPage + "", handler);
                break;
        }
    }

    public void loadMoreProjects() {
        switch (status) {
            case WAITTING:
                DataAccessUtil.projectStatusWaiting(currentPage + "", loadMoreHandler);
                break;

            case EXECUTE:
                DataAccessUtil.projectStatusExecute(loadMoreHandler);
                break;

            case COMPLETE:
                DataAccessUtil.projectStatusComplete(currentPage + "", loadMoreHandler);
                break;
        }
    }

    private AsyncHttpResponseHandler handler = new JsonHttpResponseHandler() {
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
    };

    private AsyncHttpResponseHandler loadMoreHandler = new JsonHttpResponseHandler() {
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
    };
}
