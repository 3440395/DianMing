package com.zyr.teacher.ui.activity;

import android.os.Bundle;

import com.zyr.ui.activity.CourseDateActivity;

/**
 * Created by xuekai on 2017/5/8.
 */

public class CourseDateActivityTeacher extends CourseDateActivity {
    @Override
    public void toCheckInfoActivity(Bundle bundle) {
        toActivity(CheckInfoActivityTeacher.class,bundle);
    }
}
