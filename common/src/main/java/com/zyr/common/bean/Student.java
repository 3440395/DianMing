package com.zyr.common.bean;

/**
 * Created by xuekai on 2017/5/3.
 */

public class Student {
    private String name;
    private String sex;
    private int studentid;
    private String password;

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
