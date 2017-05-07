package com.zyr.student.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.zyr.common.Constant;
import com.zyr.entity.BaseEntity;
import com.zyr.entity.Course;
import com.zyr.entity.Student;
import com.zyr.entity.Teacher;
import com.zyr.student.R;
import com.zyr.student.net.ProgressSubscriber;
import com.zyr.student.net.retrofit.Networks;
import com.zyr.student.ui.fragment.StudentMeFragment;
import com.zyr.ui.activity.CourseDateActivity;
import com.zyr.ui.activity.HomeActivity;
import com.zyr.ui.adapter.BaseListRefreshAdapter;
import com.zyr.ui.adapter.BaseRecycleAdapter;
import com.zyr.ui.adapter.BaseViewHolder;
import com.zyr.ui.fragment.FragmentFactory;
import com.zyr.ui.fragment.RefreshBaseFragment;
import com.zyr.util.RxSchedulerHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 学生的首页
 * Created by X.Sation on 2017/5/6.
 */

public class StudentHomeActivity extends HomeActivity {
    final String items[] = {"周一 1-2节", "周一 3-4节", "周一 5-6节", "周一 7-8节",
            "周二 1-2节", "周二 3-4节", "周二 5-6节", "周二 7-8节",
            "周三 1-2节", "周三 3-4节", "周三 5-6节", "周三 7-8节",
            "周四 1-2节", "周四 3-4节", "周四 5-6节", "周四 7-8节",
            "周五 1-2节", "周五 3-4节", "周五 5-6节", "周五 7-8节"};
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
        BaseListRefreshAdapter<Teacher> baseListRefreshAdapter = new BaseListRefreshAdapter<Teacher>(mContext, R.layout.item_teacher, null) {
            @Override
            public String setEmptyMstContent() {
                return "您还没有代课老师";
            }

            @Override
            protected void convert(BaseViewHolder holder, Teacher bean) {
                String sex = bean.getSex();
                int imgRes;
                if (sex != null) {
                    sex = sex.equals("男") ? "♂" : "♀";
                    imgRes = sex.equals("♂") ? R.mipmap.head_teacher_man : R.mipmap.head_teacher_women;
                } else {
                    sex = "-";
                    imgRes = R.mipmap.head_null;
                }

                String phone = bean.getPhone();
                if (phone == null) {
                    phone = "-";
                }

                holder.setText(R.id.tv_name, bean.getName());
                holder.setText(R.id.tv_sex, bean.getSex());
                holder.setText(R.id.tv_phone, bean.getPhone());
                holder.setImageResource(R.id.iv_head, imgRes);
            }

            @Override
            public void requestData(SwipeRefreshLayout swipeRefreshLayout) {
                //查询学生的老师
                //查询我的课程
                HashMap<String, String> params = new HashMap<>();
                params.put("action", "queryCourseByStudentId");
                params.put("studentid", student.getStudentid());
                Networks.getInstance().getApiService()
                        .getListCourses(params)
                        .compose(RxSchedulerHelper.io_main())
                        .subscribe(new ProgressSubscriber<BaseEntity<List<Course>>>(StudentHomeActivity.this, true) {
                            @Override
                            public void onNext(BaseEntity<List<Course>> courses) {
                                if (courses.getResultCode() == 1) {
                                    List<Course> data = courses.getData();

                                    ArrayList<Teacher> teachers = new ArrayList<>();
                                    for (Course course : data) {
                                        teachers.add(course.getTeacher());
                                    }
                                    List<Teacher> teachers1 = removeDuplicate(teachers);
                                    setData(teachers1);
                                } else {
                                    toast("获取老师失败");
                                }
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
        BaseListRefreshAdapter<Course> baseListRefreshAdapter = new BaseListRefreshAdapter<Course>(StudentHomeActivity.this, R.layout.item_course_student, null) {
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
                        imgRes = sex.equals("男") ? R.mipmap.head_teacher_man : R.mipmap.head_teacher_women;
                    } else {
                        imgRes = R.mipmap.head_null;
                    }
                    holder.setImageResource(R.id.iv_head, imgRes);
                    if (president.getStudentid().equals(student.getStudentid())) {
                        holder.setText(R.id.tv_president, "我");
                    } else {
                        holder.setText(R.id.tv_president, president.getName());
                    }
                }
                holder.setText(R.id.tv_name, bean.getName());
                holder.setText(R.id.tv_teacher, "老师：" + bean.getTeacher().getName());
            }

            @Override
            public void requestData(SwipeRefreshLayout swipeRefreshLayout) {
                //查询我的课程
                HashMap<String, String> params = new HashMap<>();
                params.put("action", "queryCourseByStudentId");
                params.put("studentid", student.getStudentid());
                Networks.getInstance().getApiService()
                        .getListCourses(params)
                        .compose(RxSchedulerHelper.io_main())
                        .subscribe(new ProgressSubscriber<BaseEntity<List<Course>>>(StudentHomeActivity.this, true) {
                            @Override
                            public void onNext(BaseEntity<List<Course>> courses) {
                                if (courses.getResultCode() == 1) {
                                    List<Course> data = courses.getData();
                                    setData(data);
                                } else {
                                    toast("获取课程失败");
                                }
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
                showChooseCourseTimeDialog(o);
            }
        });
        myCourseFragment = new RefreshBaseFragment<Course>();
        myCourseFragment.setAdapter(baseListRefreshAdapter);
        return myCourseFragment;
    }

    private void showChooseCourseTimeDialog(Course o) {

        final String items[] = new String[o.getTimes().length];
        for (int i = 0; i < o.getTimes().length; i++) {
            items[i] = this.items[o.getTimes()[i]];
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(StudentHomeActivity.this);
        builder.setTitle("请选择具体的课");
        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setItems(items, (dialog, which) -> {
            dialog.dismiss();
            Bundle bundle = new Bundle();
            bundle.putParcelable("course",o);
            bundle.putParcelable("student",student);
            bundle.putInt("courseTime",which+1);
            toActivity(CourseDateActivity.class, bundle);
        });
        builder.create().show();
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
                            if (data.size() > 0) {
                                showChooseCourseDialog(data);
                            } else {
                                toast("您没有未选择的课程");
                            }
                        } else {
                            toast("获取未选课程失败");
                        }
                    }
                });


    }

    /**
     * 开始显示选课dialog
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
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < selected.length; i++) {
                if (selected[i]) {
                    sb.append(data.get(i).getId() + ",");
                }
            }
            if (sb.length() > 0) {
                String courseids = sb.substring(0, sb.length() - 1);
                //2.将选中的课程的id发送给服务端，让服务端去保存
                HashMap<String, String> params = new HashMap<>();
                params.put("action", "chooseCourseByStudent");
                params.put("studentid", student.getStudentid());
                params.put("courseids", courseids);
                Networks.getInstance()
                        .getApiService()
                        .coreInterface(params)
                        .compose(RxSchedulerHelper.io_main())
                        .subscribe(new ProgressSubscriber<BaseEntity>(StudentHomeActivity.this, true) {
                            @Override
                            public void onNext(BaseEntity baseEntity) {
                                if (baseEntity.getResultCode() == 1) {
                                    myCourseFragment.onRefresh();
                                } else {
                                    toast("选课失败");
                                }
                            }
                        });
            }
        });
        builder.create().show();
    }

    public List<Teacher> removeDuplicate(List<Teacher> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getName().equals(list.get(i).getName())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }
}
