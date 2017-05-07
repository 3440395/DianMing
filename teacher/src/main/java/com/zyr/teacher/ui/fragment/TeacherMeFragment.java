package com.zyr.teacher.ui.fragment;

import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.zyr.entity.Teacher;
import com.zyr.subscirber.ProgressSubscriber;
import com.zyr.teacher.R;
import com.zyr.teacher.db.Dao;
import com.zyr.teacher.ui.activity.TeacherHomeActivity;
import com.zyr.teacher.ui.activity.TeacherLoginActivity;
import com.zyr.ui.fragment.MeFragment;
import com.zyr.util.RxSchedulerHelper;

import rx.Observable;

/**
 * Created by xuekai on 2017/5/6.
 */
public class TeacherMeFragment extends MeFragment<Teacher> {
    private Teacher teacher;
    private Dao dao;


    @Override
    protected void fetchData(View v) {
        super.fetchData(v);
        teacher = (Teacher) ((TeacherHomeActivity) getActivity()).getTeacher();
        dao = ((TeacherHomeActivity) getActivity()).getDao();
    }

    @Override
    public void updateUI(Teacher teacher) {
        String sex = teacher.getSex();
        ov_sex.setRightText(sex);
        ov_phone.setRightText(teacher.getPhone());
        int imgRes;
        if (sex != null) {
            imgRes = sex.equals("男") ? R.mipmap.head_teacher_man : R.mipmap.head_teacher_women;
        } else {
            imgRes = R.mipmap.head_null;
        }
        iv_head.setImageResource(imgRes);
        tv_name.setText(teacher.getName());
    }


    @Override
    protected void setupViews(View v) {
        super.setupViews(v);
        hideStudentIdItem();
    }

    @Override
    public void setPhone() {
        super.setPhone();
        showUpdateInfoDialog(teacher, "电话号码");
    }

    @Override
    public void updatePassword() {
        showUpdateInfoDialog(teacher, "新密码");
    }


    @Override
    public void setSex() {
        showUpdateInfoDialog(teacher, "性别");
    }

    @Override
    public void toLoginActivity() {
        toActivity(TeacherLoginActivity.class);
    }

    private void showUpdateInfoDialog(Teacher teacher, String title) {
        View view = View.inflate(getActivity(), R.layout.dialog_edittext, null);//这里必须是final的
        EditText edit = (EditText) view.findViewById(R.id.et_edittext);//获得输入框对象
        new AlertDialog.Builder(getActivity())
                .setTitle(teacher.getName() + " 您好,请输入" + title)//提示框标题
                .setView(view)
                .setPositiveButton("确定",//提示框的两个按钮
                        (dialog, which) -> {
                            String s = edit.getText().toString();
                            if (title.equals("电话号码")) {
                                updatePhone(teacher, s);
                            } else if (title.equals("性别")) {
                                if (s.equals("男") || s.equals("女")) {
                                    updateSex(teacher, s);
                                } else {
                                    toast("请输入男或女");
                                }
                            } else if (title.equals("新密码")) {
                                if (s.trim().equals("")) {
                                    toast("密码不能为空");
                                } else {
                                    updatePassword(teacher, s);
                                }
                            }
                        })
                .setNegativeButton("取消", null).create().show();
    }

    private void updateSex(Teacher teacher, String s) {
        Observable
                .create((Observable.OnSubscribe<Teacher>) subscriber -> {
                    teacher.setSex(s);
                    dao.updateTeacherInfo(teacher.getName(), null, s, null);
                    subscriber.onNext(teacher);
                    subscriber.onCompleted();
                })
                .compose(RxSchedulerHelper.io_main())
                .subscribe(new ProgressSubscriber<Teacher>(getActivity(), false) {
                    @Override
                    public void onNext(Teacher teacher) {
                        updateUI(teacher);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    private void updatePassword(Teacher teacher, String s) {
        Observable
                .create((Observable.OnSubscribe<Teacher>) subscriber -> {
                    teacher.setPassword(s);
                    dao.updateTeacherInfo(teacher.getName(), s, null, null);
                    subscriber.onNext(teacher);
                    subscriber.onCompleted();
                })
                .compose(RxSchedulerHelper.io_main())
                .subscribe(new ProgressSubscriber<Teacher>(getActivity(), false) {
                    @Override
                    public void onNext(Teacher teacher) {
                        toast("密码修改成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }


    private void updatePhone(Teacher teacher, String s) {
        Observable
                .create((Observable.OnSubscribe<Teacher>) subscriber -> {
                    teacher.setPhone(s);
                    dao.updateTeacherInfo(teacher.getName(), null, null, s);
                    subscriber.onNext(teacher);
                    subscriber.onCompleted();
                })
                .compose(RxSchedulerHelper.io_main())
                .subscribe(new ProgressSubscriber<Teacher>(getActivity(), false) {
                    @Override
                    public void onNext(Teacher teacher) {
                        updateUI(teacher);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
