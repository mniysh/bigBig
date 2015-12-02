package com.ms.ebangw.activity;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.ReleaseProject;
import com.ms.ebangw.bean.Staff;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.dialog.QiangDanDialog;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;

import org.apache.http.Header;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 抢单页面
 *
 * @author admin xupeng
 */
public class QiangDanActivity extends BaseNextAvtivity {

    private Button bSmillBack;
    private Button bSadBack;
    private LinearLayout lSmilllayout;
    private LinearLayout lSadlayout;
    //    private Button iQiangDan;
    private ReleaseProject releaseProject;
    private boolean flag_protocol;
    String projectId;
    @Bind(R.id.tv_time)
    TextView tTime;
    @Bind(R.id.tv_phone_num)
    TextView tPhoneNum;
    @Bind(R.id.activity_qiang_dan_but_qianddan)
    Button bQiangDan;
    private LinearLayout layout;
    @Bind(R.id.cb_procotol)
    CheckBox procotolCb;
    private String tilte;
    @Bind(R.id.tv_title)
    TextView titleTv;
    private String categroy;
    private Staff staff;
    private String craftId;


    @OnClick(R.id.activity_qiang_dan_but_qianddan)
    public void qiandDan(){
        if(flag_protocol){
            if(TextUtils.equals(categroy, Constants.HEADMAN) || TextUtils.equals(categroy, Constants.COMPANY)){
                loadHeadman();
            }else if (TextUtils.equals(categroy,Constants.WORKER)){
                loadWorker();
            }else{
                T.show("请完善您的注册信息");
            }

        }else{
            T.show("请同意亿帮无忧抢单协议，否则不能抢单");
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiang_dan);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        releaseProject = intent.getExtras().getParcelable(Constants.KEY_RELEASED_PROJECT_STR);
        categroy = intent.getExtras().getString(Constants.KEY_CATEGORY);
        if(TextUtils.equals(categroy,Constants.WORKER)){

            staff = intent.getExtras().getParcelable(Constants.KEY_RELEASED_PROJECT_STAFF);
            craftId = staff.getCraft_id();
        }

        projectId = releaseProject.getId();
        tilte = releaseProject.getTitle();



        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        tPhoneNum.setText(getUser().getPhone());
        tTime.setText(str);
        titleTv.setText(tilte);


        initView();
        initData();
//        load();
    }


    @Override
    public void initView() {
        initTitle(new OnClickListener() {
            @Override
            public void onClick(View v) {
                QiangDanActivity.this.finish();
            }
        }, "返回", "抢单", null, null);
        lSmilllayout = (LinearLayout) this.getLayoutInflater().inflate(R.layout.pop_qiangdan_succeed, null, false);
        lSadlayout = (LinearLayout) this.getLayoutInflater().inflate(R.layout.pop_qiangdan_failed, null, false);
        bQiangDan = (Button) findViewById(R.id.activity_qiang_dan_but_qianddan);
        bSmillBack = (Button) lSmilllayout.findViewById(R.id.popup_lay_iv_back);
        bSadBack = (Button) lSadlayout.findViewById(R.id.popup_lay_iv_back);
    }

    @Override
    public void initData() {
//        load();
        procotolCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    flag_protocol = true;
                }else{
                    flag_protocol = false;
                }
            }
        });
    }
    private void loadWorker(){
        DataAccessUtil.workerContendProject(projectId, craftId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    boolean b = DataParseUtil.processDataResult(response);
                    if (b) {
//                        showWindowSucceed(lSmilllayout, categroy);
                        showDialogSucceed("succeed");

                    }
                } catch (ResponseException e) {
                    e.printStackTrace();
//                    showWindowFailed(lSadlayout, e.getMessage());
                        showDialogSucceed("failed");
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    private void loadHeadman() {

        DataAccessUtil.headmanContendProject(projectId, new JsonHttpResponseHandler() {
//            @Override
//            public void onStart() {
//                super.onStart();
//                showProgressDialog();
//            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
//                dismissLoadingDialog();
                try {
                    boolean b = DataParseUtil.processDataResult(response);
                    if (b) {
//                        showWindowSucceed(lSmilllayout, categroy);
                        showDialogSucceed("succeed");

                    }
                } catch (ResponseException e) {
                    e.printStackTrace();
                    //T.show(e.getMessage());
//                    showWindowFailed(lSadlayout, e.getMessage());
                    showDialogSucceed("failed");

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                L.d(responseString);
                //dismissLoadingDialog();
            }
        });


    }
    private void showDialogSucceed(String flag){
        QiangDanDialog dialog = QiangDanDialog.newInstance(flag, "");
        dialog.show(getFragmentManager(),"qiangdan");



    }

    private void showWindowSucceed(View view, final String categroy){


            final PopupWindow pw = new PopupWindow(view, 600, LayoutParams.WRAP_CONTENT);
            pw.setBackgroundDrawable(new BitmapDrawable());
            pw.showAtLocation(bQiangDan, Gravity.CENTER_VERTICAL, 0, 0);
            bSmillBack.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    backgroundAlpha(1.0f);
                    pw.dismiss();
                    QiangDanActivity.this.finish();
                    Intent intentSucceed = new Intent();

                    intentSucceed.setAction(Constants.KEY_QIANGDAN_SUCCEED);
                    intentSucceed.putExtra("key", categroy);
                    sendBroadcast(intentSucceed);
//                                        startActivity(new Intent(QiangDanActivity.this, ShowActivity.class)); //ActivityA.this.finish();
                }
            });
            backgroundAlpha(0.5f);

    }
    private void showWindowFailed(View view , String message){
                     final PopupWindow pw = new PopupWindow(view, 600, LayoutParams.WRAP_CONTENT);
                    TextView messageTv = (TextView) lSadlayout.findViewById(R.id.tv_message);
                    messageTv.setText(message);
                    pw.setBackgroundDrawable(new BitmapDrawable());
                    pw.showAtLocation(bQiangDan, Gravity.CENTER_VERTICAL, 0, 0);
                    bSadBack.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            backgroundAlpha(1.0f);
                            pw.dismiss();
                            QiangDanActivity.this.finish();
//                                        startActivity(new Intent(QiangDanActivity.this, ShowActivity.class)); //ActivityA.this.finish();


                        }
                    });
                    backgroundAlpha(0.5f);
    }


}
