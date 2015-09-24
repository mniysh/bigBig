package com.ms.ebangw.bean;



import java.io.Serializable;
import java.util.List;

/**
 * 首页服务模块的listview部分的bean
 * Created by admin on 2015/9/24.
 */
public class ServiceListbean implements Serializable{

    private String title;
    private List<WorkType > wordTypes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<WorkType> getWordTypes() {
        return wordTypes;
    }

    public void setWordTypes(List<WorkType> wordTypes) {
        this.wordTypes = wordTypes;
    }
}
