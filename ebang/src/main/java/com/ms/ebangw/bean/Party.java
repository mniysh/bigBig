package com.ms.ebangw.bean;

import java.util.List;

/**
 * 社区  发布的活动
 * User: WangKai(123940232@qq.com)
 * 2015-12-02 14:13
 */
public class Party {

    private String active_id;
    private String real_name;
    private String gender;
    private String head_image;
    private String title;
    private String theme;
    private String created_at;
    private List<String> active_image;


    private String province;
    private String city;
    private String area_other;
    private String number_people;
    private String start_time;
    private String end_time;
    private String price;
    /**
     * （flag=1就展示报名人数，为2就展示正在审核中,3为审核失败，4为已结束）
     */
    private String flag;
    /**
     * (如果状态flag=1的话，就会查看报名人数)
     */
    private String apply_count;


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

    public String getActive_id() {
        return active_id;
    }

    public void setActive_id(String active_id) {
        this.active_id = active_id;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea_other() {
        return area_other;
    }

    public void setArea_other(String area_other) {
        this.area_other = area_other;
    }

    public String getNumber_people() {
        return number_people;
    }

    public void setNumber_people(String number_people) {
        this.number_people = number_people;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getApply_count() {
        return apply_count;
    }

    public void setApply_count(String apply_count) {
        this.apply_count = apply_count;
    }
}
