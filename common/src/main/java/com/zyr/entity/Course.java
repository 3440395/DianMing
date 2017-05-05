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
    private Student student;
    private int presidentid;

    public Course() {

    }


    protected Course(Parcel in) {
        id = in.readInt();
        name = in.readString();
        teacherid = in.readInt();
        student = in.readParcelable(Student.class.getClassLoader());
        presidentid = in.readInt();
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(teacherid);
        dest.writeParcelable(student, flags);
        dest.writeInt(presidentid);
    }
}
