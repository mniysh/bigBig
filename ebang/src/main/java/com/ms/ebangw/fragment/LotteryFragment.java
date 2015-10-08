package com.ms.ebangw.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.LoginActivity;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.db.UserDao;
import com.ms.ebangw.event.BottomTitleClickEvent;
import com.ms.ebangw.utils.DensityUtils;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.NetUtils;
import com.ms.ebangw.utils.ShareUtils;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.view.ProgressWebView;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.listener.SocializeListeners;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 摇奖Fragment
 */
public class LotteryFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    private ViewGroup contentLayout;
    /**
     * 访问URL
     */
    private String url;

    private static final String TAG = "WebActivity";
    private User user;

    @Bind(R.id.wv_action_web)
    ProgressWebView webview;



    public static LotteryFragment newInstance(String param1, String param2) {
        LotteryFragment fragment = new LotteryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public LotteryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        url = getString(R.string.url_lottery);;

        webview.setWebViewClient(new MyWebViewClient());
        // 设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        webview.requestFocus();
        webview.getSettings().setDefaultTextEncodingName("utf-8");
        user = MyApplication.instance.getUser();

        // 设置web视图客户端
        webview.setDownloadListener(new MyWebViewDownLoadListener());

        webview.addJavascriptInterface(new JsObject(), "share");
        if (!TextUtils.isEmpty(url) && null != user) {
            BDLocation bdLocation = MyApplication.getInstance().getLocation();
            url = url+ "?id=" + user.getId() + "&app_token=" + user.getApp_token() + "&dpi=" +
                getDensityDpi()+ "&w=" + DensityUtils.getWidthInPx(mActivity) + "&h=" +
                DensityUtils.getHeightInPx(mActivity);
            if (null != bdLocation) {
                double latitude = bdLocation.getLatitude();
                double longitude = bdLocation.getLongitude(); //经度
                url = url + "&current_dimensionality=" + latitude + "&current_longitude=" +
                    longitude;
            }
            L.d("webUrl: " + url);
            if (NetUtils.isConnected(mActivity)) {
                webview.loadUrl(url);
            }else {
                T.show("网络异常,请检查网络连接");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_lottery,
            container, false);
        ButterKnife.bind(this, contentLayout);
        initView();
        initData();
        return contentLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    // Web视图
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (NetUtils.isConnected(mActivity)) {
                view.loadUrl(url);
            }else {
                T.show("网络异常,请检查网络连接");
            }
            return true;
        }
    }

    public class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        url = null;
    }

    class JsObject {
        /**
         * 分享到指定平台
         * @param platform  1:微信 2：朋友圈 3：新浪微博
         */
        @JavascriptInterface
        public void sharePlatform(final int platform) {

            directShare(platform);
        }

        /**
         * 打电话
         * @param phone
         */
        @JavascriptInterface
        public void dial(String phone) {
            if (TextUtils.isEmpty(phone)) {
                T.show("号码不能为空");
                return;
            }

            Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:"+ phone));
            startActivity(phoneIntent);

        }

        /**
         * 跳转到登录页面
         */
        @JavascriptInterface
        public void gotoLogin() {

            MyApplication.getInstance().quit();
            UserDao userDao = new UserDao(mActivity);
            userDao.removeAll();

            Intent intent = new Intent(mActivity, LoginActivity.class);
            startActivity(intent);

        }

        /**
         * 跳转到认证页面
         */
        @JavascriptInterface
        public void gotoAuth() {
            EventBus.getDefault().post(new BottomTitleClickEvent(3));

        }

        public void directShare(int platform) {
            SHARE_MEDIA share_media;
            if (platform == 1) {
                share_media = SHARE_MEDIA.WEIXIN;
            } else if (platform == 2) {
                share_media = SHARE_MEDIA.WEIXIN_CIRCLE;
            } else {
                share_media = SHARE_MEDIA.SINA;
            }

            ShareUtils.directShare(mActivity, share_media, new SocializeListeners.SnsPostListener() {

                @Override
                public void onStart() {
                    L.d("Share: onStart");
                }

                @Override
                public void onComplete(SHARE_MEDIA platform, final int eCode, SocializeEntity entity) {
                    L.d("Share: onComplete");
                    String showText = "分享成功";
                    if (eCode != StatusCode.ST_CODE_SUCCESSED) {
                        showText = "分享失败 [" + eCode + "]";
                    }
                    Toast.makeText(mActivity, showText, Toast.LENGTH_SHORT).show();

                    final int p;
                    if (platform == SHARE_MEDIA.WEIXIN) {
                        p = 1;
                    } else if (platform == SHARE_MEDIA.WEIXIN_CIRCLE) {
                        p = 2;
                    } else {
                        p = 3;
                    }

                    onSharedResult(p, eCode == StatusCode.ST_CODE_SUCCESSED ? 1 : 0);
                }

            });
        }
    }

    /**
     * 分享后回调js
     * @param platform 1:微信 2：朋友圈 3：新浪微博
     * @param resultCode 0: 失败， 1：成功
     */
    public void onSharedResult(int platform, int resultCode) {
        String s = "javascript:onSharedResult(" + platform + "," + resultCode + ")";
        webview.loadUrl(s);
    }

    /**
     * 获取dpi
     * @return
     */
    public int getDensityDpi() {
        Display defaultDisplay = mActivity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        int densityDpi = displayMetrics.densityDpi;
        return densityDpi;
    }


}
