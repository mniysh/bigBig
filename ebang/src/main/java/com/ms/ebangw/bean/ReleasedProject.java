package com.ms.ebangw.bean;

/**
 * 开发商发布的工程
 * User: WangKai(123940232@qq.com)
 * 2015-11-03 09:13
 */
public class ReleasedProject {

    /**
     * id : 7
     * developer_id : 577
     * title : 测试1
     * description : 描述
     * images : http://www.labour.com/uploads/images/1.jpg
     * province : 北京
     * city : 北京
     * project_type : company
     * project_type_name : 开发商
     * grab_num : 0
     */

    private String id;
    private String developer_id;
    private String title;
    private String description;
    private String images;
    private String province;
    private String city;
    private String project_type;
    private String project_type_name;
    private String grab_num;

    public void setId(String id) {
        this.id = id;
    }

    public void setDeveloper_id(String developer_id) {
        this.developer_id = developer_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProject_type(String project_type) {
        this.project_type = project_type;
    }

    public void setProject_type_name(String project_type_name) {
        this.project_type_name = project_type_name;
    }

    public void setGrab_num(String grab_num) {
        this.grab_num = grab_num;
    }

    public String getId() {
        return id;
    }

    public String getDeveloper_id() {
        return developer_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImages() {
        return images;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getProject_type() {
        return project_type;
    }

    public String getProject_type_name() {
        return project_type_name;
    }

    public String getGrab_num() {
        return grab_num;
    }
}
