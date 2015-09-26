package com.ms.ebangw.bean;

/**
 * 工种
 * User: WangKai(123940232@qq.com)
 * 2015-09-26 11:25
 */
public class Craft {
    /**
     * 建筑
     */
    private WorkType building;
    /**
     * 工程管理
     */
    private WorkType projectManage;
    /**
     * 装修
     */
    private WorkType fitment;
    /**
     * 其它
     */
    private WorkType other;

    public WorkType getBuilding() {
        return building;
    }

    public void setBuilding(WorkType building) {
        this.building = building;
    }

    public WorkType getProjectManage() {
        return projectManage;
    }

    public void setProjectManage(WorkType projectManage) {
        this.projectManage = projectManage;
    }

    public WorkType getFitment() {
        return fitment;
    }

    public void setFitment(WorkType fitment) {
        this.fitment = fitment;
    }

    public WorkType getOther() {
        return other;
    }

    public void setOther(WorkType other) {
        this.other = other;
    }
}
