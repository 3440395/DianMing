package com.zyr.teacher.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class Dao {

    private DBOpenHelper dbHelper;
    private SQLiteDatabase writableDatabase;
    private SQLiteDatabase readableDatabase;

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
    public boolean updateStudentInfo(String studentid, String name, String password, String sex) {
        writableDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        if (password != null) {
            contentValues.put("password", password);
        }
        if (sex != null) {
            contentValues.put("sex", sex);
        }
        if (name != null) {
            contentValues.put("name", name);
        }
        long id = writableDatabase.update("tb_student", contentValues, "studentid=?", new String[]{studentid});
        return id != 0;
    }

    /**
     * 注册学生帐号
     *
     * @param studentid
     * @param password
     * @return 是否成功
     */
    public boolean registerStudent(String studentid, String password) {
        writableDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("studentid", studentid);
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

    /**
     * 老师登录
     *
     * @return
     */
    public boolean loginTeacher(String name, String password) {
        readableDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query("tb_teacher", new String[]{"name", "password"}, "name=? and password=?", new String[]{name, password}, null, null, null);
        return cursor.moveToNext();
    }

    /**
     * 学生登录
     *
     * @return
     */
    public boolean loginStudent(String studentid, String password) {
        readableDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query("tb_student", new String[]{"studentid", "password"}, "studentid=? and password=?", new String[]{studentid, password}, null, null, null);
        return cursor.moveToNext();
    }


    /**
     * 通过老师去创建一个唯一的课程
     *
     * @param teacherid
     * @param name
     * @return
     */
    public boolean createCourseByTeacher(int teacherid, String name) {
        writableDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("teacherid", teacherid);
        contentValues.put("name", name);
        long id = writableDatabase.insert("tb_course", null, contentValues);
        return id != -1;
    }


    /**
     * 设置课程时间（一个老师的一门课程可能由多个上课时间，参数就是这两个）
     *
     * @param courseid 课程的主键_id
     * @param time     课程的时间一天5节课，周一到周日
     * @return
     */
    public boolean setCourseTime(int courseid, int time) {
        writableDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("courseid", courseid);
        contentValues.put("time", time);
        long id = writableDatabase.insert("tb_course_time", null, contentValues);
        return id != -1;
    }

    /**
     * 学生添加课程
     * @param studentid
     * @param courseid
     * @return
     */
    public boolean chooseCourseByStudent(int studentid, int courseid) {
        writableDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("studentid", studentid);
        contentValues.put("courseid", courseid);
        long id = writableDatabase.insert("tb_course_student", null, contentValues);
        return id != -1;
    }


    public boolean checkIn(int studentid,int time,){

    }
}
