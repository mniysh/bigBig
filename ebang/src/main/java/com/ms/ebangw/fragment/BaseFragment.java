package com.ms.ebangw.fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.dialog.LoadingDialog;
import com.ms.ebangw.utils.L;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    protected Activity mActivity;
    private LoadingDialog mLoadingDialog;

    @Override
      public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    /**
     * 初始化View
     */
    abstract public void initView();

    /**
     * 初始化数据
     */
    abstract public void initData();

    /**
     * @param leftClickLister    返回箭的点击监听
     * @param left               左边标题
     * @param title              中间标题
     * @param right              右边标题
     * @param rightClickListener 右边标题的点击监听
     */
    public void initTitle(View.OnClickListener leftClickLister, String left, String title, String
        right, View.OnClickListener rightClickListener) {
        Window window = mActivity.getWindow();

        View backView = window.findViewById(R.id.iv_back);
        TextView leftTv = (TextView)  window.findViewById(R.id.tv_left);
        TextView titleTv = (TextView)  window.findViewById(R.id.tv_center);
        TextView rightTv = (TextView)  window.findViewById(R.id.tv_right);
        //设置返回箭头
        if (null != leftClickLister && backView != null) {
            backView.setOnClickListener(leftClickLister);
            backView.setVisibility(View.VISIBLE);
        } else {
            backView.setVisibility(View.GONE);
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
     * @param message
     */
    public void showProgressDialog(String message) {
        if (null != mLoadingDialog) {
            mLoadingDialog.dismiss();
        }
        if (null == mLoadingDialog) {
            mLoadingDialog = LoadingDialog.newInstance(message) ;
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
        if (null != mLoadingDialog && null != mLoadingDialog.getActivity()&& mLoadingDialog.isVisible()) {
            mLoadingDialog.dismiss();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName()); //统计页面
    }

    @Override

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }


    /**
     * 友盟集成测试 生成设备码
     * @param context
     * @return
     */
    public static String getDeviceInfo(Context context) {
        try{
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();

            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if( TextUtils.isEmpty(device_id) ){
                device_id = mac;
            }

            if( TextUtils.isEmpty(device_id) ){
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);
            L.d(json.toString());
            return json.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
/**
 * 实例化控件
 */
    public View findviewbyid(int rInt){


        return getView().findViewById(rInt);
    }



}
