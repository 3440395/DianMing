package com.zyr.teacher.ui.activity;

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
import com.zyr.ui.activity.HomeActivity;
import com.zyr.ui.adapter.BaseListRefreshAdapter;
import com.zyr.ui.adapter.BaseViewHolder;
import com.zyr.ui.fragment.FragmentFactory;
import com.zyr.ui.fragment.RefreshBaseFragment;
import com.zyr.util.RxSchedulerHelper;

import java.util.List;

import rx.Observable;

/**
 * 老师的首页
 * Created by X.Sation on 2017/5/5.
 */

public class TeacherHomeActivity extends HomeActivity {

    private Dao dao;
    private Teacher teacher;
    private RefreshBaseFragment studentManagerFragment;
    private RefreshBaseFragment courseManagerFragment;

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
        BaseListRefreshAdapter<Teacher> baseListRefreshAdapter = new BaseListRefreshAdapter<Teacher>(mContext, 1, null) {
            @Override
            protected void convert(BaseViewHolder holder, Teacher bean) {

            }

            @Override
            public void requestData(SwipeRefreshLayout swipeRefreshLayout) {

            }
        };
        studentManagerFragment = new RefreshBaseFragment<Student>();
        studentManagerFragment.setAdapter(baseListRefreshAdapter);
        return studentManagerFragment;
    }


    /**
     * 创建课程管理的fragment
     *
     * @return
     */
    private Fragment createCourseManagerFragment() {
        BaseListRefreshAdapter<Course> baseListRefreshAdapter = new BaseListRefreshAdapter<Course>(TeacherHomeActivity.this, R.layout.item_course, null) {
            @Override
            protected void convert(BaseViewHolder holder, Course bean) {
//                holder.setText(R.id.tv_president, "");
//                holder.setText(R.id.tv_time, "");
                holder.setText(R.id.tv_name, bean.getName());
            }

            @Override
            public void requestData(SwipeRefreshLayout swipeRefreshLayout) {
                Observable
                        .create((Observable.OnSubscribe<List<Course>>) subscriber -> {
                            List<Course> courses = dao.queryCourseByTeacher(teacher.getId());
                            // TODO: by xk 2017/5/6 0:16 拿出课程的学生id，查出学生，放进去
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
                        });
            }
        };
        courseManagerFragment = new RefreshBaseFragment<Course>();
        courseManagerFragment.setAdapter(baseListRefreshAdapter);
        return courseManagerFragment;
    }

    private Fragment createMeFragment() {
        BaseListRefreshAdapter<Teacher> baseListRefreshAdapter = new BaseListRefreshAdapter<Teacher>(null, 1, null) {
            @Override
            protected void convert(BaseViewHolder holder, Teacher bean) {

            }

            @Override
            public void requestData(SwipeRefreshLayout swipeRefreshLayout) {
            }
        };
        RefreshBaseFragment baseFragment = new RefreshBaseFragment<Student>();
        baseFragment.setAdapter(baseListRefreshAdapter);
        return baseFragment;
    }

    @Override
    public void onLeftClick() {

    }

    @Override
    public void onRightClick() {
        String title = toolbar.getTitle();
        if (title.equals(Constant.tab_names_teacher[0])) {
            //筛选

        }
        if (title.equals(Constant.tab_names_teacher[1])) {
            //添加科目
            showCreateCourseDialog(teacher);
        }
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
}
