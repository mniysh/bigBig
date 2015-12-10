package com.ms.ebangw.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Banner广告栏的图片信息
 * User: WangKai(123940232@qq.com)
 * 2015-11-04 10:55
 */
public class BannerImage implements Parcelable {

    /**
     * image_url : http://xxx/28b75ee52ae4c46d163104cef7a39a6d.jpg
     * article_url : http://xxx/phone/common/article/content/1/3
     */

    /**
     * 图片地址
     */
    private String image_url;
    /**
     * 文章地址
     */
    private String article_url;

    /**
     * 标题
     */
    private String title;

    /**
     * 0普通 1抽奖 2邀友返现
     */
    private String type;

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setArticle_url(String article_url) {
        this.article_url = article_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getArticle_url() {
        return article_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.image_url);
        dest.writeString(this.article_url);
        dest.writeString(this.title);
        dest.writeString(this.type);
    }

    public BannerImage() {
    }

    protected BannerImage(Parcel in) {
        this.image_url = in.readString();
        this.article_url = in.readString();
        this.title = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<BannerImage> CREATOR = new Parcelable.Creator<BannerImage>() {
        public BannerImage createFromParcel(Parcel source) {
            return new BannerImage(source);
        }

        public BannerImage[] newArray(int size) {
            return new BannerImage[size];
        }
    };
}
