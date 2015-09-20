//package com.ms.ebangw.activity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.KeyEvent;
//import android.webkit.DownloadListener;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//
//import com.sfc.vv.R;
//import com.vvpinche.VVPincheApplication;
//import com.vvpinche.common.Constant;
//import com.vvpinche.util.JavascriptBridge;
//import com.vvpinche.util.wechat.ShareUtil;
//import com.vvpinche.view.ProgressWebView;
//
//import org.json.JSONObject;
//
//import cn.sharesdk.framework.Platform;
//
//public class WebActivity extends BaseActivity {
//
//    private ProgressWebView webview;
//
//    /**
//     * 访问URL
//     */
//    private String url;
//
//    private String title;
//
//    private static final String TAG = "WebActivity";
//    private String sessionId;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_web);
//
//        initData();
//
//        initView();
//    }
//
//    @Override
//    public void initView() {
////		setTitle(title);
////		initTitleBackView();
//        setCommonTitle(new OnLeftClickListener() {
//            @Override
//            public void onLeftClick() {
//                finish();
//            }
//        }, title, null, null);
//
//        webview = (ProgressWebView) findViewById(R.id.wv_action_web);
//        url = getIntent().getStringExtra(Constant.KEY_URL);
//        webview.setWebViewClient(new MyWebViewClient());
//        // 设置WebView属性，能够执行Javascript脚本
//        webview.getSettings().setJavaScriptEnabled(true);
//        webview.requestFocus();
//        webview.getSettings().setDefaultTextEncodingName("utf-8");
//        sessionId = VVPincheApplication.getInstance().getUser().getSessionId();
//        if (url != null) {
//            // 添加sessionId信息
//            if (!TextUtils.isEmpty(sessionId)) {
//                if (url.contains("?")) {
//                    webview.loadUrl(url + "&sessionid=" + sessionId);
//                } else {
//                    webview.loadUrl(url + "?sessionid=" + sessionId);
//                }
//            } else {
//                webview.loadUrl(url);
//            }
//        }
//        // 设置web视图客户端
//        webview.setDownloadListener(new MyWebViewDownLoadListener(this));
//
//        JavascriptBridge jsb = new JavascriptBridge(webview);
//        jsb.addJavaMethod("share2Wechat", new JavascriptBridge.Function() {
//            @Override
//            public Object execute(JSONObject params) {
////                showToast("分享： " + params.toString());
//                ShareInfo shareInfo = new ShareInfo();
//                shareInfo.title = params.optString("title");
//                shareInfo.imageUrl = params.optString("imgUrl");
//                shareInfo.desc = params.optString("desc");
//                shareInfo.link = params.optString("link");
//                showShareDialog(shareInfo);
//                return null;
//            }
//        });
//
//    }
//
//    @Override
//    // 设置回退
//    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
//            webview.goBack(); // goBack()表示返回WebView的上一页面
//            return true;
//        } else {
//            finish();
//        }
//        return false;
//    }
//
//    // Web视图
//    private class MyWebViewClient extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
//            return true;
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        url = null;
//        title = null;
//    }
//
//
//
//    @Override
//    public void initData() {
//        title = getIntent().getStringExtra("title");
//        url = getIntent().getStringExtra(Constant.KEY_URL);
//    }
//
//    public class MyWebViewDownLoadListener implements DownloadListener {
//        private Context context;
//
//        public MyWebViewDownLoadListener(Context context) {
//            this.context = context;
//        }
//
//        @Override
//        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
//            Uri uri = Uri.parse(url);
//            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            context.startActivity(intent);
//        }
//    }
//
//
//
//
//    public void showShareDialog(final ShareInfo shareInfo) {
//
//        if (null == shareInfo){
//            return;
//        }
//
//        final Platform.ShareParams shareParams = new Platform.ShareParams();
//        shareParams.setTitle(shareInfo.title);
//        shareParams.setTitleUrl(getResources().getString(R.string.share_url));
//        shareParams.setText(shareInfo.desc);
//        shareParams.setImageUrl(shareInfo.imageUrl);
//        shareParams.setUrl(shareInfo.link);
//        ShareUtil.showShareDialog(this, shareParams);
//    }
//
//
//    private class ShareInfo{
//        String title;
//        String desc;
//        String link;
//        String imageUrl;
//    }
//}
