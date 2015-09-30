package com.ms.ebangw.bean;

import java.io.Serializable;
import java.util.List;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-09-22 18:57
 */
public class TotalRegion implements Serializable{
    private List<Province> province;
    private List<City> city;
    private List<Area> area;

    public List<Province> getProvince() {
        return province;
    }

    public void setProvince(List<Province> province) {
        this.province = province;
    }

    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }

    public List<Area> getArea() {
        return area;
    }

    public void setArea(List<Area> area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "TotalRegion{" +
                "province=" + province +
                ", city=" + city +
                ", area=" + area +
                '}';
    }
}
