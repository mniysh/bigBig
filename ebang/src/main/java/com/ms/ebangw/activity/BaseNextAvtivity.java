package com.ms.ebangw.activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * Created by yangshaohua .
 * Created by on 2015/11/19
 */
public abstract class BaseNextAvtivity extends BaseActivity{


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initView();
        initData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    /**
     *
     * @param context
     * 弹窗的参数
     * @param layout
     * @param pwWidth
     * @param pwHeight
     * 弹窗的控件
     * @param layoutView
     * @param layoutView2
     * @param layoutView3
     * @param layoutView4
     * 触发的控件
     * @param clickView
     * 所在位置参数
     * @param location
     * @param localWidth
     * @param localHeight
     */
    public void pWindow( Context context,LinearLayout layout,int pwWidth,int
            pwHeight,View layoutView,View layoutView2,View layoutView3,View layoutView4,
                         View clickView,int location,int localWidth,int localHeight){
        final PopupWindow pw=new PopupWindow(layout,pwWidth,pwHeight);
        pw.setBackgroundDrawable(new BitmapDrawable());
        layoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw.dismiss();
                finish();
                backgroundAlpha(1.0f);

            }
        });
        backgroundAlpha(0.5f);
        pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                finish();
                backgroundAlpha(1.0f);
            }
        });
        pw.setOutsideTouchable(true);
        pw.showAtLocation(clickView, location, localWidth, localHeight);
    }
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

}
