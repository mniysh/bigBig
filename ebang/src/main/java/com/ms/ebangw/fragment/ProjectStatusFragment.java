package com.ms.ebangw.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringDef;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.EvaluateActivity;
import com.ms.ebangw.activity.LookWorkmateActivity;
import com.ms.ebangw.activity.ProjectStatusActivity;
import com.ms.ebangw.activity.ShowActivity;
import com.ms.ebangw.activity.ShowDeveloperActivity;
import com.ms.ebangw.adapter.PublishedProjectStatusAdapter;
import com.ms.ebangw.bean.ReleaseProject;
import com.ms.ebangw.center.worker.InviteMineUserActivity;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.L;

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
    private String category;

    public ProjectStatusFragment() {
        // Required empty public constructor
    }

    @StringDef({AUDIT, WAITING, EXECUTE, COMPLETE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ProjectStatus {
    }

    /**
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
            } else {
                currentInviteType = null;
            }
        }
        category = getUser().getCategory();
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
        } else {
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
        adapter = new PublishedProjectStatusAdapter(new ArrayList<ReleaseProject>(), category,
            currentType, currentStatus, currentInviteType);
        setAdapterListeners();
        ptr.setAdapter(adapter);

    }

    private void setAdapterListeners() {

        String category = getUser().getCategory();
        if (TextUtils.isEmpty(category)) {
            return;
        }

        if (TextUtils.equals(category, Constants.WORKER)) {   //工人
            setWorkerAdapterListener();
        }

        if (TextUtils.equals(category, Constants.DEVELOPERS) || TextUtils.equals(category, Constants.INVESTOR)) {//个人与开发商逻辑一样
            setDevelopersAdapterListener();
        }

        if (TextUtils.equals(category, Constants.COMPANY)) {   //劳务公司
            setLabourCompanyAdapterListener();
        }

        if (TextUtils.equals(category, Constants.HEADMAN)) {   //工长
            setHeadmanAdapterListener();
        }

    }

    private void setHeadmanAdapterListener() {
        switch (currentStatus) {
            case AUDIT:
                break;
            case WAITING:
                setLookWorkmateListener();
                break;
            case EXECUTE:
                setLookWorkmateListener();
                setOnItemClickListener();
                break;
            case COMPLETE:
                setEvaluateListener();
                break;
        }
    }

    private void setLabourCompanyAdapterListener() {
        if (TextUtils.equals(currentType, ProjectStatusActivity.TYPE_GRAB)) {
            switch (currentStatus) {
                case WAITING:
                case EXECUTE:
                    setLookWorkmateListener();
                    break;
                case COMPLETE:
                    setEvaluateListener();
                    break;
            }
        }
    }

    private void setDevelopersAdapterListener() {
        if (TextUtils.equals(currentType, ProjectStatusActivity.TYPE_PUBLISH)) {
            switch (currentStatus) {
                case WAITING:
                    setInviteMineUserListener();
                    break;

                case EXECUTE:
                    setContactListener();
                    break;

                case COMPLETE:
                    setEvaluateListener();
                    break;


            }
        }
    }

    private void setWorkerAdapterListener() {
        if (TextUtils.equals(currentType, ProjectStatusActivity.TYPE_GRAB)) {   //工人抢单
            if (TextUtils.equals(currentStatus, COMPLETE)) {
                setEvaluateListener();
            }
        }

        if (TextUtils.equals(currentType, ProjectStatusActivity.TYPE_INVITE)) { //邀请我的

            if (TextUtils.equals(currentInviteType, ProjectStatusActivity.INVITE_TYPE_INVITE)) {
                //待接受邀请的
                switch (currentStatus) {
                    case WAITING:
                    case EXECUTE:
                        setInviteMineUserListener();
                        break;
                    case COMPLETE:  //已完成
                        setEvaluateListener();
                        break;
                }

            } else { //已接受邀请的
                setContactListener();   //点击联系
            }
        }
        //邀请相关
    }

    /**
     * 查看工友 （劳务公司，工长）
     */
    private void setLookWorkmateListener() {
        adapter.setOnEvaluateClickListener(new PublishedProjectStatusAdapter.OnEvaluateClickListener() {
            @Override
            public void onGrabClick(View view, ReleaseProject releaseProject) {
                Intent intent = new Intent(mActivity, LookWorkmateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.KEY_RELEASED_PROJECT_STR, releaseProject);
                bundle.putString(Constants.KEY_PROJECT_TYPE, currentType);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    /**
     * (工人)查看邀请我的 或 (开发商)查看抢单人员
     */
    private void setInviteMineUserListener() {
        adapter.setOnEvaluateClickListener(new PublishedProjectStatusAdapter.OnEvaluateClickListener() {
            @Override
            public void onGrabClick(View view, ReleaseProject releaseProject) { //
                Intent intent = new Intent(getActivity(), InviteMineUserActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.KEY_RELEASED_PROJECT_STR, releaseProject);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    /**
     * 设置点击评价
     */
    private void setEvaluateListener() {
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
    }

    /**
     * 点击工程item跳转事件
     */
    private void setOnItemClickListener() {
        ptr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReleaseProject project = (ReleaseProject) view.getTag(Constants.KEY_RELEASED_PROJECT);
                String category = getUser().getCategory();
                Intent intent;
                if(TextUtils.equals(category,Constants.DEVELOPERS) || TextUtils.equals(category, Constants.INVESTOR)){
                    intent = new Intent(getActivity(), ShowDeveloperActivity.class);
                }else{
                    intent = new Intent(getActivity(), ShowActivity.class);
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.KEY_RELEASED_PROJECT_STR, project);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    /**
     * 点击联系
     */
    private void setContactListener() {
        adapter.setOnEvaluateClickListener(new PublishedProjectStatusAdapter.OnEvaluateClickListener() {
            @Override
            public void onGrabClick(View view, ReleaseProject releaseProject) { //评论
//                Intent intent = new Intent(getActivity(), EvaluateActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putParcelable(Constants.KEY_RELEASED_PROJECT_STR, releaseProject);
//                intent.putExtras(bundle);
//                startActivity(intent);
            }
        });
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
        DataAccessUtil.grabStatus(currentPage + "", currentStatus, currentType, currentInviteType, loadMoreHandler);
    }

    private AsyncHttpResponseHandler handler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            currentPage++;
            try {
                List<ReleaseProject> list = DataParseUtil.grabStatus(response);
                if (adapter != null && list != null) {
                    adapter.setList(list);
                }else {
                    adapter.getList().clear();
                }
                adapter.notifyDataSetChanged();

            } catch (ResponseException e) {
                e.printStackTrace();
                L.d(e.getMessage());
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
                L.d(e.getMessage());
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
