package com.ms.ebangw.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.adapter.Evaluate;
import com.ms.ebangw.adapter.EvaluateListAdapter;
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
 * 收到评价
 * @author wangkai
 */
public class EvaluateListActivity extends BaseActivity {

    @Bind(R.id.ptr)
    PullToRefreshListView ptr;
    private int currentPage = 1;
    private EvaluateListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_list);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void initView() {
        initTitle(null, "返回", "收到评价", null, null);


    }

    @Override
    public void initData() {
        ptr.setMode(PullToRefreshBase.Mode.BOTH);
        ptr.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadEvaluate();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadMoreEvaluate();
            }
        });

        adapter = new EvaluateListAdapter(new ArrayList<Evaluate>());
        ptr.setAdapter(adapter);

    }

    private void loadEvaluate() {
        currentPage = 1;
        DataAccessUtil.evaluateList(currentPage + "", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                currentPage++;
                try {
                    List<Evaluate> list = DataParseUtil.evaluateList(response);
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
        });
    }

    private void loadMoreEvaluate() {
        DataAccessUtil.evaluateList(currentPage + "", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                currentPage++;
                try {
                    List<Evaluate> list = DataParseUtil.evaluateList(response);
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
                ptr.onRefreshComplete();
            }
        } );
    }
}
