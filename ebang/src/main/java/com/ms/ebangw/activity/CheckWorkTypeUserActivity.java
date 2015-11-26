package com.ms.ebangw.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.adapter.CheckWorkTypeUserAdapter;
import com.ms.ebangw.bean.CheckedWorkTypeUser;
import com.ms.ebangw.bean.People;
import com.ms.ebangw.bean.ShowedCraft;
import com.ms.ebangw.bean.Staff;
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
 * 查看各个工程的工种的人员
 *
 * @author wangkai
 */
public class CheckWorkTypeUserActivity extends BaseActivity {
    @Bind(R.id.tv_description_num)
    TextView tvDescriptionNum;
    private Handler handler;
    private ShowedCraft showedCraft;
    private String projectId;

    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.slideBar)
    QuickindexBar slideBar;
    @Bind(R.id.tv_zimu)
    TextView tvZimu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_work_type_user);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            showedCraft = extras.getParcelable(Constants.KEY_RELEASED_PROJECT_STR);
            projectId = extras.getString(Constants.KEY_PROJECT_ID);
        }
        initView();
        initData();
    }

    @Override
    public void initView() {

        if (showedCraft != null) {
            initTitle(null, "返回", showedCraft.getCraft_name(), null, null);
        }
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
        if (showedCraft != null) {

            DataAccessUtil.showPeople(projectId, showedCraft.getCraft_id(), new JsonHttpResponseHandler() {
                @Override
                public void onStart() {
                    showProgressDialog();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    dismissLoadingDialog();
                    try {

                        CheckedWorkTypeUser checkedWorkTypeUser = DataParseUtil.showPeople(response);
                        if (null != checkedWorkTypeUser) {
                            setNumDescription(checkedWorkTypeUser);
                            List<People> list = checkedWorkTypeUser.getDataList();
                            if (null != list && list.size() > 0) {
                                initPeoplesList(list);
                            }
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
    }

    private void setNumDescription(CheckedWorkTypeUser checkedWorkTypeUser) {
        if (null == checkedWorkTypeUser) return;
        String total_number = checkedWorkTypeUser.getTotal_number();
        String success_number = checkedWorkTypeUser.getSuccess_number();
        String dvalue_number = checkedWorkTypeUser.getDvalue_number();
        int anInt;
        try {
            anInt = Integer.parseInt(dvalue_number);
        } catch (Exception e) {
            anInt = 0;
        }
        if (anInt > 0) {
            SpannableString spannableString = new SpannableString("已邀请" + total_number + "人，接受邀请 " +
                "" + success_number + " 人，");
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#575757")), 0, spannableString.length()
                    - 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            SpannableString spannableString1 = new SpannableString("还差 " + dvalue_number + " 人");
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#be0102")), 0,
                spannableString1.length()
                    - 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilder.append(spannableString).append(spannableString1);
            tvDescriptionNum.setText(spannableStringBuilder);

            initTitle(null, "返回", showedCraft.getCraft_name(), "继续邀请", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CheckWorkTypeUserActivity.this, SelectWorkerActivity.class);
                    Bundle bundle = new Bundle();
                    Staff staff = new Staff();
                    staff.setCraft_id(showedCraft.getCraft_id());
                    staff.setProject_id(projectId);
                    bundle.putParcelable(Constants.KEY_RELEASED_PROJECT_STAFF, staff);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        } else {
            initTitle(null, "返回", showedCraft.getCraft_name(), null, null);
            tvDescriptionNum.setVisibility(View.GONE);
        }
    }

    private void initPeoplesList(final List<People> list) {
        Collections.sort(list);
        CheckWorkTypeUserAdapter adapter = new CheckWorkTypeUserAdapter(getUser().getCategory(), list,
            new CheckWorkTypeUserAdapter.OnAgreeListener() {
                @Override
                public void onAgree(People people) {
                    contactPeople();
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

    private void contactPeople() {

    }

}
