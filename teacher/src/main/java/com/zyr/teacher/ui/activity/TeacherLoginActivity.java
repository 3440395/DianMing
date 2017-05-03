package com.zyr.teacher.ui.activity;

import android.os.Bundle;
import android.widget.Button;

import com.zyr.base.BaseActivity;
import com.zyr.teacher.R;
import com.zyr.teacher.db.Dao;

/**
 * Created by X.Sation on 2017/5/3.
 */

public class TeacherLoginActivity extends BaseActivity {

    private Button addTeacher;
    private Button teacherList;
    private Dao dao;
    @Override
    protected void setLayout() {
        setContentView(R.layout.layout_login);
    }

    @Override
    protected void findViews() {
        addTeacher = (Button) findViewById(R.id.btn_add);
        teacherList = (Button) findViewById(R.id.rv_teacher_list);

    }

    @Override
    protected void setupViews(Bundle bundle) {

    }

    @Override
    protected void setListener() {
        addTeacher.setOnClickListener(v -> {
//            dao.registerTeacher()
        });
    }

    @Override
    protected void fetchData() {
        dao=new Dao(this);
    }
}
