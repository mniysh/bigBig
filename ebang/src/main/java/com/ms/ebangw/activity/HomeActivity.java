package com.ms.ebangw.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.Bank;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.bean.WorkType;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.event.BottomTitleClickEvent;
import com.ms.ebangw.event.OnCheckedWorkTypeEvent;
import com.ms.ebangw.event.RefreshUserEvent;
import com.ms.ebangw.event.WorkTypeEvent;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.fragment.FoundFragment;
import com.ms.ebangw.fragment.HeadmanCenterFragment;
import com.ms.ebangw.fragment.HomeFragment;
import com.ms.ebangw.fragment.LotteryFragment;
import com.ms.ebangw.fragment.ServiceFragment;
import com.ms.ebangw.fragment.WorkerHomeFragment;
import com.ms.ebangw.release.IncreaseDetailFragment;
import com.ms.ebangw.release.MapGetAddFragment;
import com.ms.ebangw.release.ReleaseFragment;
import com.ms.ebangw.release.ReleaseFrament01;
import com.ms.ebangw.release.ReleaseWorkTypeFragment;
import com.ms.ebangw.release.SelectCraftFragment;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;
import com.umeng.update.UmengUpdateAgent;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import de.greenrobot.event.EventBus;

/**
 * Home主页面
 *
 * @author admin
 */
public class HomeActivity extends BaseActivity {
    private FragmentManager fm;
    private long exitTime = 0;
    private List<WorkType> data;
    private FoundFragment foundFragment;
    private ReleaseFragment releasefragment;
    private ServiceFragment serviceFragment;
    private ReleaseFrament01 releaseFrament01;
    private ReleaseWorkTypeFragment releaseWorkTypeFragment;

    private WorkerHomeFragment workerHomeFragment, eMallFragment;

    private SelectCraftFragment selectCraftFragment;
    private LotteryFragment lotteryFragment;
    private List<Bank> banks;
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
        public void handleMessage(android.os.Message msg) {
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
//        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        fm = getFragmentManager();
        L.d("HomeActivity onCreate ");
        if (savedInstanceState != null) {
            L.d(savedInstanceState.toString());
            initView();
            initData();
        }else {
            initView();
            initData();
            initUMengUpdate();
            initJpush();
            radioGroup.getChildAt(0).performClick();
        }
    }

    public void initView() {
        selectWorkType = new ArrayList<WorkType>();

    }
    public List<WorkType> getSelectWorkType(){
        return selectWorkType;
    }


    @Override
    public void initData() {
        loadUserInformation();
        fm = getFragmentManager();
        lotteryFragment = LotteryFragment.newInstance("lotteryFragment", "lotteryFragment");
        workerHomeFragment = WorkerHomeFragment.newInstance(R.drawable.worker_home, 2);
        eMallFragment = WorkerHomeFragment.newInstance(R.drawable.e_mall, 1);
        releaseFrament01 = new ReleaseFrament01();
        foundFragment = new FoundFragment();
        serviceFragment = new ServiceFragment();


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
						fm.beginTransaction().replace(R.id.fl_content, new SelectCraftFragment()).commit();
//                        fm.beginTransaction().replace(R.id.fl_content, eMallFragment).commit();
                        break;
                    case R.id.rb_service:
                       // L.d("xxx","被点击");
						fm.beginTransaction().replace(R.id.fl_content, serviceFragment).commit();
//                        fm.beginTransaction().replace(R.id.fl_content, eMallFragment).commit();
                        break;

                    case R.id.rb_mine:

                        L.d("xxx", "返回值是" + isLogin());
                        if (isLogin()) {
                            User user = getUser();
                            String category = user.getCategory();
                            L.d("xxx", "user的内容主页部分的" + user.toString());
                            goCenter();

                        } else {
                            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        break;
                }
            }
        });

    }
    public void onEvent(WorkTypeEvent event){
        WorkType workType = event.getWorkType();
        boolean isAdd = event.isAdd();
        if(workType != null && isAdd){
            selectWorkType.add(workType);
        }else{
            selectWorkType.remove(workType);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    /**
     * 根据人员类型跳转到相应的内容
     * category	//用户已认证类型  worker(工人)/headman(工头)/developers(开发商)/investor(个人)  null（未认证）
     * <p/>
     * /认证中
     * status: 			   状态游客guest
     * auth_developers(认证开发者中)/
     * auth_worker(认证工人中)/
     * auth_headman(认证工头中)/
     * auth_investor(认证个人中)/
     * complete（完成认证)
     */
    public void goCenter() {
        User user = getUser();
        String status = user.getStatus();        //认证状态
        String category = user.getCategory();        //类型
        L.d("xxx", "状态" + status);
        String title = getTitleByStatus(status);
//        switch (status) {
//
//            case "guest":        //未认证
//            case "complete":        //认证完成
//                AuthenticationFragment authenticationfragment = new AuthenticationFragment();
//                fm.beginTransaction().replace(R.id.fl_content, authenticationfragment).commit();
//                break;
//            case "auth_investor":        //认证中
//            case "auth_worker":
//            case "auth_headman":
//            case "auth_developers":
//                fm.beginTransaction().replace(R.id.fl_content, InfoCommitSuccessFragment
//                    .newInstance(category))
//                    .commit();
//                break;
//        }
        fm.beginTransaction().replace(R.id.fl_content, new HeadmanCenterFragment()).commit();
        return;

//		switch (category) {
//			case Constants.INVESTOR:        //个人
//				fm.beginTransaction().replace(R.id.fl_content, new InvestorCenterFragment()).commit();
//				break;
//
//			case Constants.HEADMAN:        //	工头
//				fm.beginTransaction().replace(R.id.fl_content, new HeadmanCenterFragment()).commit();
//				break;
//
//			case Constants.WORKER:    //工人
//				fm.beginTransaction().replace(R.id.fl_content, new WorkerCenterFragment()).commit();
//				break;
//
//			case Constants.DEVELOPERS:    //开发商
//				fm.beginTransaction().replace(R.id.fl_content, new DevelopersCenterFragment()).commit();
//				break;
//
//		}
    }


    /**
     * 去开发商发布页面
     */
    public void goDeveloperRelease(String staff) {

        IncreaseDetailFragment increaseDetailFragment = IncreaseDetailFragment.newInstance(staff, "");
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_content, increaseDetailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    /**
     * 去地图选点页面
     */
    public void goMapAdd() {

        MapGetAddFragment mf= MapGetAddFragment.newInstance("", "");
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_content, mf);
        transaction.addToBackStack(null);
        transaction.commit();
    }

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
        L.d("loadUserInformation");
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
            String logs ;
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
     * @param event
     *
     */
    public void onEvent(OnCheckedWorkTypeEvent event){
        if(event == null){
            return;
        }
        WorkType workType = event.getWorkType();
        boolean b = event.isSelected();
        if(event != null && b){

            data.add(workType);
            releaseWorkTypeFragment = ReleaseWorkTypeFragment.newInstance(workType);
        }else if(event != null && !b){
            data.remove(workType);
        }
    }

}
