package com.zyr.student.ui.activity;

import android.os.Bundle;
import android.util.Log;

import com.zyr.base.BaseActivity;
import com.zyr.entity.Student;
import com.zyr.student.net.retrofit.Networks;
import com.zyr.student.net.ProgressSubscriber;
import com.zyr.util.RxSchedulerHelper;

/**
 * Created by X.Sation on 2017/5/3.
 */

public class StudentLoginActivity extends BaseActivity {
    private static final String TAG = "StudentLoginActivity";

    @Override
    protected void setLayout() {

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
        Networks.getInstance()
                .getApiService()
                .query("123")
                .compose(RxSchedulerHelper.io_main())
                .subscribe(new ProgressSubscriber<Student>(this,false) {
                    @Override
                    public void onNext(Student student) {
                        Log.e("StudentLoginActivity","onNext"+student);
                    }
                });
    }

}
