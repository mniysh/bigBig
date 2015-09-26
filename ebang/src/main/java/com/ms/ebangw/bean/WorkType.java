package com.ms.ebangw.bean;

import java.util.List;

/**
 * 工种
 * Created by admin on 2015/9/24.
 */
public class WorkType {

    private String id;
    private String fid;
    private String name;

    private List<WorkType> workTypes;

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

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public List<WorkType> getWorkTypes() {
        return workTypes;
    }

    public void setWorkTypes(List<WorkType> workTypes) {
        this.workTypes = workTypes;
    }
}
