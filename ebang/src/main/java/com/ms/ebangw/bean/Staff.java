package com.ms.ebangw.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 开发商发布页面的工种具体雇佣信息
 * User: WangKai(123940232@qq.com)
 * 2015-09-30 09:47
 */
public class Staff implements Parcelable {
    private String id;

    /**
     * 工种
     */
    private String craft_id;
    /**
     * 工人数量
     */
    private String staff_account;
    /**
     * 工资
     */
    private String money;
    /**
     * 开始时间 格式:2015-9-12
     */
    private String start_time;
    private String end_time;
    /**
     * 状态
     */
    private String status;
    private String project_id;
    private String staff_description;
    private String account_days;
    private String craft_name;
    private String isContend;


    public String getIsContend() {
        return isContend;
    }

    public void setIsContend(String isContend) {
        this.isContend = isContend;
    }

    /**
     * 总共邀请了多少人（只有工长能看到）
     */
    private String total_invitation;
    /**
     * 成功人数（只有工长能看到）
     */
    private String total_agree;
    /**
     * 还差多少人（只有工长能看到）
     */
    private String total_surplus;

    private List<Staff> staffs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCraft_id() {
        return craft_id;
    }

    public void setCraft_id(String craft_id) {
        this.craft_id = craft_id;
    }

    public String getStaff_account() {
        return staff_account;
    }

    public void setStaff_account(String staff_account) {
        this.staff_account = staff_account;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getStaff_description() {
        return staff_description;
    }

    public void setStaff_description(String staff_description) {
        this.staff_description = staff_description;
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

    public String getTotal_invitation() {
        return total_invitation;
    }

    public void setTotal_invitation(String total_invitation) {
        this.total_invitation = total_invitation;
    }

    public String getTotal_agree() {
        return total_agree;
    }

    public void setTotal_agree(String total_agree) {
        this.total_agree = total_agree;
    }

    public String getTotal_surplus() {
        return total_surplus;
    }

    public void setTotal_surplus(String total_surplus) {
        this.total_surplus = total_surplus;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.craft_id);
        dest.writeString(this.staff_account);
        dest.writeString(this.money);
        dest.writeString(this.start_time);
        dest.writeString(this.end_time);
        dest.writeString(this.status);
        dest.writeString(this.project_id);
        dest.writeString(this.staff_description);
        dest.writeString(this.account_days);
        dest.writeString(this.craft_name);
        dest.writeString(this.total_invitation);
        dest.writeString(this.total_agree);
        dest.writeString(this.total_surplus);
        dest.writeTypedList(staffs);
    }

    public Staff() {
    }

    protected Staff(Parcel in) {
        this.id = in.readString();
        this.craft_id = in.readString();
        this.staff_account = in.readString();
        this.money = in.readString();
        this.start_time = in.readString();
        this.end_time = in.readString();
        this.status = in.readString();
        this.project_id = in.readString();
        this.staff_description = in.readString();
        this.account_days = in.readString();
        this.craft_name = in.readString();
        this.total_invitation = in.readString();
        this.total_agree = in.readString();
        this.total_surplus = in.readString();
        this.staffs = in.createTypedArrayList(Staff.CREATOR);
    }

    public static final Creator<Staff> CREATOR = new Creator<Staff>() {
        public Staff createFromParcel(Parcel source) {
            return new Staff(source);
        }

        public Staff[] newArray(int size) {
            return new Staff[size];
        }
    };
}
