package com.ms.ebangw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.adapter.InviteWorkersAdapter;
import com.ms.ebangw.bean.Staff;
import com.ms.ebangw.bean.Worker;
import com.ms.ebangw.bean.WorkerFriend;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.view.QuickindexBar;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * 工长抢单选择工友页面
 *
 * @author yangshaohua
 */
public class SelectWorkerActivity extends BaseActivity {
    private Handler handler;

    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.slideBar)
    QuickindexBar slideBar;
    @Bind(R.id.tv_zimu)
    TextView tvZimu;
    private String project_id;
    private String craft_id;
    private Staff staff;
    private InviteWorkersAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_worker);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    @Override
    public void initView() {
        initTitle(null, "返回", "邀请工友", null, null);
        handler = new Handler();
        Intent intent = getIntent();
        staff = intent.getExtras().getParcelable(Constants.KEY_RELEASED_PROJECT_STAFF);
        project_id = staff.getProject_id();
        craft_id = staff.getCraft_id();


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

        DataAccessUtil.friendWorkers(project_id, craft_id, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                showProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                dismissLoadingDialog();
                try {
                    WorkerFriend workerFriend = DataParseUtil.friendWorker(response);
                    List<Worker> workerList = workerFriend.getWorkers();

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
//        Collections.sort(workerList);
        adapter = new InviteWorkersAdapter(workerList, new InviteWorkersAdapter.OnRemoveRelationListener() {
//            @Override
//            public void onAgree(Worker worker) {
//                removeRelation(worker.getId());
//            }

            @Override
            public void onAdd(Worker worker) {
                inviteWorker(worker.getId());

            }


        });
        listView.setAdapter(adapter);
//        slideBar.setOnSlideTouchListener(new QuickindexBar.OnSlideTouchListener() {
//
//            @Override
//            public void onBack(String str) {
//                showZimu(str);
//                if (workerList != null && workerList.size() > 0) {
//                    int size = workerList.size();
//                    for (int i = 0; i < size; i++) {
//                        if (workerList.get(i).getPinyin().substring(0, 1).equals(str)) {
//                            listView.setSelection(i);
//                            break;
//                        }
//                    }
//                }
//            }
//        });
    }
    private void inviteWorker(String workId){

        DataAccessUtil.inviteWorker(workId,project_id, craft_id ,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    boolean b = DataParseUtil.processDataResult(response);
                    if(b){
                        T.show("消息已发送");
                        adapter.notifyDataSetChanged();
                        loadWorkers();

                    }
                } catch (ResponseException e) {
                    e.printStackTrace();
                    T.show(e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                L.d(responseString);
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
