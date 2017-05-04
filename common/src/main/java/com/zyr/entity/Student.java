package com.zyr.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xuekai on 2017/5/3.
 */

public class Student implements Parcelable {
    private String name;
    private String sex;
    private int studentid;
    private String password;

    public Student() {

    }

    protected Student(Parcel in) {
        name = in.readString();
        sex = in.readString();
        studentid = in.readInt();
        password = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(sex);
        dest.writeInt(studentid);
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
    public String toString() {
        return "Student{" +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", studentid=" + studentid +
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

    public int getStudentid() {
        return studentid;
    }

    public void setStudentid(int studentid) {
        this.studentid = studentid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
