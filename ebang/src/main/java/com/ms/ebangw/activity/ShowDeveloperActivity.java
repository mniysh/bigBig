package com.ms.ebangw.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.adapter.ProjectItemdetailAdapter;
import com.ms.ebangw.adapter.SelectHeadmanAdapter;
import com.ms.ebangw.bean.HeadmanEven;
import com.ms.ebangw.bean.Message;
import com.ms.ebangw.bean.ProjectInfoDetail;
import com.ms.ebangw.bean.ReleaseProject;
import com.ms.ebangw.bean.SelectHeadman;
import com.ms.ebangw.bean.Staff;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 企业和个人的展示页面
 *
 *
 * @author admin xupeng
 */
public class ShowDeveloperActivity extends BaseActivity {

    @Bind(R.id.show_list)
    ListView showListView;
    ProjectItemdetailAdapter detailAdapter;
    @Bind(R.id.tv_title)
    TextView tTitle;
    @Bind(R.id.tv_address)
    TextView tAddress;
    @Bind(R.id.tv_start_time)
    TextView tStartTime;
    @Bind(R.id.tv_end_time)
    TextView tEndTime;
    @Bind(R.id.tv_description)
    TextView tDescription;
    @Bind(R.id.tv_link_phone)
    TextView tLinkPhone;
    @Bind(R.id.tv_link_man)
    TextView tLinkman;
    @Bind(R.id.iv_one)
    ImageView iOneImg;
    @Bind(R.id.tv_count_qiangdan)
    TextView qiangDanTv;
    @Bind(R.id.tv_count_qiangdan2)
    TextView qiangDanTv2;
    @Bind(R.id.iv_two)
    ImageView iTwoImg;
    @Bind(R.id.ll_below_show)
    LinearLayout lBelowShow;
    String imageUrl;
    @Bind(R.id.ll_show_headman)
    LinearLayout llShowHeadman;
    @Bind(R.id.ll_below_show2)
    LinearLayout llHideHeadman;
    @Bind(R.id.gridView)
    GridView gridView;
    @Bind(R.id.but_select)
    Button selectBt;


    private ReleaseProject releaseProject;
    String projectId;
    private User user;
    private String category;
    private Staff staff;
    private String projectType;
    private String userId;
    private IntentFilter filter;
    private List<SelectHeadman> headmans;
    private SelectHeadmanAdapter adapter;
    private int headmanCount;
    private SelectHeadman headman;
    private LinearLayout window;
    private Button confirmBt, cancleBt;
    private TextView messgaeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_show_developer);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        user = getUser();
        category = user.getCategory();
        userId = user.getId();

        Intent intent = getIntent();
        if (intent != null) {

            releaseProject = intent.getExtras().getParcelable(Constants.KEY_RELEASED_PROJECT_STR);
        }
        if (releaseProject != null) {
            projectId = releaseProject.getId();
            projectType = releaseProject.getProject_type();
        }
        showListView.setVisibility(View.GONE);
        if (TextUtils.equals(projectType, Constants.HEADMAN)) {
            loadOther();
        } else {
            loadDevelopers();
        }
        initView();
        initViewOper();
        initData();
    }

    private void initViewOper() {
        initTitle(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDeveloperActivity.this.finish();
            }
        }, "返回", "企业信息", null, null);
    }


    public void initView() {
        window = (LinearLayout) this.getLayoutInflater().inflate(R.layout.window_select_headman, null, false);
        confirmBt = (Button) window.findViewById(R.id.bt_confirm);
        cancleBt = (Button) window.findViewById(R.id.bt_cancel);
        messgaeTv = (TextView) window.findViewById(R.id.tv_message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initData() {
        lBelowShow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                llShowHeadman.setVisibility(View.VISIBLE);
            }
        });
        llHideHeadman.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                llShowHeadman.setVisibility(View.GONE);
            }
        });
        selectBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (headman != null) {
                    if (TextUtils.equals(headman.getContend_status(), Constants.CONTEND_STATUS_SUCCEED)) {
                        T.show("您已选择了工长");
                        return;
                    }
                    showWindow();
                } else {
                    T.show("请选择");
                }
            }
        });


    }

    private void showWindow() {
        final PopupWindow pw = new PopupWindow(window, 331, 345);
        pw.setBackgroundDrawable(new BitmapDrawable());

        pw.showAtLocation(selectBt, Gravity.CENTER_VERTICAL, 0, 0);
        messgaeTv.setText("确定雇佣" + "\r\n" + headman.getReal_name() + "吗？");
        confirmBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectHeadman();
                pw.dismiss();
                backgroundAlpha(1.0f);
            }
        });
        cancleBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pw.dismiss();
                backgroundAlpha(1.0f);
            }
        });
        backgroundAlpha(0.5f);

    }

    private void selectHeadman() {
        String project_id = headman.getProject_id();
        String contend_id = headman.getId();

        DataAccessUtil.selectHeadman(project_id, contend_id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    boolean b = DataParseUtil.processDataResult(response);
                    if (b) {
                        T.show("选择工长成功");
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

    //调用2-23接口（projectType返回值是headman）
    private void loadOther() {
        DataAccessUtil.projectInfoDetailInvistor(projectId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    ProjectInfoDetail detail = DataParseUtil.projectInfoDetail(response);
                    if (null != detail) {
                        int developersId = detail.getDevelopers_id();
                        lBelowShow.setVisibility(View.GONE);
                        tTitle.setText(detail.getTitle());
                        tAddress.setText(detail.getAddress());
                        tDescription.setText(detail.getDescription());
                        tEndTime.setText(detail.getEnd_time());
                        tStartTime.setText(detail.getStart_time());
                        tLinkman.setText(detail.getLink_man());
                        tLinkPhone.setText(detail.getLink_phone());
                        if (detail.getImages() != null) {
                            imageUrl = detail.getImages().get(0);
                        }
//                        iOneImg.setImageURI(detail.getImages());
                        if (!TextUtils.isEmpty(imageUrl)) {
                            Picasso.with(ShowDeveloperActivity.this).load(DataAccessUtil.getImageUrl(imageUrl)).placeholder(R.drawable.head).into(iOneImg);
//                            Picasso.with(ShowActivity.this).load(DataAccessUtil.getImageUrl(imageUrl)).placeholder(R.drawable.head).into(iTwoImg);
                        } else {
                            iOneImg.setImageResource(R.drawable.head);
//                            iTwoImg.setImageResource(R.drawable.head);
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
                L.d(responseString);
            }
        });
    }

    //调用2-12的接口（首页工程列表每个工程的projectType返回值除headman外全部调用此接口）
    private void loadDevelopers() {
        DataAccessUtil.projectInfoDetail(projectId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    ProjectInfoDetail detail = DataParseUtil.projectInfoDetail(response);
                    if (null != detail) {
                        int developersId = detail.getDevelopers_id();
                        headmans = detail.getHeadmans();
                        headmanCount = headmans.size();
                        if (headmanCount > 0) {
                            qiangDanTv.setText("已有" + headmanCount + "人抢单");
                            qiangDanTv2.setText("已有" + headmanCount + "人抢单");
                        }
                        int id = Integer.valueOf(userId);
                        if (developersId == id) {
                            lBelowShow.setVisibility(View.VISIBLE);

                        } else {
                            lBelowShow.setVisibility(View.GONE);
                        }
                        tTitle.setText(detail.getTitle());
                        tAddress.setText(detail.getAddress());
                        tDescription.setText(detail.getDescription());
                        tEndTime.setText(detail.getEnd_time());
                        tStartTime.setText(detail.getStart_time());
                        tLinkman.setText(detail.getLink_man());
                        tLinkPhone.setText(detail.getLink_phone());
                        if (detail.getImages() != null) {
                            imageUrl = detail.getImages().get(0);
                        }
//                        iOneImg.setImageURI(detail.getImages());
                        if (!TextUtils.isEmpty(imageUrl)) {
                            Picasso.with(ShowDeveloperActivity.this).load(DataAccessUtil.getImageUrl(imageUrl)).placeholder(R.drawable.head).into(iOneImg);
//                            Picasso.with(ShowActivity.this).load(DataAccessUtil.getImageUrl(imageUrl)).placeholder(R.drawable.head).into(iTwoImg);
                        } else {
                            iOneImg.setImageResource(R.drawable.head);
//                            iTwoImg.setImageResource(R.drawable.head);
                        }
                        if (headmans != null) {

                            adapter = new SelectHeadmanAdapter(headmans, category);
                            gridView.setAdapter(adapter);
                        }
                    }
                } catch (ResponseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                L.d(responseString);
            }
        });
    }

    //重写listview的高度
    private void setListView(ListView listview2) {
        // TODO Auto-generated method stub
        ListAdapter listadapter = listview2.getAdapter();
        if (listadapter == null) {
            return;
        }
        int aHeight = 0;
        for (int i = 0; i < listadapter.getCount(); i++) {
            View listItem = listadapter.getView(i, null, listview2);
            listItem.measure(0, 0);
            aHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listview2.getLayoutParams();
        params.height = aHeight + listview2.getDividerHeight() * (listadapter.getCount() - 1);
        listview2.setLayoutParams(params);
    }

    public void onEvent(HeadmanEven even) {
        if (even != null && even.ischeck()) {
            headman = even.getHeadman();
        } else {
            headman = null;
        }


    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }


}
