package com.ms.ebangw.activity;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.ReleaseProject;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * 抢单页面
 *
 * @author admin xupeng
 */
public class QiangDanActivity extends BaseActivity {

    private Button bSmillBack;
    private Button bSadBack;
    private LinearLayout lSmilllayout;
    private LinearLayout lSadlayout;
    //    private Button iQiangDan;
    private ReleaseProject releaseProject;
    String projectId;
    @Bind(R.id.tv_time)
    TextView tTime;
    @Bind(R.id.tv_phone_num)
    TextView tPhoneNum;
    @Bind(R.id.activity_qiang_dan_but_qianddan)
    Button bQiangDan;

//    @OnClick(R.id.activity_qiang_dan_but_qianddan)
//    public void qiangdan(View v) {
////        抢单后弹窗，目前只谈的是成功的窗口
//        final PopupWindow pw = new PopupWindow(lSmilllayout, 600, LayoutParams.WRAP_CONTENT);
//        pw.setBackgroundDrawable(new BitmapDrawable());
//        pw.showAtLocation(iQiangDan, Gravity.CENTER_VERTICAL, 0, 0);
//        bSmillBack.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                backgroundAlpha(1.0f);
//                pw.dismiss();
//
////                Intent intent=new Intent();
////                intent.setClass(QiangDanActivity.this, ShowActivity.class);
////                startActivity(intent); //ActivityA.this.finish();
//                QiangDanActivity.this.finish();
//            }
//        });
//
//        backgroundAlpha(0.5f);
//    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiang_dan);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        releaseProject = intent.getExtras().getParcelable(Constants.KEY_RELEASED_PROJECT_STR);
        if (releaseProject != null) {
            projectId = releaseProject.getId();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("HH时mm分ss秒");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        tPhoneNum.setText(getUser().getPhone());
        tTime.setText(str);

        initView();
        initData();
        load();
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
        load();
    }

    private void load() {

        DataAccessUtil.headmanContendProject(projectId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (DataParseUtil.processDataResult(response)) {
                        bQiangDan.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final PopupWindow pw = new PopupWindow(lSmilllayout, 600, LayoutParams.WRAP_CONTENT);
                                pw.setBackgroundDrawable(new BitmapDrawable());
                                pw.showAtLocation(bQiangDan, Gravity.CENTER_VERTICAL, 0, 0);
                                bSmillBack.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        backgroundAlpha(1.0f);
                                        pw.dismiss();
//                                          Intent intent = new Intent();
//                                          intent.setClass(QiangDanActivity.this, ShowActivity.class);
                                          startActivity(new Intent(QiangDanActivity.this, ShowActivity.class)); //ActivityA.this.finish();
//                                        QiangDanActivity.this.finish();
                                    }
                                });
                                backgroundAlpha(0.5f);
                            }
                        });
                    } else {
                        bQiangDan.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final PopupWindow pw = new PopupWindow(lSadlayout, 600, LayoutParams.WRAP_CONTENT);
                                pw.setBackgroundDrawable(new BitmapDrawable());
                                pw.showAtLocation(bQiangDan, Gravity.CENTER_VERTICAL, 0, 0);
                                bSadBack.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        backgroundAlpha(1.0f);
                                        pw.dismiss();
//                                        Intent intent = new Intent();
//                                        intent.setClass(QiangDanActivity.this, ShowActivity.class);
                                        startActivity(new Intent(QiangDanActivity.this, ShowActivity.class)); //ActivityA.this.finish();
//                                        QiangDanActivity.this.finish();
                                    }
                                });
                                backgroundAlpha(0.5f);
                            }
                        });

                    }
                } catch (ResponseException e) {
                    e.printStackTrace();
                }

            }
        });


    }
}
