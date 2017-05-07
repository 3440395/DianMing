package com.zyr.teacher;

import android.content.Context;

import com.google.gson.Gson;
import com.zyr.entity.BaseEntity;
import com.zyr.entity.Course;
import com.zyr.entity.Student;
import com.zyr.teacher.db.Dao;

import java.util.List;
import java.util.Map;

/**
 * 服务端接收到消息后希望在这里做处理
 * Created by xuekai on 2017/5/2.
 */

public class Helper {
    private Context context;
    private final Dao dao;
    private final Gson gson;

    public Helper(Context context) {
        this.context = context;
        dao = new Dao(context);
        gson = new Gson();
    }


    public String handOut(String action, Map<String, String> parse) {
        String responseJson = "";
        switch (action) {
            case "register":
                String studentid = parse.get("studentid");
                String password = parse.get("studentid");
                responseJson = register(studentid, password);
                break;
            case "login":
                String studentid1 = parse.get("studentid");
                String password1 = parse.get("password");
                responseJson = login(studentid1, password1);
                break;
            case "updateStudentInfo":
                String studentid2 = parse.get("studentid");
                String name2 = parse.get("name");
                String password2 = parse.get("password");
                String sex2 = parse.get("sex");
                responseJson = updateStudentInfo(studentid2, name2, password2, sex2);
                break;
            case "queryUncheckedCourse":
                String studentid3 = parse.get("studentid");
                responseJson = queryUncheckedCourse(studentid3);
                break;
            case "chooseCourseByStudent":
                String studentid4 = parse.get("studentid");
                String courseids4 = parse.get("courseids");
                responseJson = chooseCourseByStudent(studentid4, courseids4);
                break;
            case "queryCourseByStudentId":
                String studentid5 = parse.get("studentid");
                responseJson = queryCourseByStudentId(studentid5);
                break;
        }
        return responseJson;
    }

    /**
     * 通过学号，查询所有课程
     * @param studentid5
     * @return
     */
    private String queryCourseByStudentId(String studentid5) {
        BaseEntity<List<Course>> entity = new BaseEntity();
        List<Course> courses = null;
        courses=dao.queryCheckedCourseByStudet(studentid5);
        entity.setResultCode(1);
        entity.setData(courses);
        return gson.toJson(entity);    }

    /**
     * 学生选课
     *
     * @param studentid4
     * @param courseids4
     * @return
     */
    private String chooseCourseByStudent(String studentid4, String courseids4) {
        BaseEntity entity = new BaseEntity();

        boolean result = true;
        String[] split = courseids4.split(",");
        for (String s : split) {
            boolean b = dao.chooseCourseByStudent(studentid4, Integer.valueOf(s));
            if (!b) {
                result = b;
            }
        }
        entity.setResultCode(result ? 1 : 0);
        return gson.toJson(entity);
    }

    /**
     * 注册学生
     *
     * @param studentid
     * @param password
     * @return
     */
    public String register(String studentid, String password) {
        BaseEntity<Student> entity = new BaseEntity<>();
        boolean b = dao.registerStudent(studentid, password);
        entity.setResultCode(b ? 1 : 0);
        Student student = new Student();
        student.setName("薛凯");
        student.setPassword("xuekai");
        student.setSex("男");
        entity.setData(student);
        return gson.toJson(entity);
    }

    /**
     * 学生登录
     *
     * @param studentid1
     * @param password1
     * @return
     */
    public String login(String studentid1, String password1) {
        BaseEntity<Student> entity = new BaseEntity<>();
        Student student = dao.loginStudent(studentid1, password1);
        if (student != null) {
            entity.setResultCode(1);
            entity.setData(student);
        } else {
            entity.setResultCode(0);
        }
        return gson.toJson(entity);
    }

    /**
     * 更新学生信息
     *
     * @param studentid2
     * @param name2
     * @param password2
     * @param sex2
     * @return
     */
    public String updateStudentInfo(String studentid2, String name2, String password2, String sex2) {
        BaseEntity<Student> entity = new BaseEntity<>();
        boolean b = dao.updateStudentInfo(studentid2, name2, password2, sex2);
        entity.setResultCode(b ? 1 : 0);
        return gson.toJson(entity);
    }

    /**
     * 查询没有选的课程
     *
     * @param studentid3
     * @return
     */
    public String queryUncheckedCourse(String studentid3) {
        BaseEntity<List<Course>> entity = new BaseEntity<>();
        List<Course> courses = dao.queryUnCheckedCourseByStudet(studentid3);
        if (courses != null) {
            entity.setResultCode(1);
            entity.setData(courses);
        } else {
            entity.setResultCode(0);
        }
        return gson.toJson(entity);
    }
}
