package com.zyr.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by xuekai on 2017/5/3.
 */

public class Student implements Parcelable,Comparable<Student>{
    private String name;
    private String sex;
    private String studentid;
    private String password;


    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", studentid='" + studentid + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Creator<Student> getCREATOR() {
        return CREATOR;
    }

    protected Student(Parcel in) {
        name = in.readString();
        sex = in.readString();
        studentid = in.readString();
        password = in.readString();
    }

    public Student() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(sex);
        dest.writeString(studentid);
        dest.writeString(password);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int compareTo(@NonNull Student o) {
        if(studentid.equals(o.studentid)){
            return 0;
        }else{
            return o.studentid.compareTo(o.studentid);
        }
    }
}
