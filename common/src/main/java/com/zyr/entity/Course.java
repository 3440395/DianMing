package com.zyr.entity;

/**
 * Created by xuekai on 2017/5/3.
 */

public class Course {
    private int id;
    private String name;
    private int teacherid;
    private int presidentid;

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
