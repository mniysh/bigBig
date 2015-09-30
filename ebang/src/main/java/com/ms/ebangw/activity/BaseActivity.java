package com.ms.ebangw.activity;

import android.app.DialogFragment;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.dialog.LoadingDialog;
import com.ms.ebangw.utils.L;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private LoadingDialog mLoadingDialog;

    /**
     * 初始化View
     */
    abstract public void initView();

    /**
     * 初始化数据
     */
    abstract public void initData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN |
            WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        makeKeyboard();
        MyApplication.unDestroyActivityList.add(this);

    }

    /**
     * @param leftClickLister    返回箭的点击监听
     * @param left               左边标题
     * @param title              中间标题
     * @param right              右边标题
     * @param rightClickListener 右边标题的点击监听
     */
    public void initTitle(View.OnClickListener leftClickLister, String left, String title, String
        right, View.OnClickListener rightClickListener) {
        View backView = findViewById(R.id.iv_back);
        TextView leftTv = (TextView) findViewById(R.id.tv_left);
        TextView titleTv = (TextView) findViewById(R.id.tv_center);
        TextView rightTv = (TextView) findViewById(R.id.tv_right);
        //设置返回箭头
        if (null != leftClickLister && backView != null) {
            backView.setOnClickListener(leftClickLister);
        } else {
            backView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        //设置左标题
        if (leftTv != null && !TextUtils.isEmpty(left)) {
            leftTv.setText(left);
            leftTv.setVisibility(View.VISIBLE);
        }

        //设置中间的标题
        if (titleTv != null && !TextUtils.isEmpty(title)) {
            titleTv.setText(title);
            titleTv.setVisibility(View.VISIBLE);
        }

        //设置右边文字
        if (rightTv != null && !TextUtils.isEmpty(right)) {
            rightTv.setText(right);
            if (null != rightClickListener) {
                rightTv.setOnClickListener(rightClickListener);
            }
            rightTv.setVisibility(View.VISIBLE);
        }
    }

    public void initTitle(String title) {
        initTitle(null, null, title, null, null);
    }

    public void initTitle(String title, String right, View.OnClickListener rightClickListener) {
        initTitle(null, null, title, right, rightClickListener);
    }

    /**
     * 显示进度对话框
     *
     * @param message
     */
    public void showProgressDialog(String message) {
        if (null != mLoadingDialog) {
            mLoadingDialog.dismiss();
        }
        if (null == mLoadingDialog) {
            mLoadingDialog = LoadingDialog.newInstance(message);
        }
        mLoadingDialog.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        mLoadingDialog.show(getFragmentManager(), TAG);
    }

    public void showProgressDialog() {
        showProgressDialog(null);
    }

    /**
     * 关闭进度对话框
     */
    public void dismissLoadingDialog() {
        if (null != mLoadingDialog && null != mLoadingDialog.getActivity() && mLoadingDialog.isVisible()) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * 弹出popupwindow
     */
    public void pWindow(LinearLayout layout,int pwWidth,int pwHeight,View layoutView,View layoutView2,View layoutView3,View layoutView4,View clickView,int location,int localWidth,int localHeight){
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mLoadingDialog) {
            mLoadingDialog.dismiss();
        }
        MyApplication.unDestroyActivityList.remove(this);

//            makeKeyboard();
//
    }

    public User getUser() {
        User user = MyApplication.getInstance().getUser();
        return user;
    }

    /**
     * 判断是否登陆
     * @return
     */
    public boolean isLogin() {

        User user = getUser();

        if (null != user) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        makeKeyboard();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
//        makeKeyboard();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != mLoadingDialog) {
            mLoadingDialog.dismiss();
        }
        MobclickAgent.onPause(this);
    }

    /**
     * 控制软键盘
     */
    public void makeKeyboard(){

        final View v = getWindow().peekDecorView();
        if (v != null && v.getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }


    }



}
