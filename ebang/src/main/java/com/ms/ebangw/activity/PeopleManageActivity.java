package com.ms.ebangw.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.adapter.RecommendedWorkersAdapter;
import com.ms.ebangw.bean.Worker;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.view.QuickindexBar;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * 工长中心的工人管理列表
 *
 * @author wangkai
 */
public class PeopleManageActivity extends BaseActivity {
    private Handler handler;

    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.slideBar)
    QuickindexBar slideBar;
    @Bind(R.id.tv_zimu)
    TextView tvZimu;
    private String project_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommened_works);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void initView() {
        initTitle(null, "返回", "人员管理", null, null);
        handler = new Handler();
    }

    @Override
    public void initData() {
        loadWorkers();
    }

    // 显示在屏幕中间的字母
    private void showZimu(String string) {

        tvZimu.setVisibility(View.VISIBLE);
        tvZimu.setText(string);
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                tvZimu.setVisibility(View.GONE);
            }
        }, 1500);
    }

    private void loadWorkers() {

        DataAccessUtil.recommendWorkers(new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                showProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                dismissLoadingDialog();
                try {
                    List<Worker> workerList = DataParseUtil.recommendedWorkers(response);

                    if (null != workerList && workerList.size() > 0) {
                        initWorksList(workerList);
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

    private void initWorksList(final List<Worker> workerList) {
        Collections.sort(workerList);
        RecommendedWorkersAdapter adapter = new RecommendedWorkersAdapter(workerList, new RecommendedWorkersAdapter.OnRemoveRelationListener() {
            @Override
            public void onRemove(Worker worker) {
                removeRelation(worker.getId());
            }


        });
        listView.setAdapter(adapter);
        slideBar.setOnSlideTouchListener(new QuickindexBar.OnSlideTouchListener() {

            @Override
            public void onBack(String str) {
                showZimu(str);
                if (workerList != null && workerList.size() > 0) {
                    int size = workerList.size();
                    for (int i = 0; i < size; i++) {
                        if (workerList.get(i).getPinyin().substring(0, 1).equals(str)) {
                            listView.setSelection(i);
                            break;
                        }
                    }
                }
            }
        });
    }

    /**
     * 解除关系
     */
    private void removeRelation(String workerId) {

        DataAccessUtil.removeRelation(workerId, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                showProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                dismissLoadingDialog();
                try {
                    String recommend = DataParseUtil.removeRelation(response);  // 0:推荐工长的工人小于约定人数   1:推荐工长的工人大于等于约定人

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

}
