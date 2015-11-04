package com.ms.ebangw.bean;

/**
 * Banner广告栏的图片信息
 * User: WangKai(123940232@qq.com)
 * 2015-11-04 10:55
 */
public class BannerImage {
    private String imageUrl;
    private String webUrl;
    private int imgResId;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }
}
