package com.zyr.teacher.ui.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.zyr.base.BaseActivity;
import com.zyr.entity.Teacher;
import com.zyr.teacher.R;

/**
 * 老师的首页
 * Created by X.Sation on 2017/5/5.
 */

public class TeacherHomeActivity extends BaseActivity {
    @Override
    protected void setLayout() {
        setContentView(R.layout.layout_login);
        getSupportActionBar().setTitle("老师首页");
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void setupViews(Bundle bundle) {
        Teacher teacher = bundle.getParcelable("teacher");
        Log.e(TAG, "setupViews-->" + teacher);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void fetchData() {

    }
}
