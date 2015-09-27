package com.ms.ebangw.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.ArrayList;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.fid);
        dest.writeString(this.name);
        dest.writeList(this.workTypes);
    }

    public WorkType() {
    }

    protected WorkType(Parcel in) {
        this.id = in.readString();
        this.fid = in.readString();
        this.name = in.readString();
        this.workTypes = new ArrayList<WorkType>();
        in.readList(this.workTypes, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<WorkType> CREATOR = new Parcelable.Creator<WorkType>() {
        public WorkType createFromParcel(Parcel source) {
            return new WorkType(source);
        }

        public WorkType[] newArray(int size) {
            return new WorkType[size];
        }
    };
}
