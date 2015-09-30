package com.ms.ebangw.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.List;

/**
 * 工种
 * Created by admin on 2015/9/24.
 */
public class WorkType implements Parcelable {

    private String id;
    private String fid;
    private String name;

    private List<WorkType> workTypes;
    private Staff staff;

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

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public List<WorkType> getWorkTypes() {
        return workTypes;
    }

    public void setWorkTypes(List<WorkType> workTypes) {
        this.workTypes = workTypes;
    }

    @Override
    public boolean equals(Object o) {
        WorkType temp;
        if (o instanceof WorkType) {
            temp = (WorkType) o;
            return TextUtils.equals(id, temp.getId());
        }else {
            return false;
        }
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.fid);
        dest.writeString(this.name);
        dest.writeTypedList(workTypes);
        dest.writeParcelable(this.staff, flags);
    }

    public WorkType() {
    }

    protected WorkType(Parcel in) {
        this.id = in.readString();
        this.fid = in.readString();
        this.name = in.readString();
        this.workTypes = in.createTypedArrayList(WorkType.CREATOR);
        this.staff = in.readParcelable(Staff.class.getClassLoader());
    }

    public static final Creator<WorkType> CREATOR = new Creator<WorkType>() {
        public WorkType createFromParcel(Parcel source) {
            return new WorkType(source);
        }

        public WorkType[] newArray(int size) {
            return new WorkType[size];
        }
    };
}
