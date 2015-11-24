package com.ms.ebangw.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.adapter.PeopleCategoryAdapter;
import com.ms.ebangw.bean.People;
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
 * 工长或劳务公司列表  (工人,开发商）
 *
 * @author wangkai
 */
public class PeopleCategoryActivity extends BaseActivity {

    public static final String LIST_TYPE_HEADMAN = "headman";  //查看工长还是劳务公司
    public static final String LIST_TYPE_COMPANY = "company";
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.slideBar)
    QuickindexBar slideBar;
    @Bind(R.id.tv_zimu)
    TextView tvZimu;

    private String listType;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_category);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            listType = extras.getString(Constants.KEY_CATEGORY_LIST_TYPE, LIST_TYPE_HEADMAN);
        }

    }

    @Override
    public void initView() {
        initTitle(null, "返回", "人员管理", null, null);
        handler = new Handler();
    }

    @Override
    public void initData() {
        load();
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

    private void load() {

        DataAccessUtil.peopleCategory("", "", new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                showProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                dismissLoadingDialog();
                try {
                    List<People> list = DataParseUtil.peopleCategory(response);

                    if (null != list && list.size() > 0) {
                        initPeoplesList(list);
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

    private void initPeoplesList(final List<People> list) {
        Collections.sort(list);
        PeopleCategoryAdapter adapter = new PeopleCategoryAdapter(list, new PeopleCategoryAdapter.OnAgreeListener() {
            @Override
            public void onAgree(People people) {

            }
        });
        listView.setAdapter(adapter);
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
}
