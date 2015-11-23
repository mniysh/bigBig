package com.ms.ebangw.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import u.upd.l;

/**
 * 企业的展示页面
 * 现在布局还有最底面的列表，打算用listview来实现
 *
 * @author admin xupeng
 */
public class ShowActivity extends BaseActivity implements OnClickListener {
    @Bind(R.id.act_show_qiangdan)
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
    @Bind(R.id.ll_below_show)
    LinearLayout lBelowShow;
    String imageUrl;

    private ReleaseProject releaseProject;
    String projectId;
    private User user;
    private String category;
    private Staff staff;
    private String projectType;
    private String userId;

    private BroadcastReceiver br=new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent in) {
            // TODO Auto-generated method stub
            String categroy = in.getStringExtra("key");
            if(TextUtils.equals(categroy, Constants.HEADMAN)){
                showListView.setVisibility(View.VISIBLE);
                lBelowShow.setVisibility(View.GONE);
                bQiangdan.setText("已抢单");
                loadHeadwork();
            }
            if(TextUtils.equals(categroy, Constants.WORKER)){
                detailAdapter.notifyDataSetChanged();
                loadInvistor();
            }

        }
    };
    private IntentFilter filter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_show);
        ButterKnife.bind(this);
        filter=new IntentFilter();
        filter.addAction(Constants.KEY_QIANGDAN_SUCCEED);
        registerReceiver(br, filter);
        user = getUser();
        category = user.getCategory();
        userId = user.getId();

        Intent intent = getIntent();
        if(intent != null){

            releaseProject = intent.getExtras().getParcelable(Constants.KEY_RELEASED_PROJECT_STR);
        }
        if (releaseProject != null) {
            projectId = releaseProject.getId();
            projectType = releaseProject.getProject_type();
        }
        //判断是工长还是开发商
        if(TextUtils.equals(category, Constants.HEADMAN ) || TextUtils.equals(category,Constants.DEVELOPERS)){
            if(TextUtils.equals(category,Constants.DEVELOPERS)){
                showListView.setVisibility(View.GONE);
//                lBelowShow.setVisibility(View.VISIBLE);
//                bQiangdan.setVisibility(View.VISIBLE);
                bQiangdan.setText("选择工长");
                loadDevelopers();
            }else{
                lBelowShow.setVisibility(View.VISIBLE);
                bQiangdan.setText("立刻抢单");
                loadHeadwork();
            }
        }
        if(TextUtils.equals(category, Constants.WORKER) || TextUtils.equals(category, Constants.INVESTOR) ){
            if(TextUtils.equals(category, Constants.WORKER)){
//                SharedPreferences sharedPreferences = getSharedPreferences("isContend", Context.MODE_PRIVATE);
//                String isContend = sharedPreferences.getString("isContend","process");
            }
            if(TextUtils.equals(category, Constants.INVESTOR)){
                SharedPreferences sharedPreferences = getSharedPreferences("isHave", Context.MODE_PRIVATE);
                String isHave = sharedPreferences.getString("isHave","process");
            }

            showListView.setVisibility(View.VISIBLE);
            lBelowShow.setVisibility(View.GONE);
            loadInvistor();
        }

        initView();
        initViewOper();
        initData();
    }

    private void initViewOper() {
        initTitle(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowActivity.this.finish();
            }
        }, "返回", "企业信息", null, null);
        bQiangdan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowActivity.this, QiangDanActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.KEY_RELEASED_PROJECT_STR, releaseProject);
//                bundle.putParcelable(Constants.KEY_RELEASED_PROJECT_STAFF, staff);

                bundle.putString(Constants.KEY_CATEGORY, category);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }


    public void initView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(br);
        ButterKnife.unbind(this);
    }

    @Override
    public void initData() {
        if(TextUtils.equals(category, Constants.HEADMAN)){


        }else if(TextUtils.equals(category, Constants.WORKER)){

        }
    }
    private void  loadDevelopers(){
        DataAccessUtil.projectInfoDetail(projectId, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    ProjectInfoDetail detail = DataParseUtil.projectInfoDetail(response);
                    if (null != detail) {
                        int developersId = detail.getDevelopers_id();
                        int id= Integer.valueOf(userId);
                        if(developersId == id){
                            lBelowShow.setVisibility(View.VISIBLE);

                        }else{
                            lBelowShow.setVisibility(View.GONE);
                        }



//                        if( TextUtils.equals(IsContend,"contend") || TextUtils.equals(IsContend, "process")){
//                            showListView.setVisibility(View.VISIBLE);
//                            lBelowShow.setVisibility(View.GONE);
//
//                        }else{
//                            showListView.setVisibility(View.GONE);
//                            lBelowShow.setVisibility(View.VISIBLE);
//                            bQiangdan.setText("立刻抢单");
//                        }
//
//                        if(TextUtils.equals(category,Constants.DEVELOPERS)){
//                            showListView.setVisibility(View.VISIBLE);
//                            lBelowShow.setVisibility(View.VISIBLE);
//                            bQiangdan.setText("选择工长");
//                        }

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
//                        List<Staff> staffs = detail.getStaffs();
//                        if (null != staffs && staffs.size() > 0) {
//                            detailAdapter = new ProjectItemdetailAdapter(staffs, category, new ProjectItemdetailAdapter.OnGrabClickListener() {
//                                @Override
//                                public void onGrabClick(View view, Staff staff) {
//                                    Intent intent = new Intent(ShowActivity.this, SelectWorkerActivity.class);
//                                    Bundle bundle = new Bundle();
//                                    bundle.putParcelable(Constants.KEY_RELEASED_PROJECT_STR, releaseProject);
//                                    bundle.putParcelable(Constants.KEY_RELEASED_PROJECT_STAFF,staff);
//                                    bundle.putString(Constants.KEY_CATEGORY,category);
//                                    intent.putExtras(bundle);
//                                    startActivity(intent);
//                                }
//                            });
//                            showListView.setAdapter(detailAdapter);
//                            setListView(showListView);

//                            detailAdapter.setOnGrabClickListener(new ProjectItemdetailAdapter.OnGrabClickListener() {
//                                @Override
//                                public void onGrabClick(View view, Staff staff) {
//                                    Intent intent = new Intent(ShowActivity.this, SelectWorkerActivity.class);
//                                    Bundle bundle = new Bundle();
//                                    bundle.putParcelable(Constants.KEY_RELEASED_PROJECT_STR, releaseProject);
//                                    bundle.putParcelable(Constants.KEY_RELEASED_PROJECT_STAFF,staff);
//                                    bundle.putString(Constants.KEY_CATEGORY,category);
//                                    intent.putExtras(bundle);
//                                    startActivity(intent);
//                                }
//                            });

                        }
                    } catch (ResponseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFinish() {
                super.onFinish();
                dismissLoadingDialog();
            }
        });
    }
    //个人，，，还没写完
    private void loadInvistor(){
        showListView.setVisibility(View.VISIBLE);
        lBelowShow.setVisibility(View.GONE);
        DataAccessUtil.projectInfoDetailInvistor(projectId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    ProjectInfoDetail projectInfoDetail = DataParseUtil.projectInfoDetail(response);

                    if (projectInfoDetail != null) {
                        int developersId = projectInfoDetail.getDevelopers_id();



//                        if(TextUtils.equals(category, Constants.INVESTOR)){
//                            String isHave = projectInfoDetail.getIsHave();
//                            SharedPreferences sp = getSharedPreferences("isHave", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sp.edit();
//                            editor.putString("isHave", isHave);
//                            editor.commit();
////                            if(TextUtils.equals(isHave,)){
////
////                            }
//                        }
//                        if(TextUtils.equals(category, Constants.WORKER)){
//                            String IsContend = projectInfoDetail.getIsContend();
//                            SharedPreferences sharedPreferences = getSharedPreferences("isContend", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.putString("isContend", IsContend);
//                            editor.commit();
//                        }


                        tTitle.setText(projectInfoDetail.getTitle());
                        tAddress.setText(projectInfoDetail.getAddress());
                        tDescription.setText(projectInfoDetail.getDescription());
                        tEndTime.setText(projectInfoDetail.getEnd_time());
                        tStartTime.setText(projectInfoDetail.getStart_time());
                        tLinkman.setText(projectInfoDetail.getLink_man());
                        tLinkPhone.setText(projectInfoDetail.getLink_phone());
                        if (projectInfoDetail.getImages() != null) {
                            imageUrl = projectInfoDetail.getImages().get(0);
                        }
//                        iOneImg.setImageURI(detail.getImages());
                        if (!TextUtils.isEmpty(imageUrl)) {
                            Picasso.with(ShowActivity.this).load(DataAccessUtil.getImageUrl(imageUrl)).placeholder(R.drawable.head).into(iOneImg);
//                            Picasso.with(ShowActivity.this).load(DataAccessUtil.getImageUrl(imageUrl)).placeholder(R.drawable.head).into(iTwoImg);
                        } else {
                            iOneImg.setImageResource(R.drawable.head);
//                            iTwoImg.setImageResource(R.drawable.head);
                        }
                        List<Staff> staffs = projectInfoDetail.getStaffs();
                        if (null != staffs && staffs.size() > 0) {
                            detailAdapter = new ProjectItemdetailAdapter(staffs, user,developersId,new ProjectItemdetailAdapter.OnGrabClickListener() {
                                @Override
                                public void onGrabClick(View view, Staff staff) {
//                                    T.show("自定义2");
                                    Intent intent = new Intent(ShowActivity.this, QiangDanActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable(Constants.KEY_RELEASED_PROJECT_STR, releaseProject);
                                    bundle.putParcelable(Constants.KEY_RELEASED_PROJECT_STAFF, staff);
                                    bundle.putString(Constants.KEY_CATEGORY, category);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            });
                            showListView.setAdapter(detailAdapter);
                            setListView(showListView);


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
    //工长

    private void loadHeadwork() {

        DataAccessUtil.projectInfoDetail(projectId, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                     ProjectInfoDetail detail = DataParseUtil.projectInfoDetail(response);
                    if (null != detail) {
                        int id = Integer.valueOf(userId);
                        int developersId = detail.getDevelopers_id();
                        if(developersId == id){
                            lBelowShow.setVisibility(View.GONE);
                            showListView.setVisibility(View.VISIBLE);
                        }else{
                            String IsContend = detail.getIsContend();
                            if( TextUtils.equals(IsContend,"contend") || TextUtils.equals(IsContend, "process")){
                                showListView.setVisibility(View.VISIBLE);
                                lBelowShow.setVisibility(View.GONE);
                            }else{
                                showListView.setVisibility(View.GONE);
                                lBelowShow.setVisibility(View.VISIBLE);
                                bQiangdan.setText("立刻抢单");
                            }
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
                            Picasso.with(ShowActivity.this).load(DataAccessUtil.getImageUrl(imageUrl)).placeholder(R.drawable.head).into(iOneImg);
//                            Picasso.with(ShowActivity.this).load(DataAccessUtil.getImageUrl(imageUrl)).placeholder(R.drawable.head).into(iTwoImg);
                        } else {
                            iOneImg.setImageResource(R.drawable.head);
//                            iTwoImg.setImageResource(R.drawable.head);
                        }
                        List<Staff> staffs = detail.getStaffs();
                        if (null != staffs && staffs.size() > 0) {
                            detailAdapter = new ProjectItemdetailAdapter(staffs, user,developersId,new ProjectItemdetailAdapter.OnGrabClickListener() {
                                @Override
                                public void onGrabClick(View view, Staff staff) {
                                    Intent intent = new Intent(ShowActivity.this, SelectWorkerActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable(Constants.KEY_RELEASED_PROJECT_STR, releaseProject);
                                    bundle.putParcelable(Constants.KEY_RELEASED_PROJECT_STAFF,staff);
                                    bundle.putString(Constants.KEY_CATEGORY,category);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            });
                            showListView.setAdapter(detailAdapter);
                            setListView(showListView);

//                            detailAdapter.setOnGrabClickListener(new ProjectItemdetailAdapter.OnGrabClickListener() {
//                                @Override
//                                public void onGrabClick(View view, Staff staff) {
//                                    Intent intent = new Intent(ShowActivity.this, SelectWorkerActivity.class);
//                                    Bundle bundle = new Bundle();
//                                    bundle.putParcelable(Constants.KEY_RELEASED_PROJECT_STR, releaseProject);
//                                    bundle.putParcelable(Constants.KEY_RELEASED_PROJECT_STAFF,staff);
//                                    bundle.putString(Constants.KEY_CATEGORY,category);
//                                    intent.putExtras(bundle);
//                                    startActivity(intent);
//                                }
//                            });

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

                break;
            default:
                break;
        }
    }
}
