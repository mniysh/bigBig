package com.ms.ebangw.web;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.utils.NetUtils;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.view.ProgressWebView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebActivity extends BaseActivity {

    @Bind(R.id.wv_action_web)
    ProgressWebView webView;
    /**
     * 访问URL
     */
    private String url;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            title = extras.getString(Constants.KEY_WEB_TITLE);
            url = extras.getString(Constants.KEY_URL);
        }

        initData();
        initView();
    }

    @Override
    public void initView() {
        initTitle(null, "返回", title, null, null);
        initWebSetting(webView);

        if (!TextUtils.isEmpty(url)) {
            if (NetUtils.isConnected(this)) {
                webView.loadUrl(url);
            } else {
                T.show("网络异常,请检查网络连接");
            }
        }

    }

    private void initWebSetting(WebView webView) {
        webView.setWebViewClient(new MyWebViewClient());
        // 设置WebView属性，能够执行Javascript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        webView.requestFocus();
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        // 设置web视图客户端
        webView.setDownloadListener(new MyWebViewDownLoadListener());
    }

    @Override
    // 设置回退
    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); // goBack()表示返回WebView的上一页面
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
            if (NetUtils.isConnected(WebActivity.this)) {
                view.loadUrl(url);
            } else {
                T.show("网络异常,请检查网络连接");
            }
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

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
}