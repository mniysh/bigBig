package com.ms.ebangw.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.Worker;
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
 * 工长中心的工人管理列表
 *
 * @author wangkai
 */
public class RecommendedWorksActivity extends BaseActivity {


    @Bind(R.id.listView)
    ListView listView;

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
        initTitle("人员管理");

    }

    @Override
    public void initData() {

    }

    private void loadWorkers() {

        DataAccessUtil.recommendedWorkers(new JsonHttpResponseHandler(){
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

    private void initWorksList(List<Worker> workerList) {


    }

}
