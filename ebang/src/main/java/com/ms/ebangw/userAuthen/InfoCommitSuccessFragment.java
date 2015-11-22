package com.ms.ebangw.userAuthen;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.HomeActivity;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.fragment.BaseFragment;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.setting.SettingAllActivity;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
public class InfoCommitSuccessFragment extends BaseFragment {
    private static final String CATEGORY = "category";
    private ViewGroup contentLayout;
    private String category;
    @Bind(R.id.btn_goHome)
    Button goHomeBtn;
    @Bind(R.id.layout_commit_successful)
    LinearLayout commitedInfoLayout;
    @Bind(R.id.layout_auth_failed)
    LinearLayout authFailedLayout;



    public static InfoCommitSuccessFragment newInstance(String category) {
        InfoCommitSuccessFragment fragment = new InfoCommitSuccessFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    public InfoCommitSuccessFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(CATEGORY);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_info_commit_success,
            container, false);
        ButterKnife.bind(this, contentLayout);
        initView();
        initData();

        return contentLayout;
    }


    @Override
    public void initView() {

        User user = getUser();
        String status = user.getStatus();        //认证状态

        switch (status) {

            case "auth_investor":        //认证中
            case "auth_worker":
            case "auth_headman":
            case "auth_developers":
                commitedInfoLayout.setVisibility(View.VISIBLE);
                authFailedLayout.setVisibility(View.GONE);
                break;
            case "auth_investor_fail":
            case "auth_worker_fail":
            case "auth_headman_fail":
            case "auth_developers_fail":
                commitedInfoLayout.setVisibility(View.GONE);
                authFailedLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void initData() {
        goHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mActivity instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) mActivity;
                    homeActivity.lotteryRb.performClick();
                } else {
                    MyApplication.getInstance().setFlag_home(true);
                    mActivity.finish();
                }
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        View layout = mActivity.findViewById(R.id.layout_step);
        View titleLayout = mActivity.findViewById(R.id.layout_title);

        if (null != layout) {
            layout.setVisibility(View.GONE);
        }

        if (titleLayout != null) {
            titleLayout.setVisibility(View.GONE);
        }

        String title = getTitleByCategory(category);
//        initTitle(null, "返回", title, null, null);
        if (mActivity instanceof HomeActivity) {

            initTitle(null, null, "我的信息", "设置", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //设置跳转
                    Intent intent=new Intent(mActivity, SettingAllActivity.class);

                    mActivity.startActivityForResult(intent, Constants.REQUEST_EXIT);
                }
            });
        }else {
            initTitle(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.finish();
                }
            }, "返回", getTitleByCategory(category), null, null);
        }

    }

    /**
     * 认证失败后重试
     */
    @OnClick(R.id.btn_auth_again)
    public void authAgain() {
        DataAccessUtil.resetAuth(new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                showProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                dismissLoadingDialog();
                try {
                    boolean b = DataParseUtil.processDataResult(response);
                    if (b) {
                        User user = getUser();
                        user.setStatus(Constants.AUTH_GUEST);
                        if (MyApplication.getInstance().saveUser(user) && null != mActivity) {
                            ((HomeActivity)mActivity).setAuthStatus();
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
                dismissLoadingDialog();
            }
        });


    }

    public String getTitleByCategory(String category) {
        if (TextUtils.isEmpty(category)) {
            return "";
        }

        String title;
        switch (category) {
            case Constants.INVESTOR:
                title = "个人认证";
                break;

            case Constants.WORKER:

                title = "务工人认证";
                break;

            case Constants.HEADMAN:

                title = "工长认证";

                break;

            case Constants.DEVELOPERS:
                title = "开发商认证";
                break;
            default:
                title = "";
                break;
        }

        return title;
    }


}
