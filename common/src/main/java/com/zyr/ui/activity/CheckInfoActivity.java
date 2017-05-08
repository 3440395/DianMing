package com.zyr.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;

import com.zyr.base.BaseActivity;
import com.zyr.common.R;
import com.zyr.entity.CheckInfo;
import com.zyr.entity.Course;
import com.zyr.entity.Student;
import com.zyr.ui.adapter.BaseListRefreshAdapter;
import com.zyr.ui.adapter.BaseViewHolder;
import com.zyr.ui.fragment.RefreshBaseFragment;
import com.zyr.ui.view.MToolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by xuekai on 2017/5/7.
 */

public abstract class CheckInfoActivity extends BaseActivity {
    final String items[] = {"周一 1-2节", "周一 3-4节", "周一 5-6节", "周一 7-8节",
            "周二 1-2节", "周二 3-4节", "周二 5-6节", "周二 7-8节",
            "周三 1-2节", "周三 3-4节", "周三 5-6节", "周三 7-8节",
            "周四 1-2节", "周四 3-4节", "周四 5-6节", "周四 7-8节",
            "周五 1-2节", "周五 3-4节", "周五 5-6节", "周五 7-8节"};
    private boolean isAdmin = false;//是否拥有管理权限
    protected Student student;
    protected Course course;
    protected int courseTime;
    protected String courseDate;
    private ViewGroup root;
    private MToolbar toolbar;
    private FragmentManager fm;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_recyclerview_refresh);
    }

    @Override
    protected void findViews() {
        toolbar = (MToolbar) findViewById(R.id.toolbar);
        root = (ViewGroup) findViewById(R.id.root);

    }

    @Override
    protected void setupViews(Bundle bundle) {
        student = (Student) bundle.get("student");
        course = (Course) bundle.get("course");
        courseTime = (Integer) bundle.get("courseTime");
        courseDate = (String) bundle.get("courseDate");
        if (student == null) {
            //我是老师
            isAdmin = true;
        } else {
            if (student.getStudentid().equals(course.getPresidentid())) {
                isAdmin = true;
            } else {
                isAdmin = false;
            }
        }
        setFragment();

    }


    private void setFragment() {
        RefreshBaseFragment<CheckInfo> fragment = new RefreshBaseFragment<>();
        BaseListRefreshAdapter<CheckInfo> baseListRefreshAdapter = new BaseListRefreshAdapter<CheckInfo>(CheckInfoActivity.this, R.layout.item_checkin, null) {

            @Override
            public String setEmptyMstContent() {
                return "";
            }

            @Override
            protected void convert(BaseViewHolder holder, CheckInfo bean) {
                holder.setText(R.id.tv_name, bean.getStudent().getName()+"("+bean.getStudent().getStudentid()+")");
                holder.setText(R.id.tv_time, courseDate + " " + items[courseTime]);
                if (bean.isCheck()) {
                    holder.setText(R.id.tv_check, "已签到");
                    holder.setTextColor(R.id.tv_check, 0xff25D1A6);
                    holder.setOnClickListener(R.id.tv_check, null);

                } else {
                    if (student != null) {
                        if (student.getStudentid().equals(bean.getStudent().getStudentid())) {
                            //是自己
                            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式
                            Calendar calendar = Calendar.getInstance();
                            int hour = calendar.get(Calendar.HOUR_OF_DAY);
                            String date = df.format(new Date());
                            boolean isNow = isNow(hour, courseTime);
                            if (date.equals(courseDate) && isNow) {
                                //这节课允许签到
                                holder.setText(R.id.tv_check, "点击签到");
                                holder.setTextColor(R.id.tv_check, 0xFFE2C716);
                                holder.setOnClickListener(R.id.tv_check, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        checkIn(fragment);
                                    }
                                });
                            } else {
                                holder.setOnClickListener(R.id.tv_check, null);
                                holder.setText(R.id.tv_check, "未签到");
                                holder.setTextColor(R.id.tv_check, 0xFFE02724);
                            }
                        } else {
                            holder.setOnClickListener(R.id.tv_check, null);
                            holder.setText(R.id.tv_check, "未签到");
                            holder.setTextColor(R.id.tv_check, 0xFFE02724);
                        }


                    } else {
                        //是老师
                        holder.setText(R.id.tv_check, "未签到");
                        holder.setTextColor(R.id.tv_check, 0xFFE02724);
                        holder.setOnClickListener(R.id.tv_check, null);
                    }

                }
            }

            @Override
            public void requestData(SwipeRefreshLayout swipeRefreshLayout) {
                List<CheckInfo> checkInfos;
                if (isAdmin) {
                    //获取该课程的所有学生
                    requestAllStudentByCourseId(swipeRefreshLayout, this);
                } else {
                    //仅获取自己
                    requestSelf(swipeRefreshLayout, this);
                }

            }

        };
        fragment.setAdapter(baseListRefreshAdapter);
        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.root, fragment).commit();
    }

//    public static void main(String args[]) {
//        isNow(1, 2);
//        isNow(1, 3);
//        isNow(1, 4);
//        isNow(1, 5);
//        isNow(1, 6);
//        isNow(1, 7);
//        isNow(1, 8);
//        isNow(1, 9);
//        isNow(1, 10);
//        isNow(1, 11);
//        isNow(1, 12);
//    }


    /**
     * 该节课是现在
     *
     * @param hour
     * @param courseTime
     * @return
     */
    protected  boolean isNow(int hour, int courseTime) {
        int i = courseTime % 4;

        switch (i) {
            case 0:
                if (hour < 10 && hour >= 8) {
                    return true;
                }
                break;
            case 1:
                if (hour < 12 && hour >= 10) {
                    return true;
                }
                break;
            case 2:
                if (hour < 16 && hour >= 14) {
                    return true;
                }
                break;
            case 3:
                if (hour < 18 && hour >= 16) {
                    return true;
                }
                break;
        }
        return false;
    }

//    /**
//     * 获取该课程的所有学生
//     */
//    private void requestAllStudentByCourseId(SwipeRefreshLayout swipeRefreshLayout) {
//        HashMap<String, String> params = new HashMap<>();
//        params.put("action", "queryCourseByStudentId");
//        params.put("studentid", student.getStudentid());
//        Networks.getInstance().getApiService()
//                .getListCourses(params)
//                .compose(RxSchedulerHelper.io_main())
//                .subscribe(new ProgressSubscriber<BaseEntity<List<Course>>>(StudentHomeActivity.this, true) {
//                    @Override
//                    public void onNext(BaseEntity<List<Course>> courses) {
//                        if (courses.getResultCode() == 1) {
//                            List<Course> data = courses.getData();
//
//                            ArrayList<Teacher> teachers = new ArrayList<>();
//                            for (Course course : data) {
//                                teachers.add(course.getTeacher());
//                            }
//                            List<Teacher> teachers1 = removeDuplicate(teachers);
//                            setData(teachers1);
//                        } else {
//                            toast("获取老师失败");
//                        }
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                });
//    }
//
//    /**
//     * 获取自己
//     */
//    private void requestSelf(SwipeRefreshLayout swipeRefreshLayout) {
//    }

    /**
     * 获取该课程的所有学生
     */
    public abstract void requestAllStudentByCourseId(SwipeRefreshLayout swipeRefreshLayout, BaseListRefreshAdapter<CheckInfo> baseListRefreshAdapter);

    /**
     * 签到
     *
     * @return
     * @param fragment
     */
    public abstract void checkIn(RefreshBaseFragment<CheckInfo> fragment);

    /**
     * 获取自己
     */
    public abstract void requestSelf(SwipeRefreshLayout swipeRefreshLayout, BaseListRefreshAdapter<CheckInfo> baseListRefreshAdapter);

    @Override
    protected void setListener() {

    }

    @Override
    protected void fetchData() {
        toolbar.setTitle((isAdmin ? " 签到信息表" : "签到"));
    }
}
