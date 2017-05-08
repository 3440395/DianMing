package com.zyr.teacher.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;

import com.zyr.entity.CheckInfo;
import com.zyr.entity.Student;
import com.zyr.teacher.db.Dao;
import com.zyr.ui.activity.CheckInfoActivity;
import com.zyr.ui.adapter.BaseListRefreshAdapter;
import com.zyr.ui.fragment.RefreshBaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuekai on 2017/5/8.
 */

public class CheckInfoActivityTeacher extends CheckInfoActivity {
    @Override
    public void requestAllStudentByCourseId(SwipeRefreshLayout swipeRefreshLayout, BaseListRefreshAdapter<CheckInfo> baseListRefreshAdapter) {
        Dao dao=new Dao(mContext);
        CheckInfo checkInfo;
        ArrayList<CheckInfo> checkInfos= new ArrayList<>();
        List<Student> students = dao.queryStudentsByCourseId(course.getId());
        for (Student student : students) {
            boolean b = dao.querycheckIn(student.getStudentid(), courseTime, courseDate,course.getId());
            checkInfo = new CheckInfo();
            checkInfo.setCheck(b);
            checkInfo.setStudent(student);
            checkInfos.add(checkInfo);
        }
        baseListRefreshAdapter.setData(checkInfos);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void checkIn(RefreshBaseFragment<CheckInfo> fragment) {
        toast("签到");
    }

    @Override
    public void requestSelf(SwipeRefreshLayout swipeRefreshLayout, BaseListRefreshAdapter<CheckInfo> baseListRefreshAdapter) {

    }
}
