package com.ms.ebangw.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 开发商发布工程时的工程信息
 * Created by admin on 2015/10/20.
 */
public class ReleaseInfo implements Parcelable{
    private String id;
    private String title;
    private String description;
    private String link_man;
    private String link_phone;
    private String province;
    private String city;
    private String area_other;
    private float point_longitude;
    private float point_dimention;
    private String image_ary;
    private String start_time;
    private String end_time;
    private String project_money;
    private String staff;

    public ReleaseInfo() {
        
    }

    protected ReleaseInfo(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        link_man = in.readString();
        link_phone = in.readString();
        province = in.readString();
        city = in.readString();
        area_other = in.readString();
        point_longitude = in.readFloat();
        point_dimention = in.readFloat();
        image_ary = in.readString();
        start_time = in.readString();
        end_time = in.readString();
        project_money = in.readString();
        staff = in.readString();
    }

    public static final Creator<ReleaseInfo> CREATE = new Creator<ReleaseInfo>() {
        @Override
        public ReleaseInfo createFromParcel(Parcel in) {
            return new ReleaseInfo(in);
        }

        @Override
        public ReleaseInfo[] newArray(int size) {
            return new ReleaseInfo[size];
        }
    };

    @Override
    public String toString() {
        return "ReleaseInfo{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link_man='" + link_man + '\'' +
                ", link_phone='" + link_phone + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area_other='" + area_other + '\'' +
                ", point_longitude=" + point_longitude +
                ", point_dimention=" + point_dimention +
                ", image_ary='" + image_ary + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", project_money='" + project_money + '\'' +
                ", staff=" + staff +
                '}';
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink_man() {
        return link_man;
    }

    public void setLink_man(String link_man) {
        this.link_man = link_man;
    }

    public String getLink_phone() {
        return link_phone;
    }

    public void setLink_phone(String link_phone) {
        this.link_phone = link_phone;
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

    public float getPoint_longitude() {
        return point_longitude;
    }

    public void setPoint_longitude(float point_longitude) {
        this.point_longitude = point_longitude;
    }

    public float getPoint_dimention() {
        return point_dimention;
    }

    public void setPoint_dimention(float point_dimention) {
        this.point_dimention = point_dimention;
    }

    public String getImage_ary() {
        return image_ary;
    }

    public void setImage_ary(String image_ary) {
        this.image_ary = image_ary;
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

    public String getProject_money() {
        return project_money;
    }

    public void setProject_money(String project_money) {
        this.project_money = project_money;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.link_man);
        dest.writeString(this.link_phone);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeFloat(this.point_dimention);
        dest.writeFloat(this.point_longitude);
        dest.writeString(this.image_ary);
        dest.writeString(this.start_time);
        dest.writeString(this.end_time);
        dest.writeString(this.project_money);
        dest.writeString(this.staff);
        dest.writeString(this.area_other);


    }
    public static Parcelable.Creator<ReleaseInfo> CREATOR = new Parcelable.Creator<ReleaseInfo>(){
        @Override
        public ReleaseInfo createFromParcel(Parcel source) {
            return new ReleaseInfo(source);
        }

        @Override
        public ReleaseInfo[] newArray(int size) {
            return new ReleaseInfo[size];
        }
    };


}
