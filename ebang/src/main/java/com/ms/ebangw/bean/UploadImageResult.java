package com.ms.ebangw.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 上传图片的返回结果
 * User: WangKai(123940232@qq.com)
 * 2015-09-22 16:59
 */
public class UploadImageResult implements Parcelable {
    private String id;
    private String name;
    private String url;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }




    @Override
    public String toString() {
        return "UploadImageResult{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", url='" + url + '\'' +
            '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.url);
    }

    public UploadImageResult() {
    }

    protected UploadImageResult(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<UploadImageResult> CREATOR = new Parcelable.Creator<UploadImageResult>() {
        public UploadImageResult createFromParcel(Parcel source) {
            return new UploadImageResult(source);
        }

        public UploadImageResult[] newArray(int size) {
            return new UploadImageResult[size];
        }
    };
}
