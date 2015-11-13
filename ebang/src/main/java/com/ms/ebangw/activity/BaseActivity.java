package com.ms.ebangw.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.Bank;
import com.ms.ebangw.bean.TotalRegion;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.db.UserDao;
import com.ms.ebangw.dialog.LoadingDialog;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN |
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
//        //设置返回箭头
//        if (null != leftClickLister && backView != null) {
//            backView.setOnClickListener(leftClickLister);
//        } else {
//            backView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onBackPressed();
//                }
//            });
//        }
        //设置左标题
        if (leftTv != null && !TextUtils.isEmpty(left)) {
            leftTv.setText(left);
            leftTv.setVisibility(View.VISIBLE);

            //设置返回箭头
            backView.setVisibility(View.VISIBLE);
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

        }else {
            backView.setVisibility(View.INVISIBLE);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mLoadingDialog) {
            mLoadingDialog.dismiss();
        }
        MyApplication.unDestroyActivityList.remove(this);
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

    /**
     * 获取所有省市信息
     * @return
     */
    public TotalRegion getAreaFromAssets() {
        try {
            StringBuilder builder = new StringBuilder();
            InputStream is = getAssets().open("area.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(is);
            char[] chars = new char[1024];
            int len;
            while ((len = inputStreamReader.read(chars)) != -1) {
                builder.append(chars, 0, len);
            }
            String s = builder.toString();
            JSONObject jsonObject = new JSONObject(s);
            return DataParseUtil.provinceCityArea(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取银行
     * @return
     */
    public List<Bank> getBanks(){
        StringBuilder sb= new StringBuilder();
        try {
            InputStream is = getAssets().open("bank_list.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(is);
            char[] chars = new char[1024];
            int len;
            while ((len = inputStreamReader.read(chars)) != -1){
                sb.append(chars, 0 ,len);
            }
            String s = sb.toString();
            JSONObject jsonObject = new JSONObject(s);
            return DataParseUtil.bankList(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ResponseException e) {
            e.printStackTrace();
        }
        return  null;

    }



    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != mLoadingDialog) {
            mLoadingDialog.dismiss();
        }
        MobclickAgent.onPause(this);
    }
    public  void logout(){
        DataAccessUtil.exit(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    boolean b = DataParseUtil.exit(response);

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

        UserDao userDao = new UserDao(this);
        userDao.removeAll();
        MyApplication.getInstance().quit();
        startActivity(new Intent(this, LoginActivity.class));
//        activity.setResult(Constants.REQUEST_EXIT);
//        activity.finish();
    }


}
