package com.ms.ebangw.bean;

import java.util.List;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-11-03 10:33
 */
public class HomeProjectInfo {
    private List<RecommendedDeveoper> developers;
    private List<ReleaseProject> project;

    public List<RecommendedDeveoper> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<RecommendedDeveoper> developers) {
        this.developers = developers;
    }

    public List<ReleaseProject> getProject() {
        return project;
    }

    public void setProject(List<ReleaseProject> project) {
        this.project = project;
    }
}
