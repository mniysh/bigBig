package com.ms.ebangw.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 省
 * User: WangKai(123940232@qq.com)
 * 2015-09-22 18:57
 */
public class Province implements Serializable{
    private String id;
    private String fid;
    private String name;
    private List<City> citys;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getCitys() {
        return citys;
    }

    public void setCitys(List<City> citys) {
        this.citys = citys;
    }

    @Override
    public String toString() {
        return name;
    }
}
