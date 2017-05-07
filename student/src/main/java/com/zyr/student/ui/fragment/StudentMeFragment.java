package com.zyr.student.ui.fragment;

import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.zyr.entity.BaseEntity;
import com.zyr.entity.Student;
import com.zyr.student.R;
import com.zyr.student.net.retrofit.Networks;
import com.zyr.student.ui.activity.StudentHomeActivity;
import com.zyr.student.ui.activity.StudentLoginActivity;
import com.zyr.subscirber.ProgressSubscriber;
import com.zyr.ui.fragment.MeFragment;
import com.zyr.util.RxSchedulerHelper;

import java.util.HashMap;

/**
 * Created by xuekai on 2017/5/6.
 */
public class StudentMeFragment extends MeFragment<Student> {
    private Student student;


    @Override
    protected void fetchData(View v) {
        super.fetchData(v);
        student = ((StudentHomeActivity) getActivity()).getStudent();
    }

    @Override
    public void updateUI(Student student) {
        String sex = student.getSex();
        ov_sex.setRightText(sex);
        ov_studentid.setRightText(student.getStudentid());
        int imgRes;
        if (sex != null) {
            imgRes = sex.equals("男") ? R.mipmap.head_student_boy : R.mipmap.head_student_girl;
        } else {
            imgRes = R.mipmap.head_null;
        }
        iv_head.setImageResource(imgRes);
        tv_name.setText((student.getName()==null||student.getName().equals(""))?"未设置名字":student.getName());
    }


    @Override
    protected void setupViews(View v) {
        super.setupViews(v);
        hidePhoneItem();
    }


    @Override
    public void updatePassword() {
        showUpdateInfoDialog(student, "新密码");
    }

    public void setName() {
        showUpdateInfoDialog(student, "姓名");
    }

    @Override
    public void setSex() {
        showUpdateInfoDialog(student, "性别");
    }

    @Override
    public void toLoginActivity() {
        toActivity(StudentLoginActivity.class);
    }

    private void showUpdateInfoDialog(Student student, String title) {
        View view = View.inflate(getActivity(), R.layout.dialog_edittext, null);//这里必须是final的
        EditText edit = (EditText) view.findViewById(R.id.et_edittext);//获得输入框对象
        new AlertDialog.Builder(getActivity())
                .setTitle(student.getName() + " 您好,请输入" + title)//提示框标题
                .setView(view)
                .setPositiveButton("确定",//提示框的两个按钮
                        (dialog, which) -> {
                            String s = edit.getText().toString();
                            if (title.equals("性别")) {
                                if (s.equals("男") || s.equals("女")) {
                                    updateSex(student, s);
                                } else {
                                    toast("请输入男或女");
                                }
                            } else if (title.equals("新密码")) {
                                if (s.trim().equals("")) {
                                    toast("密码不能为空");
                                } else {
                                    updatePassword(student, s);
                                }
                            } else if (title.equals("姓名")) {
                                updateName(student, s);
                            }
                        })
                .setNegativeButton("取消", null).create().show();
    }

    private void updateName(Student student, String s) {
        student.setName(s);
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "updateStudentInfo");
        params.put("studentid", student.getStudentid());
        params.put("name", student.getName());
        Networks.getInstance().getApiService()
                .coreInterface(params)
                .compose(RxSchedulerHelper.io_main())
                .subscribe(new ProgressSubscriber<BaseEntity>(getActivity(), true) {
                               @Override
                               public void onNext(BaseEntity baseEntity) {
                                   if (baseEntity.getResultCode() == 1) {
                                       updateUI(student);
                                   }
                               }
                           }
                );

    }

    private void updateSex(Student student, String s) {
        student.setSex(s);
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "updateStudentInfo");
        params.put("studentid", student.getStudentid());
        params.put("sex", student.getSex());
        Networks.getInstance().getApiService()
                .coreInterface(params)
                .compose(RxSchedulerHelper.io_main())
                .subscribe(new ProgressSubscriber<BaseEntity>(getActivity(), true) {
                               @Override
                               public void onNext(BaseEntity baseEntity) {
                                   if (baseEntity.getResultCode() == 1) {
                                       updateUI(student);
                                   }
                               }
                           }
                );

    }

    private void updatePassword(Student student, String s) {
        student.setPassword(s);
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "updateStudentInfo");
        params.put("studentid", student.getStudentid());
        params.put("password", student.getPassword());
        Networks.getInstance().getApiService()
                .coreInterface(params)
                .compose(RxSchedulerHelper.io_main())
                .subscribe(new ProgressSubscriber<BaseEntity>(getActivity(), true) {
                               @Override
                               public void onNext(BaseEntity baseEntity) {
                                   if (baseEntity.getResultCode() == 1) {
                                       updateUI(student);
                                   }
                               }
                           }
                );

    }
}
