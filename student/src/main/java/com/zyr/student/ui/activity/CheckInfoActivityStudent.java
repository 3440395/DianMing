package com.zyr.student.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;

import com.zyr.entity.CheckInfo;
import com.zyr.ui.activity.CheckInfoActivity;

import java.util.List;

/**
 * Created by xuekai on 2017/5/8.
 */

public class CheckInfoActivityStudent extends CheckInfoActivity {
    @Override
    public List<CheckInfo> requestAllStudentByCourseId(SwipeRefreshLayout swipeRefreshLayout) {
        return null;
    }

    @Override
    public List<CheckInfo> requestSelf(SwipeRefreshLayout swipeRefreshLayout) {
        return null;
    }
}
