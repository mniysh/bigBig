package com.ms.ebangw.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.adapter.AccountDetailAdapter;
import com.ms.ebangw.bean.Trade;
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
 * 交易明细
 *
 * @author wangkai
 */
public class AccountDetailActivity extends BaseActivity {


    @Bind(R.id.ptr)
    PullToRefreshListView ptr;
    private int currentPage = 1;
    private AccountDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);
        ButterKnife.bind(this);
        initView();
        initData();
    }


    @Override
    public void initView() {
        initTitle(null, "返回", "交易明细", null, null);
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
        adapter = new AccountDetailAdapter(this, new ArrayList<Trade>());
        ptr.setAdapter(adapter);
        load();
    }

    private void load() {
        currentPage = 1;
        DataAccessUtil.tradeDetail(currentPage + "", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                currentPage++;
                try {
                    List<Trade> list = DataParseUtil.tradeDetail(response);
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
        });
    }

    private void loadMore() {

        DataAccessUtil.tradeDetail(currentPage + "", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                currentPage++;
                try {
                    List<Trade> list = DataParseUtil.tradeDetail(response);
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
