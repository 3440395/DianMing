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
import com.zyr.ui.adapter.BaseRecycleAdapter;
import com.zyr.ui.adapter.BaseViewHolder;
import com.zyr.ui.fragment.RefreshBaseFragment;
import com.zyr.ui.view.MToolbar;

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
    private Student student;
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
                // TODO: by xk 2017/5/8 0:17
            }

            @Override
            public void requestData(SwipeRefreshLayout swipeRefreshLayout) {
                List<CheckInfo> checkInfos;
                if (isAdmin) {
                    //获取该课程的所有学生
                    checkInfos = requestAllStudentByCourseId(swipeRefreshLayout);
                } else {
                    //仅获取自己
                    checkInfos = requestSelf(swipeRefreshLayout);
                }
                setData(checkInfos);
            }

        };
        fragment.setAdapter(baseListRefreshAdapter);
        baseListRefreshAdapter.setOnItemClickListner(new BaseRecycleAdapter.OnItemClickListner<Course>() {
            @Override
            public void onItemClickListner(View v, Course o) {
            }
        });
        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.root, fragment).commit();
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
    public abstract List<CheckInfo> requestAllStudentByCourseId(SwipeRefreshLayout swipeRefreshLayout);

    /**
     * 获取自己
     */
    public abstract List<CheckInfo> requestSelf(SwipeRefreshLayout swipeRefreshLayout);

    @Override
    protected void setListener() {

    }

    @Override
    protected void fetchData() {
        toolbar.setTitle((isAdmin ? " 签到信息表" : "签到"));
    }
}
