package com.ms.ebangw.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * Created by admin on 2015/11/2.
 */
public class ReleaseProject implements Parcelable{
    private String id;
    private String no;
    private String province;
    private String city;
    //private String area;
    private String area_other;
    //选择工种的数量
    private String account_staff;
    //工程总金额
    private String total_money;
    private String project_money;
    private String image_par;

    public String getImage_par() {
        return image_par;
    }

    public void setImage_par(String image_par) {
        this.image_par = image_par;
    }

    public ReleaseProject() {

    }

    protected ReleaseProject(Parcel in) {
        id = in.readString();
        no = in.readString();
        province = in.readString();
        city = in.readString();
        area_other = in.readString();
        account_staff = in.readString();
        total_money = in.readString();
        project_money = in.readString();
        image_par = in.readString();
    }

    public static final Creator<ReleaseProject> CREATOR = new Creator<ReleaseProject>() {
        @Override
        public ReleaseProject createFromParcel(Parcel in) {
            return new ReleaseProject(in);
        }

        @Override
        public ReleaseProject[] newArray(int size) {
            return new ReleaseProject[size];
        }
    };

    public String getProject_money() {
        return project_money;
    }

    public void setProject_money(String project_money) {
        this.project_money = project_money;
    }

    @Override
    public String toString() {
        return "ReleaseProject{" +
                "id='" + id + '\'' +
                ", no='" + no + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city +
                 '\'' +
                ", area_other='" + area_other + '\'' +
                ", account_staff=" + account_staff +
                ", total_money='" + total_money + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
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

    public String getAccount_staff() {
        return account_staff;
    }

    public void setAccount_staff(String account_staff) {
        this.account_staff = account_staff;
    }

    public String getTotal_money() {
        return total_money;
    }

    public void setTotal_money(String total_money) {
        this.total_money = total_money;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(no);
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(area_other);
        dest.writeString(account_staff);
        dest.writeString(total_money);
        dest.writeString(project_money);
    }
//    public static Parcelable.Creator<ReleaseProject> CREATOR = new Parcelable.Creator<ReleaseProject>(){
//        @Override
//        public ReleaseProject createFromParcel(Parcel source) {
//            return new ReleaseProject(source);
//        }
//
//        @Override
//        public ReleaseProject[] newArray(int size) {
//            return new ReleaseProject[size];
//        }
//    };
}
