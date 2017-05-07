package com.zyr.ui.activity;

import android.os.Bundle;

import com.zyr.base.BaseActivity;
import com.zyr.entity.Course;
import com.zyr.entity.Student;

/**
 * Created by xuekai on 2017/5/7.
 */

public class CheckInfoActivity extends BaseActivity {
    private boolean isAdmin = false;//是否拥有管理权限

    @Override
    protected void setLayout() {

    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void setupViews(Bundle bundle) {
        //        学生过来
//        bundle.putParcelable("course",course);
//        bundle.putParcelable("student",student);
//        bundle.putInt("courseTime",courseTime);
//        bundle.putString("courseDate",o);

        //        老师过来
//        bundle.putParcelable("course",course);
//        bundle.putInt("courseTime",courseTime);
//        bundle.putString("courseDate",o);
        Student student = (Student) bundle.get("student");
        Course course = (Course) bundle.get("course");
        int courseTime = (Integer) bundle.get("courseTime");
        String courseDate = (String) bundle.get("courseDate");
        if (student == null) {
            //我是老师
            isAdmin = true;
        } else {
            if (student.getStudentid().equals(course.getPresidentid())) {
                isAdmin = true;
            } else {
                isAdmin = false;
            }
        }

        toast("woshi管理" + isAdmin);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void fetchData() {

    }
}
