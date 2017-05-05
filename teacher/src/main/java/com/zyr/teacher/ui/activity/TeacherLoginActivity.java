package com.zyr.teacher.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zyr.base.BaseActivity;
import com.zyr.entity.Teacher;
import com.zyr.subscirber.ProgressSubscriber;
import com.zyr.teacher.R;
import com.zyr.teacher.db.Dao;
import com.zyr.teacher.ui.TeacherListAdapter;
import com.zyr.util.RxSchedulerHelper;

import java.util.List;

import rx.Observable;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by X.Sation on 2017/5/3.
 */

public class TeacherLoginActivity extends BaseActivity {

    private Button addTeacher;
    private RecyclerView teacherList;
    private Dao dao;
    private TeacherListAdapter teacherListAdapter;

    @Override
    protected void setLayout() {
        setContentView(R.layout.layout_login);
    }

    @Override
    protected void findViews() {
        addTeacher = (Button) findViewById(R.id.btn_add);
        teacherList = (RecyclerView) findViewById(R.id.rv_teacher_list);

    }

    @Override
    protected void setupViews(Bundle bundle) {
        teacherList.setLayoutManager(new LinearLayoutManager(mContext));
        teacherListAdapter = new TeacherListAdapter(mContext);
        teacherList.setAdapter(teacherListAdapter);
    }

    @Override
    protected void setListener() {
        addTeacher.setOnClickListener(v -> {
            toActivity(TeacherRegisterActivity.class);
        });
        teacherListAdapter.setOnItemClickListener(teacher -> {
            showPasswordDialog(teacher);

        });
    }

    private void showPasswordDialog(Teacher teacher) {
        View view = View.inflate(mContext,R.layout.dialog_login, null);//这里必须是final的
        EditText edit = (EditText) view.findViewById(R.id.et_password);//获得输入框对象
        new AlertDialog.Builder(TeacherLoginActivity.this)
                .setTitle(teacher.getName() + " 您好,请输入密码")//提示框标题
                .setView(view)
                .setPositiveButton("确定",//提示框的两个按钮
                        (dialog, which) -> {
                            teacher.setPassword(edit.getText().toString());
                            login(teacher);
                        })
                .setNegativeButton("取消", null).create().show();
        edit.requestFocus();
    }

    private void login(Teacher teacher) {
        Observable
                .create((Observable.OnSubscribe<Boolean>) subscriber -> {
                    boolean result = dao.loginTeacher(teacher.getName(),teacher.getPassword());
                    subscriber.onNext(result);
                    subscriber.onCompleted();
                })
                .compose(RxSchedulerHelper.io_main())
                .subscribe(new ProgressSubscriber<Boolean>(TeacherLoginActivity.this, true) {
                    @Override
                    public void onNext(Boolean result) {
                        if (result) {
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("teacher",teacher);
                            toActivity(TeacherHomeActivity.class,bundle);
                        }else{
                            toast("密码错误");
                        }
                    }
                });
    }

    @Override
    protected void fetchData() {
        dao = new Dao(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllTeacher();
    }

    /**
     * 获取所有的老师
     */
    private void getAllTeacher() {

        Observable
                .create((Observable.OnSubscribe<List<Teacher>>) subscriber -> {
                    List<Teacher> teachers = dao.queryAllTeacher();
                    subscriber.onNext(teachers);
                    subscriber.onCompleted();
                })
                .compose(RxSchedulerHelper.io_main())
                .subscribe(new ProgressSubscriber<List<Teacher>>(TeacherLoginActivity.this, true) {
                    @Override
                    public void onNext(List<Teacher> teachers) {
                        teacherListAdapter.setDatas(teachers);
                    }
                });

    }
}
