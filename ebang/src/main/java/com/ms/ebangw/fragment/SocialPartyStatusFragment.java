package com.ms.ebangw.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.adapter.ReleasedPartyAdapter;
import com.ms.ebangw.bean.Party;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 社交——发布活动的状态
 *
 * @author wangkai
 */
public class SocialPartyStatusFragment extends BaseFragment {
    public static final String PARTY_STATUS_UNDERWAY = "1";
    public static final String PARTY_STATUS_UNDER_REVIEW = "2";
    public static final String PARTY_STATUS_COMPLETE = "4";
    public static final String PARTY_STATUS_FAILURE = "3";

    private static final String KEY_PARTY_STATUS = "party_status";
    @Bind(R.id.ptr)
    PullToRefreshListView ptr;

    private String partyStatus;
    private int currentPage = 1;
    private ReleasedPartyAdapter adapter;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 发布的活动的状态：进行中， 审核中， 完成， 失败
     */
    @StringDef({PARTY_STATUS_UNDERWAY, PARTY_STATUS_UNDER_REVIEW, PARTY_STATUS_COMPLETE, PARTY_STATUS_FAILURE})
    public @interface PartyStatus {
    }

    public SocialPartyStatusFragment() {
        // Required empty public constructor
    }

    public static SocialPartyStatusFragment newInstance(@PartyStatus @Nullable String partyStatus) {
        SocialPartyStatusFragment fragment = new SocialPartyStatusFragment();
        Bundle args = new Bundle();
        args.putString(KEY_PARTY_STATUS, partyStatus);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            partyStatus = getArguments().getString(KEY_PARTY_STATUS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_social_party_status, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    @Override
    public void initView() {
        ptr.setMode(PullToRefreshBase.Mode.BOTH);
        ptr.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                load();
            }


            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadMore();
            }
        });
    }

    @Override
    public void initData() {
        adapter = new ReleasedPartyAdapter(mActivity, new ArrayList<Party>());
        ptr.setAdapter(adapter);
        load();
    }

    private void load() {
        currentPage = 1;
        DataAccessUtil.socialMyPartyList(currentPage + "", partyStatus, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                currentPage++;
                try {
                    List<Party> list = DataParseUtil.socialShow(response);
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
        });
    }

    private void loadMore() {

        DataAccessUtil.socialMyPartyList(currentPage + "", partyStatus, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    currentPage++;
                    try {
                        List<Party> list = DataParseUtil.socialShow(response);
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
                    ptr.onRefreshComplete();
                }
            });
    }
}
