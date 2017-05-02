package com.zyr.teacher.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import static android.R.attr.id;
import static android.R.attr.name;
import static android.R.attr.password;


public class Dao {

    private DBOpenHelper dbHelper;
    private SQLiteDatabase writableDatabase;

    public Dao(Context context) {
        dbHelper = new DBOpenHelper(context);
    }

    /**
     * 注册老师帐号
     *
     * @param name
     * @param password
     * @return 是否成功
     */
    public boolean registerTeacher(String name, String password) {
        writableDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("password", password);
        long id = writableDatabase.insert("tb_teacher", null, contentValues);
        return id != -1;
    }

    /**
     * 修改老师密码
     *
     * @param name
     * @param password
     * @return 是否成功
     */
    public boolean updateTeacherPsd(String name, String password) {
        return updateTeacherInfo(name, password, null, null);
    }

    /**
     * 修改学生密码
     *
     * @param name
     * @param password
     * @return 是否成功
     */
    public boolean updateStudentPsd(String name, String password) {
        return updateStudentInfo(name, password, null, null);
    }

    /**
     * 修改老师信息
     *
     * @param name
     * @param password
     * @return 是否成功
     */
    public boolean updateTeacherInfo(String name, String password, String sex, String phone) {
        writableDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        if (password != null) {
            contentValues.put("password", password);
        }
        if (sex != null) {
            contentValues.put("sex", sex);
        }
        if (phone != null) {
            contentValues.put("phone", phone);
        }

        long id = writableDatabase.update("tb_teacher", contentValues, "name=?", new String[]{name});
        return id != 0;
    }


    /**
     * 修改学生信息
     *
     * @param name
     * @param password
     * @return 是否成功
     */
    public boolean updateStudentInfo(String name, String password, String sex, String studentid) {
        writableDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        if (password != null) {
            contentValues.put("password", password);
        }
        if (sex != null) {
            contentValues.put("sex", sex);
        }
        if (studentid != null) {
            contentValues.put("studentid", studentid);
        }
        long id = writableDatabase.update("tb_student", contentValues, "name=?", new String[]{name});
        return id != 0;
    }

    /**
     * 注册学生帐号
     *
     * @param name
     * @param password
     * @return 是否成功
     */
    public boolean registerStudent(String name, String password) {
        writableDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("password", password);
        long id = writableDatabase.insert("tb_student", null, contentValues);
        return id != -1;
    }

    /**
     * 创建一门课程（课程名和老师组成了一门课程）
     *
     * @return
     */
    public boolean createCourse(int teacherId, String name) {
        writableDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("teacherId", teacherId);
        contentValues.put("name", name);
        long id = writableDatabase.insert("tb_course", null, contentValues);
        return id != -1;
    }

    /**
     * 为课程设置班长（课程名和老师组成了一门课程）
     *
     * @param teacherId
     * @param name
     * @param presidentid 班长对应的studentid
     * @return
     */
    public boolean setPresident(int teacherId, String name, int presidentid) {
        writableDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("presidentid", presidentid);
        long id = writableDatabase.update("tb_course", contentValues, "teacherid=? and name=?", new String[]{teacherId + "", name});
        return id != 0;
    }
}
