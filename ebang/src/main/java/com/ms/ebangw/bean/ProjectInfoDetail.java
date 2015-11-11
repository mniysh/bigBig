package com.ms.ebangw.bean;

import java.util.List;

/**
 *  发布的工程详情界面
 * User: WangKai(123940232@qq.com)
 * 2015-11-03 11:04
 */
public class ProjectInfoDetail {

    /**
     * id : 7
     * developers_id : 577
     * title : 测试1
     * description : 描述
     * address : 北京北京昌平
     * start_time : 2015-10-28 00:00:00
     * end_time : 2015-10-29 00:00:00
     * link_man : 杨
     * link_phone : 13261519276
     * images : ["http://www.labour.com/uploads/images/1.jpg","http://www.labour.com/uploads/images/2.jpg"]
     * staffs : [{"id":49,"craft_id":1,"money":100,"status":"prepare","project_id":7,"staff_account":2,"staff_description":null,"start_time":"2015-10-28 00:00:00","end_time":"2015-10-29 00:00:00","account_days":2,"craft_name":"工程管理"},{"id":50,"craft_id":2,"money":100,"status":"prepare","project_id":7,"staff_account":1,"staff_description":null,"start_time":"2015-10-28 00:00:00","end_time":"2015-10-29 00:00:00","account_days":2,"craft_name":"工长"}]
     */

    private int id;
    private int developers_id;
    private String title;
    private String description;
    private String address;
    private String start_time;
    private String end_time;
    private String link_man;
    private String link_phone;
    private List<String> images;
    /**
     * id : 49
     * craft_id : 1
     * money : 100
     * status : prepare
     * project_id : 7
     * staff_account : 2
     * staff_description : null
     * start_time : 2015-10-28 00:00:00
     * end_time : 2015-10-29 00:00:00
     * account_days : 2
     * craft_name : 工程管理
     */

    private String money;
    private String staff_account;
    private String account_days;
    private String craft_name;

    private List<Staff> staffs;

    public void setId(int id) {
        this.id = id;
    }

    public void setDevelopers_id(int developers_id) {
        this.developers_id = developers_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public void setLink_man(String link_man) {
        this.link_man = link_man;
    }

    public void setLink_phone(String link_phone) {
        this.link_phone = link_phone;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }



    public int getId() {
        return id;
    }

    public int getDevelopers_id() {
        return developers_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getLink_man() {
        return link_man;
    }

    public String getLink_phone() {
        return link_phone;
    }

    public List<String> getImages() {
        return images;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getStaff_account() {
        return staff_account;
    }

    public void setStaff_account(String staff_account) {
        this.staff_account = staff_account;
    }

    public String getAccount_days() {
        return account_days;
    }

    public void setAccount_days(String account_days) {
        this.account_days = account_days;
    }

    public String getCraft_name() {
        return craft_name;
    }

    public void setCraft_name(String craft_name) {
        this.craft_name = craft_name;
    }

    public List<Staff> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<Staff> staffs) {
        this.staffs = staffs;
    }
}
