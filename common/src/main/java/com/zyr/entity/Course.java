package com.zyr.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by xuekai on 2017/5/3.
 */

public class Course implements Parcelable {
    private int id;
    private String name;
    private int teacherid;
    private Student president;
    private String presidentid="";
    private int[] times={};
    private Teacher teacher;

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teacherid=" + teacherid +
                ", president=" + president +
                ", presidentid=" + presidentid +
                ", times=" + Arrays.toString(times) +
                ", teacher=" + teacher +
                '}';
    }

    public Course() {

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

    public Student getPresident() {
        return president;
    }

    public void setPresident(Student president) {
        this.president = president;
    }

    public String getPresidentid() {
        return presidentid;
    }

    public void setPresidentid(String presidentid) {
        this.presidentid = presidentid;
    }

    public int[] getTimes() {
        return times;
    }

    public void setTimes(int[] times) {
        this.times = times;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public static Creator<Course> getCREATOR() {
        return CREATOR;
    }

    protected Course(Parcel in) {
        id = in.readInt();
        name = in.readString();
        teacherid = in.readInt();
        president = in.readParcelable(Student.class.getClassLoader());
        presidentid = in.readString();
        times = in.createIntArray();
        teacher = in.readParcelable(Teacher.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(teacherid);
        dest.writeParcelable(president, flags);
        dest.writeString(presidentid);
        dest.writeIntArray(times);
        dest.writeParcelable(teacher, flags);
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
}
