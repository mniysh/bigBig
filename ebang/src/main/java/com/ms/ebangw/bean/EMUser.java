package com.ms.ebangw.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * 聊天时的用户的基本信息
 * User: WangKai(123940232@qq.com)
 * 2015-12-09 09:50
 */
@DatabaseTable(tableName = "em_user")
public class EMUser implements Parcelable, Serializable{
    /**
     * 环信的id
     */
    @DatabaseField(id = true, unique = true, columnName = "userId", canBeNull = false)
    private String userId;
    /**
     * 聊天类型：单聊 群聊 {@link com.easemob.easeui.EaseConstant}
     */
    @DatabaseField(columnName = "type")
    private int type;
    /**
     * 昵称
     */
    @DatabaseField(columnName = "real_name")
    private String real_name;
    /**
     *头像
     */
    @DatabaseField(columnName = "head_image")
    private String head_image;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getHead_image() {
        return head_image;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeInt(this.type);
        dest.writeString(this.real_name);
        dest.writeString(this.head_image);
    }

    public EMUser() {
    }

    protected EMUser(Parcel in) {
        this.userId = in.readString();
        this.type = in.readInt();
        this.real_name = in.readString();
        this.head_image = in.readString();
    }

    public static final Creator<EMUser> CREATOR = new Creator<EMUser>() {
        public EMUser createFromParcel(Parcel source) {
            return new EMUser(source);
        }

        public EMUser[] newArray(int size) {
            return new EMUser[size];
        }
    };
}
