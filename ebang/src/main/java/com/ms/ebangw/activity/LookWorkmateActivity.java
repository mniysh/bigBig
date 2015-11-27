package com.ms.ebangw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.adapter.ShowCraftAdapter;
import com.ms.ebangw.bean.ReleaseProject;
import com.ms.ebangw.bean.ShowedCraft;
import com.ms.ebangw.commons.Constants;
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
 * 查看工友 （劳务公司或工长）
 * @author wangkai
 */
public class LookWorkmateActivity extends BaseActivity {
    private Handler handler;
    private ReleaseProject releaseProject;
    private String currentType;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.slideBar)
    QuickindexBar slideBar;
    @Bind(R.id.tv_zimu)
    TextView tvZimu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_workmate);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            releaseProject = extras.getParcelable(Constants.KEY_RELEASED_PROJECT_STR);
            currentType = extras.getString(Constants.KEY_PROJECT_TYPE);
        }
        initView();
        initData();
    }

    @Override
    public void initView() {
        initTitle(null, "返回", "接受邀请工友", null, null);
        handler = new Handler();
    }

    @Override
    public void initData() {
        load();
    }

    private void load() {
        DataAccessUtil.showCraft(releaseProject.getId(), new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                showProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                dismissLoadingDialog();
                try {
                    List<ShowedCraft> workerList = DataParseUtil.showCraft(response);

                    if (null != workerList && workerList.size() > 0) {
                        initList(workerList);
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

    private void initList(final List<ShowedCraft> list) {
        Collections.sort(list);
        ShowCraftAdapter adapter = new ShowCraftAdapter(list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShowedCraft showedCraft = (ShowedCraft) view.getTag(Constants.KEY_SHOW_CRAFT);
                Intent intent = new Intent(LookWorkmateActivity.this, CheckWorkTypeUserActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.KEY_SHOWED_CRAFT, showedCraft);
                bundle.putString(Constants.KEY_PROJECT_ID, releaseProject.getId());
                bundle.putString(Constants.KEY_PROJECT_TYPE, currentType);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        slideBar.setOnSlideTouchListener(new QuickindexBar.OnSlideTouchListener() {

            @Override
            public void onBack(String str) {
                showZimu(str);
                if (list != null && list.size() > 0) {
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        if (list.get(i).getPinyin().substring(0, 1).equals(str)) {
                            listView.setSelection(i);
                            break;
                        }
                    }
                }
            }
        });
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

}
