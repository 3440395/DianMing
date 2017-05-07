package com.zyr.student.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.zyr.common.Constant;
import com.zyr.entity.BaseEntity;
import com.zyr.entity.Course;
import com.zyr.entity.Student;
import com.zyr.student.R;
import com.zyr.student.net.ProgressSubscriber;
import com.zyr.student.net.retrofit.Networks;
import com.zyr.student.ui.fragment.StudentMeFragment;
import com.zyr.ui.activity.HomeActivity;
import com.zyr.ui.adapter.BaseListRefreshAdapter;
import com.zyr.ui.adapter.BaseViewHolder;
import com.zyr.ui.fragment.FragmentFactory;
import com.zyr.ui.fragment.RefreshBaseFragment;
import com.zyr.util.RxSchedulerHelper;

import java.util.HashMap;
import java.util.List;

/**
 * 学生的首页
 * Created by X.Sation on 2017/5/6.
 */

public class StudentHomeActivity extends HomeActivity {

    /**
     * 筛选id 按钮点击之后，改变这里，刷新学生数据的课程依据从这里拿
     */
    private int[] filterCourseIds;
    private Student student;
    private RefreshBaseFragment myTeacherFragment;
    private RefreshBaseFragment myCourseFragment;


    public Student getStudent() {
        return student;
    }


    @Override
    public FragmentFactory createFragmentFactory() {
        return new FragmentFactory() {
            @Override
            public Fragment createFragment(int position) {
                Fragment fragment = null;
                switch (position) {
                    case 1:
                        fragment = createMyTeacherFragment();
                        break;
                    case 2:
                        fragment = createMyCourseFragment();
                        break;
                    case 3:
                        fragment = createMeFragment();
                        break;
                }
                return fragment;
            }
        };
    }

    @Override
    protected void fetchData() {
        super.fetchData();
        student = mBundle.getParcelable("student");
    }

    /**
     * 创建我的老师的fragment
     *
     * @return
     */
    private Fragment createMyTeacherFragment() {
        BaseListRefreshAdapter<Student> baseListRefreshAdapter = new BaseListRefreshAdapter<Student>(mContext, 1, null) {
            @Override
            public String setEmptyMstContent() {
                return "您还没有代课老师";
            }

            @Override
            protected void convert(BaseViewHolder holder, Student bean) {
//                holder.setText(R.id.tv_name, "姓名：" + bean.getName());
//                holder.setText(R.id.tv_studentid, "学号：" + bean.getPassword());
//                String sex = bean.getSex();
//                int imgRes;
//                if (sex != null) {
//                    imgRes = sex.equals("男") ? R.mipmap.head_teacher_man : R.mipmap.head_teacher_women;
//                } else {
//                    imgRes = R.mipmap.head_null;
//                }
//                holder.setImageResource(R.id.iv_head, imgRes);
            }

            @Override
            public void requestData(SwipeRefreshLayout swipeRefreshLayout) {
//                Observable
//                        .create((Observable.OnSubscribe<List<Student>>) subscriber -> {
//                            //从数据库中查询学生数据
//                            List<Student> students = null;
//                            List<Course> courses = dao.queryCourseByTeacher(teacher.getId());
//                            for (Course course : courses) {
//                                List<Student> students1 = dao.queryStudentsByCourseId(course.getId());
//                                if (students1 != null) {
//                                    students.addAll(students1);
//                                }
//                            }
//                            subscriber.onNext(students);
//                            subscriber.onCompleted();
//                        })
//                        .compose(RxSchedulerHelper.io_main())
//                        .subscribe(new ProgressSubscriber<List<Student>>(StudentHomeActivity.this, false) {
//                            @Override
//                            public void onNext(List<Student> students) {
//                                setData(students);
//                                swipeRefreshLayout.setRefreshing(false);
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                super.onError(e);
//                                swipeRefreshLayout.setRefreshing(false);
//                            }
//                        });
            }
        };
        myTeacherFragment = new RefreshBaseFragment<Student>();
        myTeacherFragment.setAdapter(baseListRefreshAdapter);
        return myTeacherFragment;
    }

    /**
     * 创建我的课程的fragment
     *
     * @return
     */
    private Fragment createMyCourseFragment() {
        BaseListRefreshAdapter<Course> baseListRefreshAdapter = new BaseListRefreshAdapter<Course>(StudentHomeActivity.this, 1, null) {
            @Override
            public String setEmptyMstContent() {
                return "您还没有添加课程";
            }

            @Override
            protected void convert(BaseViewHolder holder, Course bean) {
//                Student president = bean.getPresident();
//                if (president == null) {
//                    holder.setImageResource(R.id.iv_head, R.mipmap.head_null);
//                    holder.setText(R.id.tv_president, "无班长");
//                } else {
//                    String sex = president.getSex();
//                    int imgRes;
//                    if (sex != null) {
//                        imgRes = sex.equals("男") ? R.mipmap.head_teacher_man : R.mipmap.head_teacher_women;
//                    } else {
//                        imgRes = R.mipmap.head_null;
//                    }
//
//
//                    holder.setImageResource(R.id.iv_head, imgRes);
//                    holder.setText(R.id.tv_president, president.getName());
//                }
//                holder.setText(R.id.tv_name, bean.getName());
            }

            @Override
            public void requestData(SwipeRefreshLayout swipeRefreshLayout) {
            }
        };
        myCourseFragment = new RefreshBaseFragment<Course>();
        myCourseFragment.setAdapter(baseListRefreshAdapter);
        return myCourseFragment;
    }

    /**
     * 创建“我的” fragment
     *
     * @return
     */
    private Fragment createMeFragment() {
        StudentMeFragment studentMeFragment = new StudentMeFragment();
        studentMeFragment.setData(student);
        return studentMeFragment;
    }


    @Override
    public void onLeftClick() {

    }

    @Override
    public void onRightClick() {
        String title = toolbar.getTitle();

        if (title.equals(Constant.tab_names_student[1])) {
            //选课
            Bundle bundle = new Bundle();
            bundle.putParcelable("student", student);
            showChooseCourseDialog();
        }
    }

    /**
     * 展示选课dialog
     */
    private void showChooseCourseDialog() {
        //1.从服务端获取所有我没选中的课程
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "queryUncheckedCourse");
        params.put("studentid", student.getStudentid());
        Networks.getInstance().getApiService()
                .getListCourses(params)
                .compose(RxSchedulerHelper.io_main())
                .subscribe(new ProgressSubscriber<BaseEntity<List<Course>>>(StudentHomeActivity.this, true) {
                    @Override
                    public void onNext(BaseEntity<List<Course>> baseEntity) {
                        if (baseEntity.getResultCode() == 1) {
                            List<Course> data = baseEntity.getData();
                            showChooseCourseDialog(data);
                            System.out.println(data.toString());
                        } else {
                            toast("获取未选课程失败");
                        }
                    }


                });

//        //2.将选中的课程的id发送给服务端，让服务端去保存
//        String[] items={};
//        AlertDialog.Builder builder = new AlertDialog.Builder(StudentHomeActivity.this);
//        builder.setTitle("请选择课程"); //设置标题
//        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
//        boolean[] selected = new boolean[20];
//        builder.setMultiChoiceItems(items, selected, (dialog, which, isChecked) -> {
//        });
//        builder.setPositiveButton("确定", (dialog, which) -> {
//            dialog.dismiss();
//            StringBuffer sb = new StringBuffer();
//            for (int i = 0; i < selected.length; i++) {
//                if (selected[i]) {
//                    sb.append(i + 1);
//                    sb.append(",");
//                }
//            }
//            if (sb.length() > 1) {
//                String courseTime = sb.substring(0, sb.length() - 1);
//                saveCourseTime(courseId, courseTime);
//                courseManagerFragment.onRefresh();
//            }
//        });
//        builder.create().show();
    }

    /**
     * 开始显示dialog
     *
     * @param data
     */
    private void showChooseCourseDialog(List<Course> data) {
        String[] items = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            items[i] = data.get(i).getName() + "(" + data.get(i).getTeacher().getName() + ")";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(StudentHomeActivity.this);
        builder.setTitle("请选择课程"); //设置标题
        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        boolean[] selected = new boolean[data.size()];
        builder.setMultiChoiceItems(items, selected, (dialog, which, isChecked) -> {
        });
        builder.setPositiveButton("确定", (dialog, which) -> {
            dialog.dismiss();
            for (int i = 0; i < selected.length; i++) {
                if (selected[i]) {
                    Log.e(TAG, "showChooseCourseDialog" + data.get(i).toString());
                }
            }
//            StringBuffer sb = new StringBuffer();
//            for (int i = 0; i < selected.length; i++) {
//                if (selected[i]) {
//                    sb.append(i + 1);
//                    sb.append(",");
//                }
//            }
//            if (sb.length() > 1) {
//                String courseTime = sb.substring(0, sb.length() - 1);
//                saveCourseTime(courseId, courseTime);
//                courseManagerFragment.onRefresh();
//            }
        });
        builder.create().show();
    }

//    private void showCreateCourseDialog(Teacher teacher) {
//        View view = View.inflate(mContext, R.layout.dialog_create_course, null);
//        EditText edit = (EditText) view.findViewById(R.id.et_course_name);
//        new AlertDialog.Builder(StudentHomeActivity.this)
//                .setTitle(teacher.getName() + "老师,请输入课程名")//提示框标题
//                .setView(view)
//                .setPositiveButton("确定",//提示框的两个按钮
//                        (dialog, which) -> {
//                            createCourse(edit.getText().toString());
//                        })
//                .setNegativeButton("取消", null).create().show();
//        edit.requestFocus();
//    }

}
