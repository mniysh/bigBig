package com.ms.ebangw.utils;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.ms.ebangw.R;
import com.ms.ebangw.commons.Constants;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-09-15 16:04
 */
public class ShareUtils {
    public static final UMSocialService mController = UMServiceFactory.getUMSocialService(Constants
        .DESCRIPTOR);

    // 配置需要分享的相关平台
//    configPlatforms();
//    // 设置分享的内容
//    setShareContent();

    static {

        // 配置需要分享的相关平台
//        configPlatforms();
//    // 设置分享的内容
//    setShareContent();

    }


    /**
     * 配置分享平台参数</br>
     */
    private static void configPlatforms(Activity activity) {
        // 添加新浪SSO授权
//        mController.getConfig().setSsoHandler(new SinaSsoHandler());

        // 添加QQ平台
//        addQQPlatform(activity, "");

        // 添加微信、微信朋友圈平台
        addWXPlatform(activity);

    }

    /**
     * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
     *       image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
     *       要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
     *       : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
     * @param targetUrl       QQ分享内容的target url
     * @return
     */
    private static void addQQPlatform(Activity activity, String targetUrl) {
        String appId = "1104869616";
        String appKey = "FrYtDX9Er9TKkdRj";
        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(activity,appId, appKey);
        if (!TextUtils.isEmpty(targetUrl)) {
            qqSsoHandler.setTargetUrl(targetUrl);
        }else {
            qqSsoHandler.setTargetUrl(activity.getString(R.string.url_download));
        }

        qqSsoHandler.addToSocialSDK();

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
     * 根据不同的平台设置不同的分享内容</br>
     */
    private void setShareContent(Activity activity) {

        // 配置SSO
        mController.getConfig().setSsoHandler(new SinaSsoHandler());

      
        mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能。http://www.umeng.com/social");

   

        UMImage localImage = new UMImage(activity, R.drawable.ms_logo);
        UMImage urlImage = new UMImage(activity,
            "http://www.umeng.com/images/pic/social/integrated_3.png");
        // UMImage resImage = new UMImage(activity, R.drawable.icon);

        // 视频分享
        UMVideo video = new UMVideo(
            "http://v.youku.com/v_show/id_XNTc0ODM4OTM2.html");
        // vedio.setThumb("http://www.umeng.com/images/pic/home/social/img-1.png");
        video.setTitle("友盟社会化组件视频");
        video.setThumb(urlImage);

        UMusic uMusic = new UMusic(
            "http://music.huoxing.com/upload/20130330/1364651263157_1085.mp3");
        uMusic.setAuthor("umeng");
        uMusic.setTitle("天籁之音");
        // uMusic.setThumb(urlImage);
        uMusic.setThumb("http://www.umeng.com/images/pic/social/chart_1.png");

        // UMEmoji emoji = new UMEmoji(activity,
        // "http://www.pc6.com/uploadimages/2010214917283624.gif");
        // UMEmoji emoji = new UMEmoji(activity,
        // "/storage/sdcard0/emoji.gif");

        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent
            .setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能-微信。http://www.umeng.com/social");
        weixinContent.setTitle("友盟社会化分享组件-微信");
        weixinContent.setTargetUrl("http://www.umeng.com/social");
        weixinContent.setShareMedia(urlImage);
        mController.setShareMedia(weixinContent);

        // 设置朋友圈分享的内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia
            .setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能-朋友圈。http://www.umeng.com/social");
        circleMedia.setTitle("友盟社会化分享组件-朋友圈");
        circleMedia.setShareMedia(urlImage);
        // circleMedia.setShareMedia(uMusic);
        // circleMedia.setShareMedia(video);
        circleMedia.setTargetUrl("http://www.umeng.com/social");
        mController.setShareMedia(circleMedia);


//        UMImage qzoneImage = new UMImage(activity,
//            "http://www.umeng.com/images/pic/social/integrated_3.png");
//        qzoneImage
//            .setTargetUrl("http://www.umeng.com/images/pic/social/integrated_3.png");
//
//        // 设置QQ空间分享内容
//        QZoneShareContent qzone = new QZoneShareContent();
//        qzone.setShareContent("share test");
//        qzone.setTargetUrl("http://www.umeng.com");
//        qzone.setTitle("QZone title");
//        qzone.setShareMedia(urlImage);
//        // qzone.setShareMedia(uMusic);
//        mController.setShareMedia(qzone);

        video.setThumb(new UMImage(activity, BitmapFactory.decodeResource(
            activity.getResources(), R.drawable.ms_logo)));

        QQShareContent qqShareContent = new QQShareContent();
        qqShareContent.setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能 -- QQ");
        qqShareContent.setTitle("hello, title");
//        qqShareContent.setShareMedia(image);
        qqShareContent.setTargetUrl("http://www.umeng.com/social");
        mController.setShareMedia(qqShareContent);


        SinaShareContent sinaContent = new SinaShareContent();
        sinaContent
            .setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能-新浪微博。http://www.umeng.com/social");
        sinaContent.setShareImage( new UMImage( activity, R.drawable.ms_logo));
        mController.setShareMedia(sinaContent);

    }

    /**
     * 分享
     * @param activity
     * @param content   分享的内容
     * @param url       分享的链接
     * @param imageUrl  分享的图片链接
     */
    public static void share(Activity activity, String content, String url, String imageUrl) {

        // 设置分享内容
        mController.setShareContent(content);
        // 设置分享图片, 参数2为图片的url地址
        mController.setShareMedia(new UMImage(activity, imageUrl));
//        设置本地绝对路径图片
//        mController.setShareMedia(new UMImage(activity, BitmapFactory.decodeFile
//            ("/mnt/sdcard/icon.png")));

//        设置本地图片
//        mController.setShareMedia(new UMImage(getActivity(), R.drawable.icon));

        addQQPlatform(activity, url);
        addWXPlatform(activity);
//        mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
//            SHARE_MEDIA.QQ, SHARE_MEDIA.SINA);

         mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
            SHARE_MEDIA.SINA);
        mController.openShare(activity, false);
    }

    public static void directShare(final Activity activity, SHARE_MEDIA share_media,
                                   SocializeListeners.SnsPostListener listener) {

        addWXPlatform(activity);
        addSinaPlatform(activity);


        String title = "亿帮无忧";
        String shareContent = activity.getString(R.string.lottery_desc);// 设置分享内容
        String targetUrl = activity.getString(R.string.url_share);
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


}  
