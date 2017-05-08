package com.zyr.teacher.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.zyr.common.Constant;
import com.zyr.entity.Course;
import com.zyr.entity.Student;
import com.zyr.entity.Teacher;
import com.zyr.subscirber.ProgressSubscriber;
import com.zyr.teacher.R;
import com.zyr.teacher.db.Dao;
import com.zyr.teacher.ui.fragment.TeacherMeFragment;
import com.zyr.ui.activity.HomeActivity;
import com.zyr.ui.adapter.BaseListRefreshAdapter;
import com.zyr.ui.adapter.BaseRecycleAdapter;
import com.zyr.ui.adapter.BaseViewHolder;
import com.zyr.ui.fragment.FragmentFactory;
import com.zyr.ui.fragment.RefreshBaseFragment;
import com.zyr.util.RxSchedulerHelper;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * 老师的首页
 * Created by X.Sation on 2017/5/5.
 */

public class TeacherHomeActivity extends HomeActivity {

    /**
     * 筛选id 按钮点击之后，改变这里，刷新学生数据的课程依据从这里拿
     */
    private int filterCourseId=-1;
    private Dao dao;
    private Teacher teacher;
    private RefreshBaseFragment studentManagerFragment;
    private RefreshBaseFragment courseManagerFragment;


    public Teacher getTeacher() {
        return teacher;
    }

    public Dao getDao() {
        return dao;
    }

    @Override
    public FragmentFactory createFragmentFactory() {
        return new FragmentFactory() {
            @Override
            public Fragment createFragment(int position) {
                Fragment fragment = null;
                switch (position) {
                    case 1:
                        fragment = createStudentManagerFragment();
                        break;
                    case 2:
                        fragment = createCourseManagerFragment();
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
        dao = new Dao(this);
        super.fetchData();
        teacher = mBundle.getParcelable("teacher");
    }

    /**
     * 创建学生管理的fragment
     *
     * @return
     */
    private Fragment createStudentManagerFragment() {
        BaseListRefreshAdapter<Student> baseListRefreshAdapter = new BaseListRefreshAdapter<Student>(mContext, R.layout.item_student, null) {
            @Override
            public String setEmptyMstContent() {
                return "您还没有学生";
            }

            @Override
            protected void convert(BaseViewHolder holder, Student bean) {
                holder.setText(R.id.tv_name, "姓名：" + bean.getName());
                holder.setText(R.id.tv_studentid, "学号：" + bean.getStudentid());
                String sex = bean.getSex();
                int imgRes;
                if (sex != null) {
                    imgRes = sex.equals("男") ? R.mipmap.head_student_boy : R.mipmap.head_student_girl;
                } else {
                    imgRes = R.mipmap.head_null;
                }
                holder.setImageResource(R.id.iv_head, imgRes);
            }

            @Override
            public void requestData(SwipeRefreshLayout swipeRefreshLayout) {
                Observable
                        .create((Observable.OnSubscribe<List<Student>>) subscriber -> {
                            //从数据库中查询学生数据
                            List<Student> students = new ArrayList<>();
                            List<Course> courses = dao.queryCourseByTeacher(teacher.getId());
                            if (filterCourseId == -1) {
                                for (Course course : courses) {
                                    List<Student> students1 = dao.queryStudentsByCourseId(course.getId());
                                    if (students1 != null) {
                                        students.addAll(students1);
                                    }
                                }
                            } else {
                                for (Course course : courses) {
                                    if (course.getId() == filterCourseId) {
                                        List<Student> students1 = dao.queryStudentsByCourseId(course.getId());
                                        if (students1 != null) {
                                            students.addAll(students1);
                                        }
                                    }
                                }
                            }


                            List<Student> newStudents = removeDuplicate(students);
                            subscriber.onNext(newStudents);
                            subscriber.onCompleted();
                        })
                        .compose(RxSchedulerHelper.io_main())
                        .subscribe(new ProgressSubscriber<List<Student>>(TeacherHomeActivity.this, false) {
                            @Override
                            public void onNext(List<Student> students) {
                                setData(students);
                                swipeRefreshLayout.setRefreshing(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
            }
        };
        baseListRefreshAdapter.setOnItemClickListner(new BaseRecycleAdapter.OnItemClickListner<Student>() {
            @Override
            public void onItemClickListner(View v, Student o) {
                if (filterCourseId==-1) {
                    toast("请先筛选学生");
                }else{
                    boolean b = dao.setPresident(filterCourseId, Integer.parseInt(o.getStudentid()));
                    if (b) {
                        toast("设置班长成功");
                    }else{
                        toast("设置班长失败");
                    }
                }
            }
        });
        studentManagerFragment = new RefreshBaseFragment<Student>();
        studentManagerFragment.setAdapter(baseListRefreshAdapter);
        return studentManagerFragment;
    }

    final String items[] = {"周一 1-2节", "周一 3-4节", "周一 5-6节", "周一 7-8节",
            "周二 1-2节", "周二 3-4节", "周二 5-6节", "周二 7-8节",
            "周三 1-2节", "周三 3-4节", "周三 5-6节", "周三 7-8节",
            "周四 1-2节", "周四 3-4节", "周四 5-6节", "周四 7-8节",
            "周五 1-2节", "周五 3-4节", "周五 5-6节", "周五 7-8节"};

    /**
     * 创建课程管理的fragment
     *
     * @return
     */
    private Fragment createCourseManagerFragment() {

        BaseListRefreshAdapter<Course> baseListRefreshAdapter = new BaseListRefreshAdapter<Course>(TeacherHomeActivity.this, R.layout.item_course, null) {
            @Override
            public String setEmptyMstContent() {
                return "您还没有添加课程";
            }

            @Override
            protected void convert(BaseViewHolder holder, Course bean) {
                Student president = bean.getPresident();
                if (president == null) {
                    holder.setImageResource(R.id.iv_head, R.mipmap.head_null);
                    holder.setText(R.id.tv_president, "无班长");
                } else {
                    String sex = president.getSex();
                    int imgRes;
                    if (sex != null) {
                        imgRes = sex.equals("男") ? R.mipmap.head_student_boy : R.mipmap.head_student_girl;
                    } else {
                        imgRes = R.mipmap.head_null;
                    }


                    holder.setImageResource(R.id.iv_head, imgRes);
                    holder.setText(R.id.tv_president, president.getName());
                }
                holder.setText(R.id.tv_name, bean.getName());
                StringBuffer sb = new StringBuffer();
                for (int i : bean.getTimes()) {
                    sb.append(items[i] + "  ");
                }
                holder.setText(R.id.tv_time, sb.toString());
            }

            @Override
            public void requestData(SwipeRefreshLayout swipeRefreshLayout) {
                Observable
                        .create((Observable.OnSubscribe<List<Course>>) subscriber -> {
                            List<Course> courses = dao.queryCourseByTeacher(teacher.getId());
                            //从数据库中查出班长详细信息，然后放到course中
                            for (Course course : courses) {
                                if (course.getPresidentid() != null) {
                                    Student student = dao.queryStudentByStudentId(course.getPresidentid());
                                    course.setPresident(student);
                                }
                            }

                            //从数据库中查出该课程的上课时间，然后放到course中
                            for (Course course : courses) {
                                String s = dao.queryCourseTime(course.getId());
                                if (s.length() > 0) {
                                    String[] split = s.split(",");
                                    int[] ints = new int[split.length];
                                    for (int i = 0; i < split.length; i++) {
                                        ints[i] = Integer.valueOf(split[i]);
                                    }
                                    course.setTimes(ints);
                                }
                            }
                            subscriber.onNext(courses);
                            subscriber.onCompleted();
                        })
                        .compose(RxSchedulerHelper.io_main())
                        .subscribe(new ProgressSubscriber<List<Course>>(TeacherHomeActivity.this, false) {
                            @Override
                            public void onNext(List<Course> courses) {
                                setData(courses);
                                swipeRefreshLayout.setRefreshing(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
            }
        };
        baseListRefreshAdapter.setOnItemClickListner(new BaseRecycleAdapter.OnItemClickListner<Course>() {

            @Override
            public void onItemClickListner(View v, Course o) {
                final String items[] = {"进入", "设置上课时间"};
                AlertDialog.Builder builder = new AlertDialog.Builder(TeacherHomeActivity.this);
                builder.setTitle("请选择操作");
                builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
                builder.setItems(items, (dialog, which) -> {
                    dialog.dismiss();
                    if (which == 0) {
                        showChooseCourseTimeDialog(o);
                    } else {
                        showSetCourseTimeDialog(o.getId());
                    }

                });
                builder.create().show();
            }

            private void showSetCourseTimeDialog(int courseId) {

                AlertDialog.Builder builder = new AlertDialog.Builder(TeacherHomeActivity.this);
                builder.setTitle("请设置上课时间"); //设置标题
                builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可  
                boolean[] selected = new boolean[20];
                builder.setMultiChoiceItems(items, selected, (dialog, which, isChecked) -> {
                });
                builder.setPositiveButton("确定", (dialog, which) -> {
                    dialog.dismiss();
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < selected.length; i++) {
                        if (selected[i]) {
                            sb.append(i);
                            sb.append(",");
                        }
                    }
                    if (sb.length() > 1) {
                        String courseTime = sb.substring(0, sb.length() - 1);
                        saveCourseTime(courseId, courseTime);
                        courseManagerFragment.onRefresh();
                    }
                });
                builder.create().show();
            }
        });
        courseManagerFragment = new RefreshBaseFragment<Course>();
        courseManagerFragment.setAdapter(baseListRefreshAdapter);
        return courseManagerFragment;
    }

    private void showChooseCourseTimeDialog(Course o) {

        final String items[] = new String[o.getTimes().length];
        for (int i = 0; i < o.getTimes().length; i++) {
            items[i] = this.items[o.getTimes()[i]];
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(TeacherHomeActivity.this);
        builder.setTitle("请选择具体的课");
        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setItems(items, (dialog, which) -> {
            dialog.dismiss();
            Bundle bundle = new Bundle();
            bundle.putParcelable("course", o);

            String content = items[which];
            int courseTime = 0;
            for (int i = 0; i < TeacherHomeActivity.this.items.length; i++) {
                if (TeacherHomeActivity.this.items[i].equals(content)) {
                    courseTime = i;
                }
            }
            bundle.putInt("courseTime", courseTime);
            toActivity(CourseDateActivityTeacher.class, bundle);
        });
        builder.create().show();
    }

    /**
     * 保存上课时间信息
     *
     * @param courseId
     * @param courseTime
     */
    private void saveCourseTime(int courseId, String courseTime) {
        boolean b = dao.setCourseTime(courseId, courseTime);
        if (b) {
            toast("保存成功");
        } else {
            toast("保存失败");
        }
    }

    /**
     * 创建“我的” fragment
     *
     * @return
     */
    private Fragment createMeFragment() {
        TeacherMeFragment teacherMeFragment = new TeacherMeFragment();
        teacherMeFragment.setData(teacher);
        return teacherMeFragment;
    }


    @Override
    public void onLeftClick() {

    }

    @Override
    public void onRightClick() {
        String title = toolbar.getTitle();
        if (title.equals(Constant.tab_names_teacher[0])) {
            //筛选
            // TODO: by xk 2017/5/6 11:17 筛选
            showShaixuanDialog();

        }
        if (title.equals(Constant.tab_names_teacher[1])) {
            //添加科目
            showCreateCourseDialog(teacher);
        }
    }

    private void showShaixuanDialog() {

        List<Course> courses = dao.queryCourseByTeacher(teacher.getId());
        final String items[] = new String[courses.size()];

        for (int i = 0; i < courses.size(); i++) {
            items[i] = courses.get(i).getName();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(TeacherHomeActivity.this);
        builder.setTitle("请选择筛选学生的课程");
        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setItems(items, (dialog, which) -> {
            dialog.dismiss();
            Course course = courses.get(which);
            filterCourseId = course.getId();
            studentManagerFragment.onRefresh();

        });
        builder.create().show();
    }

    private void createCourse(String courseName) {
        Observable
                .create((Observable.OnSubscribe<Boolean>) subscriber -> {
                    boolean result = dao.createCourse(teacher.getId(), courseName);
                    subscriber.onNext(result);
                    subscriber.onCompleted();
                })
                .compose(RxSchedulerHelper.io_main())
                .subscribe(new ProgressSubscriber<Boolean>(this, false) {
                    @Override
                    public void onNext(Boolean result) {
                        if (courseManagerFragment != null) {
                            courseManagerFragment.onRefresh();
                        }
                    }
                });
    }

    private void showCreateCourseDialog(Teacher teacher) {
        View view = View.inflate(mContext, R.layout.dialog_create_course, null);
        EditText edit = (EditText) view.findViewById(R.id.et_course_name);
        new AlertDialog.Builder(TeacherHomeActivity.this)
                .setTitle(teacher.getName() + "老师,请输入课程名")//提示框标题
                .setView(view)
                .setPositiveButton("确定",//提示框的两个按钮
                        (dialog, which) -> {
                            createCourse(edit.getText().toString());
                        })
                .setNegativeButton("取消", null).create().show();
        edit.requestFocus();
    }

    public List<Student> removeDuplicate(List<Student> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getStudentid().equals(list.get(i).getStudentid())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }
}
