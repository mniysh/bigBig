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
    @DatabaseField(columnName = "nickName")
    private String nickName;
    /**
     *头像
     */
    @DatabaseField(columnName = "avatarUrl")
    private String avatarUrl;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public EMUser() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeInt(this.type);
        dest.writeString(this.nickName);
        dest.writeString(this.avatarUrl);
    }

    protected EMUser(Parcel in) {
        this.userId = in.readString();
        this.type = in.readInt();
        this.nickName = in.readString();
        this.avatarUrl = in.readString();
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
