package com.zyr.student.ui.activity;

import android.os.Bundle;

import com.zyr.ui.activity.CourseDateActivity;

/**
 * Created by xuekai on 2017/5/8.
 */

public class CourseDateActivityStudent extends CourseDateActivity {
    @Override
    public void toCheckInfoActivity(Bundle bundle) {
        toActivity(CheckInfoActivityStudent.class,bundle);
    }
}
