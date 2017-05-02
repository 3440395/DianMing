package com.zyr.teacher.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBOpenHelper extends SQLiteOpenHelper {

    public DBOpenHelper(Context context) {
        super(context, "database.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tb_teacher(" +
                "_id integer primary key autoincrement," +
                "name text unique," +
                "password text," +
                "sex text," +
                "phone text)");

        db.execSQL("create table tb_student(" +
                "_id integer primary key autoincrement," +
                "name text," +
                "sex text," +
                "studentid text unique," +//学号
                "password text)");

        db.execSQL("create table tb_course(" +
                "_id integer primary key autoincrement," +
                "name text ," +
                "teacherid integer," +//这门课的代课老师是谁
                "presidentid integer," +
                "unique  (name,teacherid))");//设置名字和老师id唯一约束

        db.execSQL("create table tb_course_time(" +
                "_id integer primary key autoincrement," +
                "courseid integer," +
                "time integer," +
                "unique  (courseid,time))");//课程时间用数字表示，从周一到周日，每天5节课（上下各2，晚上1）

        db.execSQL("create table tb_course_student(" +
                "_id integer primary key autoincrement," +
                "courseid integer," +
                "studentid integer," +
                "unique  (courseid,studentid))");

        db.execSQL("create table tb_check(" +
                "_id integer primary key autoincrement," +
                "coursetimeid integer," +//签到是按照具体某天的某节课来说的，所以关联了coursetimeid
                "time text," +//年月日
                "studentid integer)");

        db.execSQL("create table tb_leave(" +
                "_id integer primary key autoincrement," +
                "coursetimeid text," +
                "time text," +//年月日
                "studentid integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
