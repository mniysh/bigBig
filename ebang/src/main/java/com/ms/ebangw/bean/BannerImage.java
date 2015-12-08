package com.ms.ebangw.bean;

/**
 * Banner广告栏的图片信息
 * User: WangKai(123940232@qq.com)
 * 2015-11-04 10:55
 */
public class BannerImage {

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
}
