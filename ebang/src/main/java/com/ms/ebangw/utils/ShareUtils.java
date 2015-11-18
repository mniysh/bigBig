package com.ms.ebangw.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.ms.ebangw.R;
import com.ms.ebangw.commons.Constants;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.SmsShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-09-15 16:04
 */
public class ShareUtils {
    public static final UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR);

    /**
     * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
     *       image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
     *       要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
     *       : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
     * @return
     */
    private static void addQQQZonePlatform(Activity activity) {
        String appId = "1104869616";
        String appKey = "FrYtDX9Er9TKkdRj";
        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(activity, appId, appKey);
//        qqSsoHandler.setTargetUrl("http://www.umeng.com/social");
        qqSsoHandler.addToSocialSDK();

        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activity, appId, appKey);
        qZoneSsoHandler.addToSocialSDK();
    }

    /**
     * @功能描述 : 添加微信平台分享
     * @return
     */
    private static void addWXPlatform(Activity activity) {
        // 注意：在微信授权的时候，必须传递appSecret
        // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
        String appId = "wx1ec3d7c0ee36ca87";
        String appSecret = "b38590e30806db0259b2186c7b5afa9a";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(activity, appId, appSecret);
        wxHandler.addToSocialSDK();

        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(activity, appId, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }

    private static void addSinaPlatform(Activity activity) {
        String appId = "250536578";
        String appSecret = "33d07f4f624541337ee8311cd90ff459";
//        SinaSsoHandler sinaSsoHandler = new SinaSsoHandler();
//        sinaSsoHandler.addToSocialSDK();
        //设置新浪SSO handler
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
    }


    /**
     * 添加短信平台</br>
     */
    private static void addSMS() {
        // 添加短信
        SmsHandler smsHandler = new SmsHandler();
        smsHandler.addToSocialSDK();
    }




    public static void directShare(final Activity activity, SHARE_MEDIA share_media,
                                   SocializeListeners.SnsPostListener listener) {

        addWXPlatform(activity);
        addSinaPlatform(activity);


        String title = "亿帮无忧";
        String shareContent = activity.getString(R.string.lottery_desc);// 设置分享内容
        String targetUrl = activity.getString(R.string.url_share);
        try {
            String appChannel = AppUtils.getAppChannel(activity);
            if (TextUtils.equals("ground_push", appChannel)) {
                targetUrl = activity.getString(R.string.url_share_for_ground_push);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        UMImage umImage = new UMImage(activity, activity.getString(R.string.url_logo));// 设置分享图片, 参数2为图片的url地址

        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent.setShareContent(shareContent);
        weixinContent.setTitle(title);
        weixinContent.setTargetUrl(targetUrl);
        weixinContent.setShareMedia(umImage);
        mController.setShareMedia(weixinContent);

        // 设置朋友圈分享的内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setShareContent(shareContent);
        circleMedia.setTitle(title);
        circleMedia.setShareMedia(umImage);
        // circleMedia.setShareMedia(uMusic);
        // circleMedia.setShareMedia(video);
        circleMedia.setTargetUrl(targetUrl);
        mController.setShareMedia(circleMedia);

        //设置新浪微博分享的内容
        SinaShareContent sinaShareContent = new SinaShareContent();
        sinaShareContent.setShareContent(shareContent + targetUrl);
        sinaShareContent.setTitle(title);
        sinaShareContent.setShareImage(umImage);
        sinaShareContent.setTargetUrl(targetUrl);
        mController.setShareMedia(sinaShareContent);

        mController.directShare(activity, share_media, listener);
    }

    public static void shareAllPlatform(Activity activity, String title, String content, String
        targetUrl, String imgUrl) {
        addQQQZonePlatform(activity);
        addWXPlatform(activity);
        addSinaPlatform(activity);
        addSMS();

        mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
            SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA, SHARE_MEDIA.SMS);

        UMImage umImage = new UMImage(activity, imgUrl);// 设置分享图片,图片的url地址

        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent.setShareContent(content);
        weixinContent.setTitle(title);
        weixinContent.setTargetUrl(targetUrl);
        weixinContent.setShareMedia(umImage);
        mController.setShareMedia(weixinContent);

        // 设置朋友圈分享的内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setShareContent(content);
        circleMedia.setTitle(title);
        circleMedia.setShareMedia(umImage);
        circleMedia.setTargetUrl(targetUrl);
        mController.setShareMedia(circleMedia);

        //设置新浪微博分享的内容
        SinaShareContent sinaShareContent = new SinaShareContent();
        sinaShareContent.setShareContent(content + targetUrl);
        sinaShareContent.setTitle(title);
        sinaShareContent.setShareImage(umImage);
        sinaShareContent.setTargetUrl(targetUrl);
        mController.setShareMedia(sinaShareContent);
        //设置分享到QQ的内容
        QQShareContent qqShareContent = new QQShareContent();
        qqShareContent.setTitle(title);
        qqShareContent.setShareContent(content);
        qqShareContent.setShareImage(umImage);
        qqShareContent.setTargetUrl(targetUrl);
        mController.setShareMedia(qqShareContent);
        //设置分享到Qzone的内容
        QZoneShareContent qZoneShareContent = new QZoneShareContent();
        qZoneShareContent.setTitle(title);
        qZoneShareContent.setShareContent(content);
        qZoneShareContent.setShareImage(umImage);
        qZoneShareContent.setTargetUrl(targetUrl);
        mController.setShareMedia(qZoneShareContent);

        SmsShareContent smsShareContent = new SmsShareContent();
//        smsShareContent.setShareImage(umImage);
        smsShareContent.setShareContent(content + targetUrl);
        mController.setShareMedia(smsShareContent);

        mController.openShare(activity, false);
    }


}  
