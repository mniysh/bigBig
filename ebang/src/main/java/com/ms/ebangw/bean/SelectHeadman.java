package com.ms.ebangw.bean;

/**
 * Created by yangshaohua .
 * Created by on 2015/11/24
 */
public class SelectHeadman {
    private String id;
    private String project_id;
    private String contend_id;
    private String contend_status;
    private String real_name;

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getContend_id() {
        return contend_id;
    }

    public void setContend_id(String contend_id) {
        this.contend_id = contend_id;
    }

    public String getContend_status() {
        return contend_status;
    }

    public void setContend_status(String contend_status) {
        this.contend_status = contend_status;
    }
}
