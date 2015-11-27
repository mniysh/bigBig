package com.ms.ebangw.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.db.UserDao;
import com.ms.ebangw.service.RequestUrl;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.NetUtils;
import com.ms.ebangw.utils.ShareUtils;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.view.ProgressWebView;
import com.umeng.socialize.sso.UMSsoHandler;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 邀友返现
 *
 * @author wangkai
 */
public class InviteWithCashActivity extends BaseActivity {

    private String url;

    @Bind(R.id.wv_action_web)
    ProgressWebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_with_cash);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = ShareUtils.mController.getConfig().getSsoHandler(requestCode) ;
        if(ssoHandler != null){
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

        url = RequestUrl.invite_friends;
        webview.setWebViewClient(new MyWebViewClient());
        // 设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        webview.requestFocus();
        webview.getSettings().setDefaultTextEncodingName("utf-8");
        User user = getUser();

        // 设置web视图客户端
        webview.setDownloadListener(new MyWebViewDownLoadListener());

        webview.addJavascriptInterface(new JsInviteObject(), "invite");
        if (!TextUtils.isEmpty(url) && null != user) {
            url = url + "?id=" + user.getId() + "&app_token=" + user.getApp_token();
            if (NetUtils.isConnected(InviteWithCashActivity.this)) {
                L.d(url);
                webview.loadUrl(url);
            } else {
                T.show("网络异常,请检查网络连接");
            }
        }
    }

    class JsInviteObject {
        /**
         * 分享到所有平台
         *
         * @param title     标题
         * @param content   内容
         * @param targetUrl 要跳转的url
         * @param imgUrl    logo图片的url
         */
        @JavascriptInterface
        public void invite(final String title, final String content, final String targetUrl, final String imgUrl) {
            webview.post(new Runnable() {
                @Override
                public void run() {
                    ShareUtils.shareAllPlatform(InviteWithCashActivity.this, title, content,
                        targetUrl, imgUrl);
                }
            });
        }

        /**
         * 短信分享
         *
         * @param smsBody
         */
        @JavascriptInterface
        public void sendMessage(String smsBody) {
            Uri smsToUri = Uri.parse("smsto:");
            Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
            //sendIntent.putExtra("address", "123456"); // 电话号码，这行去掉的话，默认就没有电话
            //短信内容
            sendIntent.putExtra("sms_body", smsBody);
            sendIntent.setType("vnd.android-dir/mms-sms");
            startActivityForResult(sendIntent, 1002);
        }

        /**
         * 跳转到登录页面
         */
        @JavascriptInterface
        public void gotoLogin() {
            MyApplication.getInstance().quit();
            UserDao userDao = new UserDao(InviteWithCashActivity.this);
            userDao.removeAll();

            Intent intent = new Intent(InviteWithCashActivity.this, LoginActivity.class);
            startActivity(intent);

        }
    }

    // Web视图
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (NetUtils.isConnected(InviteWithCashActivity.this)) {
                view.loadUrl(url);
            } else {
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

}
