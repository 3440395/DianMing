package com.zyr.teacher.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zyr.entity.Course;
import com.zyr.entity.Student;
import com.zyr.entity.Teacher;

import java.util.ArrayList;
import java.util.List;


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
     * 为课程设置班长（课程名和老师组成了一门课程）
     *
     * @param courseid
     * @param presidentid 班长对应的studentid
     * @return
     */
    public boolean setPresident(int courseid, int presidentid) {
        writableDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("presidentid", presidentid);
        long id = writableDatabase.update("tb_course", contentValues, "_id=? ", new String[]{courseid + ""});
        return id != 0;
    }

    /**
     * 老师登录
     *
     * @return
     */
    public boolean loginTeacher(String name, String password) {
        readableDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query("tb_teacher", new String[]{}, "name=? and password=?", new String[]{name, password}, null, null, null);
        boolean result = cursor.moveToNext();
        cursor.close();
        return result;
    }

    /**
     * 学生登录
     *
     * @return
     */
    public Student loginStudent(String studentid, String password) {
        Student student;
        readableDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query("tb_student", new String[]{"name", "sex", "studentid"}, "studentid=? and password=?", new String[]{studentid, password}, null, null, null);
        if (cursor.moveToNext()) {
            student = new Student();
            student.setSex(getStringFromCursor(cursor, "sex"));
            student.setStudentid(getStringFromCursor(cursor, "studentid"));
            student.setName(getStringFromCursor(cursor, "name"));
        } else {
            student = null;
        }
        cursor.close();
        return student;
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
     * 设置课程时间（一个老师的一门课程可能由多个上课时间，参数就是这两个）
     *
     * @param courseid 课程的主键_id
     * @param time     课程的时间一天4节课，周一到周五 0开始
     * @return
     */
    public boolean setCourseTime(int courseid, String time) {
        writableDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("courseid", courseid);
        contentValues.put("time", time);
        String s = queryCourseTime(courseid);
        if (s.equals("")) {
            long id = writableDatabase.insert("tb_course_time", null, contentValues);
            return id != -1;
        } else {
            return updateCourseTime(courseid, time);
        }
    }

    /**
     * 设置课程时间（一个老师的一门课程可能由多个上课时间，参数就是这两个）
     *
     * @param courseid 课程的主键_id
     * @param time     课程的时间一天4节课，周一到周5  0开始
     * @return
     */
    private boolean updateCourseTime(int courseid, String time) {
        writableDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("courseid", courseid);
        contentValues.put("time", time);
        long id = writableDatabase.update("tb_course_time", contentValues, "courseid=?", new String[]{courseid + ""});
        return id != 0;
    }


    /**
     * 查询课程时间（一个老师的一门课程可能由多个上课时间）
     *
     * @param courseid 课程的主键_id
     * @return
     */
    public String queryCourseTime(int courseid) {
        readableDatabase = dbHelper.getReadableDatabase();
        String time = "";
        ContentValues contentValues = new ContentValues();
        contentValues.put("courseid", courseid);
        Cursor cursor = readableDatabase.query("tb_course_time", null, "courseid=?", new String[]{courseid + ""}, null, null, null, null);
        while (cursor.moveToNext()) {
            time = getStringFromCursor(cursor, "time");
        }
        return time;
    }

    /**
     * 学生添加课程
     *
     * @param studentid
     * @param courseid
     * @return
     */
    public boolean chooseCourseByStudent(String studentid, int courseid) {
        writableDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("studentid", studentid);
        contentValues.put("courseid", courseid);
        long id = writableDatabase.insert("tb_course_student", null, contentValues);
        return id != -1;
    }


    /**
     * 签到
     *
     * @param studentid    学生id
     * @param time         哪节课
     * @return
     */
    public boolean checkIn(String studentid, int time, String date,int courseId) {
        writableDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("studentid", studentid);
        contentValues.put("time", time);
        contentValues.put("date", date);
        contentValues.put("courseid", courseId);
        long id = writableDatabase.insert("tb_check", null, contentValues);
        return id != -1;
    }



    /**
     * 查询签到信息
     *
     * @param studentid    学生id
     * @param time         年月日
     * @param date 课程id（老师和课程名组成）
     * @return
     */
    public boolean querycheckIn(String studentid, int time, String date,int courseId) {
        writableDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("studentid", studentid);
        contentValues.put("time", time);
        contentValues.put("date", date);
        contentValues.put("courseid", courseId);
        Cursor cursor = readableDatabase.query("tb_check", null, "studentid=? and time=? and date=? and courseid=?", new String[]{studentid,time+"",date,courseId+""}, null, null, null);
        if (cursor.moveToNext()) {
            return true;
        }
        return false;
    }



    /**
     * 请假
     *
     * @param studentid    学生id
     * @param time         年月日
     * @param coursetimeid 课程id（老师和课程名组成）
     * @return
     */
    public boolean leave(String studentid, String time, int coursetimeid) {
        writableDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("studentid", studentid);
        contentValues.put("time", time);
        contentValues.put("coursetimeid", coursetimeid);
        long id = writableDatabase.insert("tb_leave", null, contentValues);
        return id != -1;
    }

    /**
     * 通过课程id查询它所对应的学生
     *
     * @param courseid
     * @return
     */
    public List<Student> queryStudentsByCourseId(int... courseid) {
        List<Student> students = new ArrayList<>();
        Student student = null;
        readableDatabase = dbHelper.getReadableDatabase();
        String[] selectionArgs = new String[courseid.length];
        for (int i = 0; i < selectionArgs.length; i++) {
            selectionArgs[i] = String.valueOf(courseid[i]);
        }
        Cursor cursor = readableDatabase.query("tb_course_student", new String[]{"studentId"}, "courseid=?", selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            String studentId = getStringFromCursor(cursor, "studentid");
            student = queryStudentByStudentId(studentId);
            students.add(student);
        }
        cursor.close();
        return students;
    }

    /**
     * 通过学号查询学生的详细信息
     *
     * @param studentId
     * @return
     */
    public Student queryStudentByStudentId(String studentId) {
        if (studentId==null) {
            return null;
        }
        Student student = null;
        readableDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query("tb_student", new String[]{"name", "sex"}, "studentId=?", new String[]{studentId}, null, null, null);
        if (cursor.moveToNext()) {
            student = new Student();
            student.setName(getStringFromCursor(cursor, "name"));
            student.setSex(getStringFromCursor(cursor, "sex"));
            student.setStudentid(studentId);
        }
        cursor.close();
        return student;
    }

    /**
     * 查询老师根据id
     *
     * @param teacherId -1表示返回所有
     * @return
     */
    public List<Teacher> queryTeacher(int teacherId) {
        Cursor cursor;
        List<Teacher> teachers = new ArrayList<>();
        Teacher teacher = null;
        readableDatabase = dbHelper.getReadableDatabase();
        if (teacherId==-1) {
            cursor = readableDatabase.query("tb_teacher", new String[]{"_id", "name", "sex", "phone"}, null, null, null, null, null);
        }else{
             cursor = readableDatabase.query("tb_teacher", new String[]{"_id", "name", "sex", "phone"}, "_id=?", new String[]{teacherId+""}, null, null, null);
        }
        while (cursor.moveToNext()) {
            teacher = new Teacher();
            teacher.setId(getIntFromCursor(cursor, "_id"));
            teacher.setName(getStringFromCursor(cursor, "name"));
            teacher.setPhone(getStringFromCursor(cursor, "phone"));
            teacher.setSex(getStringFromCursor(cursor, "sex"));
            teachers.add(teacher);
        }
        cursor.close();
        return teachers;
    }

    /**
     * 通过teacher查询所有的课程
     *
     * @param teacherid
     * @return
     */
    public List<Course> queryCourseByTeacher(int teacherid) {
        List<Course> courses = new ArrayList<>();
        Course course = null;
        readableDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query("tb_course", new String[]{"_id", "name", "presidentid"}, "teacherid=?", new String[]{teacherid + ""}, null, null, null);
        while (cursor.moveToNext()) {
            course = new Course();
            int id = getIntFromCursor(cursor, "_id");
            String name = getStringFromCursor(cursor, "name");
            String presidentid = getStringFromCursor(cursor, "presidentid");
            course.setId(id);
            course.setName(name);
            course.setPresidentid(presidentid);
            courses.add(course);
        }
        cursor.close();
        return courses;
    }

    /**
     * 通过学号查询未选中的课程
     *
     * @param studentid
     * @return
     */
    public List<Course> queryUnCheckedCourseByStudet(String studentid) {
        List<Course> oldCourses = queryAllCourse();
        List<Course> newCourses = new ArrayList<>();
        newCourses.addAll(oldCourses);
        List<Integer> courseIds = new ArrayList<>();
        Course course = null;
        readableDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query("tb_course_student", null, "studentid=?", new String[]{studentid + ""}, null, null, null);
        while (cursor.moveToNext()) {
            int courseid = getIntFromCursor(cursor, "courseid");
            courseIds.add(courseid);
        }

        for (Course oldCourse : oldCourses) {
            int id = oldCourse.getId();
            boolean contains = courseIds.contains(id);
            if (contains) {
                //oldCourse已经被选中
                newCourses.remove(oldCourse);
            }
        }
        cursor.close();
        return newCourses;
    }

    /**
     * 通过学号查询已经选中的课程
     *
     * @param studentid
     * @return
     */
    public List<Course> queryCheckedCourseByStudet(String studentid) {
        List<Course> oldCourses = queryAllCourse();
        List<Course> newCourses = new ArrayList<>();
        List<Integer> courseIds = new ArrayList<>();
        Course course = null;
        readableDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query("tb_course_student", null, "studentid=?", new String[]{studentid + ""}, null, null, null);
        while (cursor.moveToNext()) {
            int courseid = getIntFromCursor(cursor, "courseid");
            courseIds.add(courseid);
        }

        for (Course oldCourse : oldCourses) {
            int id = oldCourse.getId();
            boolean contains = courseIds.contains(id);
            if (contains) {
                //oldCourse已经被选中
                newCourses.add(oldCourse);
            }
        }
        cursor.close();
        return newCourses;
    }

    /**
     * 查询所有课程
     *
     * @return
     */
    public List<Course> queryAllCourse() {
        List<Course> courses = new ArrayList<>();
        Course course = null;
        readableDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query("tb_course", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            course = new Course();
            int id = getIntFromCursor(cursor, "_id");
            String name = getStringFromCursor(cursor, "name");
            String presidentid = getStringFromCursor(cursor, "presidentid");
            Student president = queryStudentByStudentId(presidentid);
            int teacherid = getIntFromCursor(cursor, "teacherid");
            List<Teacher> teachers = queryTeacher(teacherid);
            course.setId(id);
            String s = queryCourseTime(id);
            int[] ints;
            if (s.length() > 0) {
                String[] split = s.split(",");
                ints = new int[split.length];
                for (int i = 0; i < split.length; i++) {
                    ints[i] = Integer.valueOf(split[i]);
                }
                course.setTimes(ints);
            }
            course.setName(name);
            course.setPresidentid(presidentid);
            course.setTeacherid(teacherid);
            course.setPresident(president);
            course.setTeacher(teachers.get(0));
            courses.add(course);
        }
        cursor.close();
        return courses;
    }


    private String getStringFromCursor(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    private int getIntFromCursor(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

}
