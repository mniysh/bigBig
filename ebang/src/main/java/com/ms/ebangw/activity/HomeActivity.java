package com.ms.ebangw.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.bean.WorkType;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.event.BottomTitleClickEvent;
import com.ms.ebangw.event.OnCheckedWorkTypeEvent;
import com.ms.ebangw.event.RefreshUserEvent;
import com.ms.ebangw.event.WorkTypeEvent;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.fragment.AuthenticationFragment;
import com.ms.ebangw.fragment.DevelopersCenterFragment;
import com.ms.ebangw.fragment.FoundFragment;
import com.ms.ebangw.fragment.HeadmanCenterFragment;
import com.ms.ebangw.fragment.HomeFragment;
import com.ms.ebangw.fragment.InvestorCenterFragment;
import com.ms.ebangw.fragment.ServiceFragment;
import com.ms.ebangw.fragment.WorkerCenterFragment;
import com.ms.ebangw.release.IncreaseDetailFragment;
import com.ms.ebangw.release.ReleaseActivity;
import com.ms.ebangw.release.ReleaseWorkTypeFragment;
import com.ms.ebangw.release.SelectCraftFragment;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.userAuthen.InfoCommitSuccessFragment;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;
import com.umeng.update.UmengUpdateAgent;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cz.msebera.android.httpclient.Header;
import de.greenrobot.event.EventBus;

/**
 * Home主页面
 *
 * @author admin
 */
public class HomeActivity extends BaseActivity {
    @Bind(R.id.tl_tab)
    TabLayout tabLayout;
    private FragmentManager fm;
    private long exitTime = 0;
    private List<WorkType> data;
    private FoundFragment foundFragment;
    private ServiceFragment serviceFragment;
    private ReleaseWorkTypeFragment releaseWorkTypeFragment;
    private List<WorkType> selectWorkType;

    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    @Bind(R.id.rb_mine)
    public RadioButton mineRb;
    @Bind(R.id.rb_home)
    public RadioButton lotteryRb;

    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    L.d("Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
                    break;
                default:
                    L.i("Unhandled msg - " + msg.what);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        fm = getFragmentManager();
        L.d("HomeActivity onCreate, savedInstanceState=  " + savedInstanceState);
        if (savedInstanceState != null) {
            L.d(savedInstanceState.toString());
//            initView();
//            initData();
        } else {
            initView();
            initData();
            initUMengUpdate();
            initJpush();
            radioGroup.getChildAt(0).performClick();
        }
    }

    public void initView() {
//        initTabs();
        selectWorkType = new ArrayList<>();
    }

    public List<WorkType> getSelectWorkType() {
        return selectWorkType;
    }


    @Override
    public void initData() {

        loadUserInformation();
        foundFragment = new FoundFragment();
        serviceFragment = new ServiceFragment();
        User user = getUser();
        final String categroy = user.getCategory();

        releaseWorkTypeFragment = new ReleaseWorkTypeFragment();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        fm.beginTransaction().replace(R.id.fl_content, new HomeFragment()).commit();
//                        fm.beginTransaction().replace(R.id.fl_content, lotteryFragment).commit();
                        break;
                    case R.id.rb_discovery:
                        fm.beginTransaction().replace(R.id.fl_content, foundFragment).commit();
//                        fm.beginTransaction().replace(R.id.fl_content, workerHomeFragment).commit();
                        break;
                    case R.id.rb_release:
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.RELEASE_WORKTYPE_KEY, categroy);
                        Intent intentRelease = new Intent(HomeActivity.this, ReleaseActivity.class);
                        intentRelease.putExtras(bundle);
                        startActivity(intentRelease);
//                        startActivity(new Intent());
//                        fm.beginTransaction().replace(R.id.fl_content, new SelectCraftFragment().newInstance(categroy, "")).commit();
//                        fm.beginTransaction().replace(R.id.fl_content, new SelectCraftFragment()).commit();
//                        fm.beginTransaction().replace(R.id.fl_content, eMallFragment).commit();
                        break;
                    case R.id.rb_service:
                        // L.locationpois_item("xxx","被点击");
                        fm.beginTransaction().replace(R.id.fl_content, serviceFragment).commit();
//                        fm.beginTransaction().replace(R.id.fl_content, eMallFragment).commit();
                        break;

                    case R.id.rb_mine:

                        L.d("xxx", "返回值是" + isLogin());
                        if (isLogin()) {
                            User user = getUser();
                            String category = user.getCategory();
                            setAuthStatus();

                        } else {
                            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        break;
                }
            }
        });

    }


    public void onEvent(WorkTypeEvent event) {
        WorkType workType = event.getWorkType();
        boolean isAdd = event.isAdd();
        if (workType != null && isAdd) {
            selectWorkType.add(workType);
        } else {
            selectWorkType.remove(workType);
        }
    }

    /**
     * 根据人员类型跳转到相应的内容
     * category	//用户已认证类型  worker(工人)/headman(工头)/developers(开发商)/investor(个人)  null（未认证）
     * <p>
     * /认证中
     * status: 			   状态游客guest
     * auth_developers(认证开发者中)/
     * auth_worker(认证工人中)/
     * auth_headman(认证工头中)/
     * auth_investor(认证个人中)/
     * <p>
     * complete（完成认证)
     * <p>
     * auth_developers_fail（认证开发商失败）
     * auth_worker_fail（认证务工失败）
     * auth_headman_fail（认证工头失败）
     * auth_investor_fail（认证个人失败）
     * auth_company_fail（认证劳务公司失败）
     */
    public void setAuthStatus() {
        User user = getUser();
        String status = user.getStatus();        //认证状态
        String category = user.getCategory();        //类型
        L.d("xxx", "状态" + status);
        String title = getTitleByStatus(status);
        switch (status) {

            case "guest":        //未认证
                AuthenticationFragment authenticationfragment = new AuthenticationFragment();
                fm.beginTransaction().replace(R.id.fl_content, authenticationfragment).commit();
                break;
            case "complete":        //认证完成
                goUserCenter(category);
                break;
            case "auth_investor":        //认证中
            case "auth_worker":
            case "auth_headman":
            case "auth_developers":
                fm.beginTransaction().replace(R.id.fl_content, InfoCommitSuccessFragment
                    .newInstance(category))
                    .commit();
                break;
            case "auth_investor_fail":
            case "auth_worker_fail":
            case "auth_headman_fail":
            case "auth_developers_fail":
                fm.beginTransaction().replace(R.id.fl_content, InfoCommitSuccessFragment
                    .newInstance(category))
                    .commit();
                break;
        }
    }

    /**
     * 认证完成后，进入相应的用户中心
     */
    private void goUserCenter(String category) {
        if (TextUtils.isEmpty(category)) {
            return;
        }
        switch (category) {
            case Constants.INVESTOR:        //个人
                fm.beginTransaction().replace(R.id.fl_content, new InvestorCenterFragment()).commit();
                break;

            case Constants.HEADMAN:        //	工头
                fm.beginTransaction().replace(R.id.fl_content, new HeadmanCenterFragment()).commit();
                break;

            case Constants.WORKER:    //工人
                fm.beginTransaction().replace(R.id.fl_content, new WorkerCenterFragment()).commit();
                break;

            case Constants.DEVELOPERS:    //开发商
                fm.beginTransaction().replace(R.id.fl_content, new DevelopersCenterFragment()).commit();
                break;
        }
    }


    /**
     * 去开发商发布页面
     */
    public void goDeveloperRelease(String staff, String cate) {

        IncreaseDetailFragment increaseDetailFragment = IncreaseDetailFragment.newInstance(staff, cate);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_content, increaseDetailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
//    /**
//     * 去地图选点页面
//     */
//    public void goMapAdd() {
//
//        MapGetAddFragment mf= MapGetAddFragment.newInstance("", "");
//        FragmentTransaction transaction = fm.beginTransaction();
//        transaction.replace(R.id.fl_content, mf);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }

    public void onEvent(BottomTitleClickEvent event) {
        switch (event.getIndex()) {
            case 3:        //我的
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mineRb.toggle();
                    }
                });
                break;
        }
    }

    public void onEvent(RefreshUserEvent event) {
        L.d("RefreshUserEvent");
        loadUserInformation();

//		String category = event.getCategory(); //认证提交后
//		User user = getUser();
//		switch (category) {
//			case Constants.INVESTOR:
//				user.setStatus();
//
//				break;
//
//
//		}

    }

    public String getTitleByStatus(String status) {
        String title = "";
        switch (status) {

            case "guest":

                break;
            case "auth_investor":
                title = "个人认证";
                break;

            case "auth_worker":
                title = "务工人认证";
                break;

            case "auth_headman":
                title = "工长认证";
                break;

            case "auth_developers":
                title = "开发商认证";
                break;
            case "complete":
                title = "我的信息";
                break;
            default:
                title = "";
                break;
        }
        return title;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.d("activity aa");
        if (resultCode == Constants.REQUEST_EXIT) {
            radioGroup.getChildAt(0).performClick();
        }


    }


    /**
     * 友盟版本更新
     */
    public void initUMengUpdate() {
        UmengUpdateAgent.update(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        JPushInterface.init(getApplicationContext());
        JPushInterface.onResume(this);
        boolean b = MyApplication.getInstance().isFlag_home();
        if (b) {
            b = !b;
            MyApplication.getInstance().setFlag_home(b);
            radioGroup.getChildAt(0).performClick();
        }
//        radioGroup.getChildAt(4).performClick();


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }


    /**
     * 双击退出
     */
    public void exitApp() {

        if ((System.currentTimeMillis() - exitTime) > 2000) {
            T.show("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            MyApplication.getInstance().quit();
            finish();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitApp();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void loadUserInformation() {
        DataAccessUtil.userInformation(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


                try {
                    User user = DataParseUtil.userInformation(response);
                    if (null != user) {
                        user.setApp_token(getUser().getApp_token());
                        MyApplication.getInstance().saveUser(user);
                        L.d(user.toString());
                    }
                } catch (ResponseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                L.d(responseString);
            }
        });


    }

    /**
     * 初始化极光推送
     */
    private void initJpush() {
        JPushInterface.setDebugMode(false);
        User user = getUser();
        if (null != user) {
            String id = user.getId();
            // 调用 Handler 来异步设置别名
            mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, id));
        }

        JPushInterface.init(this);
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    L.d(logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
//                    SPUtils.put(Constants.KEY_IS_ALIAS_SETED, true);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    L.i(logs);
                    // 延迟 20 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias),
                        1000 * 20);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    L.d(logs);
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.d("==onDestroy");
        EventBus.getDefault().unregister(this);
    }


    /**
     * 接收选中的工种，发布界面的工种页面
     *
     * @param event
     */
    public void onEvent(OnCheckedWorkTypeEvent event) {
        if (event == null) {
            return;
        }
        WorkType workType = event.getWorkType();
        boolean b = event.isSelected();
        if (event != null && b) {

            data.add(workType);
            releaseWorkTypeFragment = ReleaseWorkTypeFragment.newInstance(workType);
        } else if (event != null && !b) {
            data.remove(workType);
        }
    }

}
