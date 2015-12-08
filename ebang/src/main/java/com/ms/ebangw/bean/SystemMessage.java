package com.ms.ebangw.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 系统消息
 * User: WangKai(123940232@qq.com)
 * 2015-12-07 16:33
 */
public class SystemMessage implements Parcelable{
    /**
     * id : 编号
     * title : 测试
     * content : 消息
     * created_at : 时间
     * is_read : 0  /是否已读  0未读  1已读
     */

    private String id;
    private String title;
    private String content;
    private String created_at;
    /**
     * /是否已读  0未读  1已读
     */
    private String is_read;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getIs_read() {
        return is_read;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SystemMessage message = (SystemMessage) o;

        return id.equals(message.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.created_at);
        dest.writeString(this.is_read);
    }

    public SystemMessage() {
    }

    protected SystemMessage(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        this.created_at = in.readString();
        this.is_read = in.readString();
    }

    public static final Creator<SystemMessage> CREATOR = new Creator<SystemMessage>() {
        public SystemMessage createFromParcel(Parcel source) {
            return new SystemMessage(source);
        }

        public SystemMessage[] newArray(int size) {
            return new SystemMessage[size];
        }
    };
}
