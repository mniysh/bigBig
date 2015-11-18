package com.ms.ebangw.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.adapter.ProjectItemdetailAdapter;
import com.ms.ebangw.bean.ProjectInfoDetail;
import com.ms.ebangw.bean.ReleaseProject;
import com.ms.ebangw.bean.Staff;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * 企业的展示页面
 * 现在布局还有最底面的列表，打算用listview来实现
 *
 * @author admin xupeng
 */
public class ShowActivity extends BaseActivity implements OnClickListener {

    Button bQiangdan;
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
    @Bind(R.id.iv_two)
    ImageView iTwoImg;
    //    @Bind(R.id.ll_below_show)
//    LinearLayout lBelowShow;
    String imageUrl;

    private ReleaseProject releaseProject;
    String projectId;
    private User user;
    private String category;
    private Staff staff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_show);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        releaseProject = intent.getExtras().getParcelable(Constants.KEY_RELEASED_PROJECT_STR);
        if (releaseProject != null) {
            projectId = releaseProject.getId();
        }
        initView();
        initViewOper();
        initData();
    }

    private void initViewOper() {
        bQiangdan.setOnClickListener(this);

        initTitle(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowActivity.this.finish();
            }
        }, "返回", "企业信息", null, null);

    }


    public void initView() {
        bQiangdan = (Button) findViewById(R.id.act_show_qiangdan);
        user = getUser();
        if(user != null){
            category = user.getCategory();
        }
    }

    @Override
    public void initData() {
        load();
    }

    private void load() {
        DataAccessUtil.projectInfoDetail(projectId, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                     ProjectInfoDetail detail = DataParseUtil.projectInfoDetail(response);
                    if (null != detail) {
                        String IsContend = detail.getIsContend();
                        SharedPreferences sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("IsContend", IsContend);
                        editor.commit();

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
                            Picasso.with(ShowActivity.this).load(DataAccessUtil.getImageUrl(imageUrl)).placeholder(R.drawable.head).into(iOneImg);
//                            Picasso.with(ShowActivity.this).load(DataAccessUtil.getImageUrl(imageUrl)).placeholder(R.drawable.head).into(iTwoImg);
                        } else {
                            iOneImg.setImageResource(R.drawable.head);
//                            iTwoImg.setImageResource(R.drawable.head);
                        }
                        List<Staff> staffs = detail.getStaffs();
                        if (null != staffs && staffs.size() > 0) {
                            detailAdapter = new ProjectItemdetailAdapter(staffs);
                            showListView.setAdapter(detailAdapter);
                            setListView(showListView);

                            detailAdapter.setOnGrabClickListener(new ProjectItemdetailAdapter.OnGrabClickListener() {
                                @Override
                                public void onGrabClick(View view, Staff staff) {
                                    Intent intent = new Intent(ShowActivity.this, RecommendedWorksActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable(Constants.KEY_RELEASED_PROJECT_STR, releaseProject);
                                    bundle.putParcelable(Constants.KEY_RELEASED_PROJECT_STAFF,staff);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            });

                        }
                    }
                } catch (ResponseException e) {
                    e.printStackTrace();
                    T.show(e.getMessage());

                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dismissLoadingDialog();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //点击立刻抢单跳转
            case R.id.act_show_qiangdan:
                L.d("xxx", "跳转能不能进来");
                Intent intent = new Intent(ShowActivity.this, QiangDanActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.KEY_RELEASED_PROJECT_STR, releaseProject);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
