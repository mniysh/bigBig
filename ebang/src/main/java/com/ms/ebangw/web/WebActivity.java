package com.ms.ebangw.web;

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
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.ShareUtils;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.view.ProgressWebView;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.listener.SocializeListeners;

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
                onSharedResult(1, 1);
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
        if (!TextUtils.isEmpty(url) && null != user) {
            BDLocation bdLocation = MyApplication.getInstance().getLocation();
            url = url+ "?id=" + user.getId() + "&app_token=" + user.getApp_token();
            if (null != bdLocation) {
                double latitude = bdLocation.getLatitude();
                double longitude = bdLocation.getLongitude(); //经度
                url = url + "&current_dimensionality=" + latitude + "&current_longitude=" +
                    longitude;
            }
            L.d("webUrl: " + url);
            webview.loadUrl(url);
        }
        // 设置web视图客户端
        webview.setDownloadListener(new MyWebViewDownLoadListener());

        webview.addJavascriptInterface(new JsObject(), "share");

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        /**使用SSO授权必须添加如下代码 */
//        UMSsoHandler ssoHandler = ShareUtils.mController.getConfig().getSsoHandler(requestCode) ;
//        if(ssoHandler != null){
//            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
//        }
//    }

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

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
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
        public void sharePlatform(final int platform) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    directShare(platform);
                }
            });

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

        public void directShare(int platform) {
            SHARE_MEDIA share_media;
            if (platform == 1) {
                share_media = SHARE_MEDIA.WEIXIN;
            } else if (platform == 2) {
                share_media = SHARE_MEDIA.WEIXIN_CIRCLE;
            } else {
                share_media = SHARE_MEDIA.SINA;
            }

            ShareUtils.directShare(WebActivity.this, share_media, new SocializeListeners.SnsPostListener() {

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
                    Toast.makeText(WebActivity.this, showText, Toast.LENGTH_SHORT).show();

                    final int p;
                    if (platform == SHARE_MEDIA.WEIXIN){
                        p = 1;
                    }else if (platform == SHARE_MEDIA.WEIXIN_CIRCLE) {
                        p = 2;
                    } else {
                        p = 3;
                    }

                    onSharedResult(p, eCode == StatusCode.ST_CODE_SUCCESSED ? 1 : 0);

                }

            });
        }
    }
}