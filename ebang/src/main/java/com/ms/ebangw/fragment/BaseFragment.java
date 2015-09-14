package com.ms.ebangw.fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;

import com.ms.ebangw.dialog.LoadingDialog;

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



}
