package com.ms.ebangw.bean;

/**
 * 开发商发布信息
 * User: WangKai(123940232@qq.com)
 * 2015-09-30 09:27
 */
public class DeveloperReleaseInfo {
    private String title;
    private String description;
    private String link_man;
    private String link_phone;
    private String province;
    private String city;
    private String area;
    private String area_other;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 经度
     */
    private String pay_type;
    private String image_ary;
    /**
     * 工种信息
     */
    private String staffs;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink_man() {
        return link_man;
    }

    public void setLink_man(String link_man) {
        this.link_man = link_man;
    }

    public String getLink_phone() {
        return link_phone;
    }

    public void setLink_phone(String link_phone) {
        this.link_phone = link_phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea_other() {
        return area_other;
    }

    public void setArea_other(String area_other) {
        this.area_other = area_other;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getImage_ary() {
        return image_ary;
    }

    public void setImage_ary(String image_ary) {
        this.image_ary = image_ary;
    }

    public String getStaffs() {
        return staffs;
    }

    public void setStaffs(String staffs) {
        this.staffs = staffs;
    }
}
