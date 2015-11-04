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
    private String title;
    private String city;
    //private String area;
    private String area_other;
    //选择工种的数量
    private String account_staff;
    //工程总金额
    private String total_money;
    private String project_money;
    private String image_par;
    private String count;

    /**
     *  "developer_id": 577,

     "title": "测试1",

     "description": "描述",

     "images": "http://www.labour.com/uploads/images/1.jpg",

     "province": "北京",

     "city": "北京",

     "project_type": "company",

     "project_type_name": "开发商",

     "grab_num": 0,            //已经有多少人抢单

     "project_money": 0,     //   工程金额

     "distance": 256           //距离 单位（m）
     * @return
     */


    private String developer_id;
    private String description;
    private String images;
    private String project_type;
    private String project_type_name;
    private String grab_num;
    private String distance;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getImage_par() {
        return image_par;
    }

    public void setImage_par(String image_par) {
        this.image_par = image_par;
    }

    public ReleaseProject() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProject_money() {
        return project_money;
    }

    public void setProject_money(String project_money) {
        this.project_money = project_money;
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

    public String getDeveloper_id() {
        return developer_id;
    }

    public void setDeveloper_id(String developer_id) {
        this.developer_id = developer_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getProject_type() {
        return project_type;
    }

    public void setProject_type(String project_type) {
        this.project_type = project_type;
    }

    public String getProject_type_name() {
        return project_type_name;
    }

    public void setProject_type_name(String project_type_name) {
        this.project_type_name = project_type_name;
    }

    public String getGrab_num() {
        return grab_num;
    }

    public void setGrab_num(String grab_num) {
        this.grab_num = grab_num;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.no);
        dest.writeString(this.province);
        dest.writeString(this.title);
        dest.writeString(this.city);
        dest.writeString(this.area_other);
        dest.writeString(this.account_staff);
        dest.writeString(this.total_money);
        dest.writeString(this.project_money);
        dest.writeString(this.image_par);
        dest.writeString(this.count);
        dest.writeString(this.developer_id);
        dest.writeString(this.description);
        dest.writeString(this.images);
        dest.writeString(this.project_type);
        dest.writeString(this.project_type_name);
        dest.writeString(this.grab_num);
        dest.writeString(this.distance);
    }

    protected ReleaseProject(Parcel in) {
        this.id = in.readString();
        this.no = in.readString();
        this.province = in.readString();
        this.title = in.readString();
        this.city = in.readString();
        this.area_other = in.readString();
        this.account_staff = in.readString();
        this.total_money = in.readString();
        this.project_money = in.readString();
        this.image_par = in.readString();
        this.count = in.readString();
        this.developer_id = in.readString();
        this.description = in.readString();
        this.images = in.readString();
        this.project_type = in.readString();
        this.project_type_name = in.readString();
        this.grab_num = in.readString();
        this.distance = in.readString();
    }

    public static final Creator<ReleaseProject> CREATOR = new Creator<ReleaseProject>() {
        public ReleaseProject createFromParcel(Parcel source) {
            return new ReleaseProject(source);
        }

        public ReleaseProject[] newArray(int size) {
            return new ReleaseProject[size];
        }
    };
}
