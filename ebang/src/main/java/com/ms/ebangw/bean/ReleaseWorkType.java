package com.ms.ebangw.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2015/9/27.
 */
public class ReleaseWorkType implements Serializable {
    private String id;
    private String name;
    private List<WorkType> datas;

    @Override
    public String toString() {
        return "ReleaseWorkType{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", datas=" + datas +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WorkType> getDatas() {
        return datas;
    }

    public void setDatas(List<WorkType> datas) {
        this.datas = datas;
    }
}
