package com.zyr.dianming.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.zyr.base.BaseActivity;
import com.zyr.common.Constant;
import com.zyr.common.R;
import com.zyr.dianming.ui.fragment.ChooseRoleFragment;
import com.zyr.student.ui.activity.StudentLoginActivity;
import com.zyr.teacher.CoreService;
import com.zyr.teacher.ui.activity.TeacherLoginActivity;
import com.zyr.util.SharedPreferencesUtil;

/**
 * Created by X.Sation on 2017/5/3.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void setupViews(Bundle bundle) {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void fetchData() {
        startService(new Intent(SplashActivity.this, CoreService.class));
        int role = SharedPreferencesUtil.getInt(mContext, Constant.SP_KEY_CURRENT_ROLE);
        switch (role) {
            case 0://跳转到老师登录页面
                toActivity(TeacherLoginActivity.class);
                finish();
                break;
            case 1://跳转到学生登录页面
                toActivity(StudentLoginActivity.class);
                finish();
                break;
            case -1://展示choose的fragment
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("Splash")
                        .add(R.id.root, new ChooseRoleFragment())
                        .commit();
                break;
        }
    }
}
