package com.ms.ebangw.bean;

import java.util.List;

/**
 * 抢单选工友
 * Created by yangshaohua on 2015/11/18.
 */
public class WorkerFriend {
    private String project_id;
    private String craft_id;
    private String ctaft_name;
    private List<Worker> workers;

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getCraft_id() {
        return craft_id;
    }

    public void setCraft_id(String craft_id) {
        this.craft_id = craft_id;
    }

    public String getCtaft_name() {
        return ctaft_name;
    }

    public void setCtaft_name(String ctaft_name) {
        this.ctaft_name = ctaft_name;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }
}
