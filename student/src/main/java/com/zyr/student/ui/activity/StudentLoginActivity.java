package com.zyr.student.ui.activity;

import android.os.Bundle;

import com.zyr.base.BaseActivity;
import com.zyr.bean.Student;
import com.zyr.student.retrofit.Networks;
import com.zyr.util.RxSchedulerHelper;
import com.zyr.student.retrofit.subscirber.ProgressSubscriber;

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
                .subscribe(new ProgressSubscriber<Student>(this) {
                    @Override
                    public void onNext(Student student) {
                        System.out.println(student);
                    }
                });
    }

}
