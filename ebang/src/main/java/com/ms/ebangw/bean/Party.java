package com.ms.ebangw.bean;

import java.util.List;

/**
 * 社区  发布的活动
 * User: WangKai(123940232@qq.com)
 * 2015-12-02 14:13
 */
public class Party {

    /**
     * id : 5
     * real_name : 杨李鹏
     * gender : male
     * head_image :
     * title : 阿萨德
     * theme : 孔垂楠草草哦按搜拉长
     * created_at : 2015-12-01 12:05:14
     * active_image : ["http://www.labour.com/uploads/images/0a05a403aacdfd2429b92c95527011f0.jpg"]
     */

    private String id;
    private String real_name;
    private String gender;
    private String head_image;
    private String title;
    private String theme;
    private String created_at;
    private List<String> active_image;

    public void setId(String id) {
        this.id = id;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setActive_image(List<String> active_image) {
        this.active_image = active_image;
    }

    public String getId() {
        return id;
    }

    public String getReal_name() {
        return real_name;
    }

    public String getGender() {
        return gender;
    }

    public String getHead_image() {
        return head_image;
    }

    public String getTitle() {
        return title;
    }

    public String getTheme() {
        return theme;
    }

    public String getCreated_at() {
        return created_at;
    }

    public List<String> getActive_image() {
        return active_image;
    }
}
