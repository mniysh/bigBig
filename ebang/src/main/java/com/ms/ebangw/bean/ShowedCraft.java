package com.ms.ebangw.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.ms.ebangw.utils.PinYinUtils;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-11-25 16:46
 */
public class ShowedCraft implements Comparable<ShowedCraft>, Parcelable{

    /**
     * craft_id : 32
     * staff_account :  1
     * craft_name : 土木工程师
     * count : 1
     */

    private String craft_id;
    private String staff_account;
    private String craft_name;
    private String count;

    public void setCraft_id(String craft_id) {
        this.craft_id = craft_id;
    }

    public void setStaff_account(String staff_account) {
        this.staff_account = staff_account;
    }

    public void setCraft_name(String craft_name) {
        this.craft_name = craft_name;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCraft_id() {
        return craft_id;
    }

    public String getStaff_account() {
        return staff_account;
    }

    public String getCraft_name() {
        return craft_name;
    }

    public String getCount() {
        return count;
    }
    public String getPinyin() {
        return PinYinUtils.getPinyin(craft_name);
    }

    @Override
    public int compareTo(ShowedCraft another) {
        return PinYinUtils.getPinyin(craft_name).compareTo(PinYinUtils.getPinyin(another
            .getCraft_name()));
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.craft_id);
        dest.writeString(this.staff_account);
        dest.writeString(this.craft_name);
        dest.writeString(this.count);
    }

    public ShowedCraft() {
    }

    protected ShowedCraft(Parcel in) {
        this.craft_id = in.readString();
        this.staff_account = in.readString();
        this.craft_name = in.readString();
        this.count = in.readString();
    }

    public static final Creator<ShowedCraft> CREATOR = new Creator<ShowedCraft>() {
        public ShowedCraft createFromParcel(Parcel source) {
            return new ShowedCraft(source);
        }

        public ShowedCraft[] newArray(int size) {
            return new ShowedCraft[size];
        }
    };
}
