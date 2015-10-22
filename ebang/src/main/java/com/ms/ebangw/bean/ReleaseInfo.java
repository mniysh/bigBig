package com.ms.ebangw.bean;

/**
 * Created by admin on 2015/10/20.
 */
public class ReleaseInfo {
    private String id;
    private String no;
    private String province;
    private String city;
    private String area;
    private String area_other;
    private String account_staffs;
    private String total_money;
    private String privinceId;

    public String getPrivinceId() {
        return privinceId;
    }

    public void setPrivinceId(String privinceId) {
        this.privinceId = privinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    private String cityId;
    private String areaId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
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

    public String getAccount_staffs() {
        return account_staffs;
    }

    public void setAccount_staffs(String account_staffs) {
        this.account_staffs = account_staffs;
    }

    public String getTotal_money() {
        return total_money;
    }

    public void setTotal_money(String total_money) {
        this.total_money = total_money;
    }

    @Override
    public String toString() {
        return "ReleaseInfo{" +
                "id='" + id + '\'' +
                ", no='" + no + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", area_other='" + area_other + '\'' +
                ", account_staffs='" + account_staffs + '\'' +
                ", total_money='" + total_money + '\'' +
                '}';
    }
}
