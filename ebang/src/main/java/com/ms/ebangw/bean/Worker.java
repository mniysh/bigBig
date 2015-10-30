package com.ms.ebangw.bean;

import com.ms.ebangw.utils.PinYinUtils;

import java.util.List;

/**
 * 工人
 * User: WangKai(123940232@qq.com)
 * 2015-10-29 15:10
 */
public class Worker implements Comparable<Worker>{
    private String id;
    private String head_image;
    private String real_name;

    /**
     * 工种
     */
    private List<WorkType> craft ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHead_image() {
        return head_image;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public List<WorkType> getCraft() {
        return craft;
    }

    public void setCraft(List<WorkType> craft) {
        this.craft = craft;
    }

    public String getPinyin() {
        return PinYinUtils.getPinyin(real_name);
    }

    @Override
    public int compareTo(Worker another) {
        return PinYinUtils.getPinyin(real_name).compareTo(PinYinUtils.getPinyin(another
            .getReal_name()));
    }
}
