package com.ms.ebangw.activity;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;


import com.ms.ebangw.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 抢单页面
 */
public class QiangDanActivity extends BaseActivity {

    private Button bBack;
    private LinearLayout layout;
    private Button iQiangDan;
    @OnClick(R.id.activity_qiang_dan_but_qianddan)
    public void qiangdan(View v){
        //抢单后弹窗，目前只谈的是成功的窗口
        final PopupWindow pw=new PopupWindow(layout, 600,LayoutParams.WRAP_CONTENT);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.showAtLocation(iQiangDan, Gravity.CENTER_VERTICAL,0, 0);
        bBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundAlpha(1.0f);
                pw.dismiss();

//                Intent intent=new Intent();
//                intent.setClass(QiangDanActivity.this, ShowActivity.class);
//                startActivity(intent); //ActivityA.this.finish();
                QiangDanActivity.this.finish();
            }
        });

        backgroundAlpha(0.5f);


    }


    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiang_dan);
        ButterKnife.bind(this);
        initView();
        initData();

    }


    @Override
    public void initView() {
        initTitle(new OnClickListener() {
            @Override
            public void onClick(View v) {
                QiangDanActivity.this.finish();
            }
        },"返回","抢单",null,null);
        layout= (LinearLayout) this.getLayoutInflater().inflate(R.layout.pop_qiangdan_succeed,null,false);
        iQiangDan= (Button)findViewById(R.id.activity_qiang_dan_but_qianddan);
        bBack= (Button) layout.findViewById(R.id.popup_lay_iv_back);
    }

    @Override
    public void initData() {

    }


}
