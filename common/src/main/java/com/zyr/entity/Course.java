package com.zyr.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xuekai on 2017/5/3.
 */

public class Course implements Parcelable {
    private int id;
    private String name;
    private int teacherid;
    private int presidentid;

    public Course() {

    }

    protected Course(Parcel in) {
        id = in.readInt();
        name = in.readString();
        teacherid = in.readInt();
        presidentid = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(teacherid);
        dest.writeInt(presidentid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teacherid=" + teacherid +
                ", presidentid=" + presidentid +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(int teacherid) {
        this.teacherid = teacherid;
    }

    public int getPresidentid() {
        return presidentid;
    }

    public void setPresidentid(int presidentid) {
        this.presidentid = presidentid;
    }
}
