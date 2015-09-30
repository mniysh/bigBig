package com.ms.ebangw.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 开发商发布页面的工种具体雇佣信息
 * User: WangKai(123940232@qq.com)
 * 2015-09-30 09:47
 */
public class Staff implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.craft_id);
        dest.writeString(this.staff_account);
        dest.writeString(this.money);
        dest.writeString(this.start_time);
        dest.writeString(this.end_time);
    }

    public Staff() {
    }

    protected Staff(Parcel in) {
        this.craft_id = in.readString();
        this.staff_account = in.readString();
        this.money = in.readString();
        this.start_time = in.readString();
        this.end_time = in.readString();
    }

    public static final Parcelable.Creator<Staff> CREATOR = new Parcelable.Creator<Staff>() {
        public Staff createFromParcel(Parcel source) {
            return new Staff(source);
        }

        public Staff[] newArray(int size) {
            return new Staff[size];
        }
    };
}
