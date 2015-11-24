package com.ms.ebangw.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringDef;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.EvaluateActivity;
import com.ms.ebangw.activity.ProjectStatusActivity;
import com.ms.ebangw.adapter.PublishedProjectStatusAdapter;
import com.ms.ebangw.bean.ReleaseProject;
import com.ms.ebangw.commons.Constants;
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
public class ProjectStatusFragment extends BaseFragment {
    /**
     * 已发布工程的状态 待审核 wating_audit， 待通过， 进行中  已结束
     */
    public static final String AUDIT = "waiting_audit";
    public static final String WAITING = "sign_waiting";
    public static final String EXECUTE = "execute";
    public static final String COMPLETE = "complete";


    @Bind(R.id.ptr)
    PullToRefreshListView ptr;

    private String currentStatus = WAITING;
    private String currentType;
    private String currentInviteType;
    private View contentLayout;
    private int currentPage = 1;
    private PublishedProjectStatusAdapter adapter;


    public ProjectStatusFragment() {
        // Required empty public constructor
    }

    @StringDef({AUDIT, WAITING, EXECUTE, COMPLETE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ProjectStatus {
    }

    /**
     *
     * @param projectStatus
     * @param selectedType
     * @param inviteType
     * @return
     */
    public static ProjectStatusFragment newInstance(@ProjectStatus String
        projectStatus, @ProjectStatusActivity.SelectedType String selectedType, String inviteType) {
        ProjectStatusFragment fragment = new ProjectStatusFragment();
        Bundle args = new Bundle();
        args.putString(Constants.KEY_PROJECT_STATUS, projectStatus);
        args.putString(Constants.KEY_PROJECT_TYPE, selectedType);
        args.putString(Constants.KEY_PROJECT_TYPE_INVITE, inviteType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentStatus = getArguments().getString(Constants.KEY_PROJECT_STATUS);
            currentType = getArguments().getString(Constants.KEY_PROJECT_TYPE);
            if (TextUtils.equals(currentType, ProjectStatusActivity.TYPE_INVITE)) { //工人--》邀请我的
                currentInviteType = getArguments().getString(Constants.KEY_PROJECT_TYPE_INVITE);
            }else {
                currentInviteType = null;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentLayout = inflater.inflate(R.layout.fragment_project_status, container, false);
        ButterKnife.bind(this, contentLayout);
        initView();
        initData();
        return contentLayout;
    }

    @Override
    public void initView() {
        if (TextUtils.equals(currentStatus, EXECUTE)) {        //进行中只有一项
            ptr.setMode(PullToRefreshBase.Mode.DISABLED);
        }else {
            ptr.setMode(PullToRefreshBase.Mode.BOTH);
        }
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
        adapter = new PublishedProjectStatusAdapter(new ArrayList<ReleaseProject>(), currentStatus);
        adapter.setOnEvaluateClickListener(new PublishedProjectStatusAdapter.OnEvaluateClickListener() {
            @Override
            public void onGrabClick(View view, ReleaseProject releaseProject) { //评论
                Intent intent = new Intent(getActivity(), EvaluateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.KEY_RELEASED_PROJECT_STR, releaseProject);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        ptr.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        loadProjects();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void loadProjects() {
        currentPage = 1;
        DataAccessUtil.grabStatus(currentPage + "", currentStatus, currentType, currentInviteType, handler);
    }

    public void loadMoreProjects() {
        DataAccessUtil.grabStatus(currentPage + "", currentStatus,currentType, currentInviteType, loadMoreHandler);
    }

    private AsyncHttpResponseHandler handler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            currentPage++;
            try {
                List<ReleaseProject> list = DataParseUtil.grabStatus(response);
                if (adapter != null && list != null && list.size() > 0) {
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();
                }
            } catch (ResponseException e) {
                e.printStackTrace();
                T.show(e.getMessage());
            }
        }

        @Override
        public void onFinish() {
            super.onFinish();
            if (null != ptr) {
                ptr.onRefreshComplete();
            }
        }
    };

    private AsyncHttpResponseHandler loadMoreHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            currentPage++;
            try {
                List<ReleaseProject> list = DataParseUtil.grabStatus(response);
                if (adapter != null && list != null && list.size() > 0) {
                    adapter.getList().addAll(list);
                    adapter.notifyDataSetChanged();
                }
            } catch (ResponseException e) {
                e.printStackTrace();
                T.show(e.getMessage());
            }
        }

        @Override
        public void onFinish() {
            super.onFinish();
            if (null != ptr) {
                ptr.onRefreshComplete();
            }
        }
    };
}
