package com.ms.ebangw.bean;

import java.util.List;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-11-26 10:20
 */
public class CheckedWorkTypeUser {

    /**
     * project_id : 119
     * craft_id : 119
     * total_number : 119
     * success_number : 119
     * dvalue_number : 119
     * dataList : [{"login_id":100068,"phone":"18031192001","head_image":"2a6ad06ef6c4787908f60e29774ddf33.jpg","real_name":"时则会"},{"login_id":10519,"phone":"18601176297","head_image":"9dab91d58d5ef7bd5e71c43cb5cb5347.png","real_name":"王凯"},{"login_id":10514,"phone":"18518716740","head_image":"41d1e8555c561570ffad51d8a4a59a74.jpg","real_name":"康建军"}]
     */

    private String project_id;
    private String craft_id;
    private String total_number;
    private String success_number;
    private String dvalue_number;
    /**
     * login_id : 100068
     * phone : 18031192001
     * head_image : 2a6ad06ef6c4787908f60e29774ddf33.jpg
     * real_name : 时则会
     */

    private List<People> dataList;

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

    public String getTotal_number() {
        return total_number;
    }

    public void setTotal_number(String total_number) {
        this.total_number = total_number;
    }

    public String getSuccess_number() {
        return success_number;
    }

    public void setSuccess_number(String success_number) {
        this.success_number = success_number;
    }

    public String getDvalue_number() {
        return dvalue_number;
    }

    public void setDvalue_number(String dvalue_number) {
        this.dvalue_number = dvalue_number;
    }

    public List<People> getDataList() {
        return dataList;
    }

    public void setDataList(List<People> dataList) {
        this.dataList = dataList;
    }
}
