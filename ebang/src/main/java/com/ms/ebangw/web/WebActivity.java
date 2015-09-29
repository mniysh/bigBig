package com.ms.ebangw.web;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.view.ProgressWebView;

public class WebActivity extends BaseActivity {

    private ProgressWebView webview;

    /**
     * 访问URL
     */
    private String url;

    private static final String TAG = "WebActivity";
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web);

        initData();

        initView();
    }

    @Override
    public void initView() {
        initTitle("幸运大转盘", "活动规则", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSharedResult(1,1);
            }
        });
        webview = (ProgressWebView) findViewById(R.id.wv_action_web);
        url = getIntent().getStringExtra(Constants.KEY_URL);
        webview.setWebViewClient(new MyWebViewClient());
        // 设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        webview.requestFocus();
        webview.getSettings().setDefaultTextEncodingName("utf-8");
        user = MyApplication.instance.getUser();
        if (!TextUtils.isEmpty(url)) {

            webview.loadUrl(url+ "?id=" + user.getId() + "&app_token=" + user.getApp_token());
        }
        // 设置web视图客户端
        webview.setDownloadListener(new MyWebViewDownLoadListener(this));

        webview.addJavascriptInterface(new JsObject(), "share");

    }

    @Override
    // 设置回退
    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        } else {
            finish();
        }
        return false;
    }

    // Web视图
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        url = null;
    }

    @Override
    public void initData() {
        url = getIntent().getStringExtra(Constants.KEY_URL);
    }

    public class MyWebViewDownLoadListener implements DownloadListener {
        private Context context;

        public MyWebViewDownLoadListener(Context context) {
            this.context = context;
        }

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }







    private class ShareInfo{
        String title;
        String desc;
        String link;
        String imageUrl;
    }

    /**
     * 分享后回调js
     * @param platform 1:微信 2：朋友圈 3：新浪微博
     * @param resultCode 0: 失败， 1：成功
     */
    public void onSharedResult(int platform, int resultCode) {
        webview.loadUrl("javascript:onSharedResult(" + platform + "," + resultCode + ")");
    }

    class JsObject {
        @JavascriptInterface
        public void sharePlatform(int platform) {

        }

        /**
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

    }
}