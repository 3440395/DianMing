package com.zyr.teacher.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import com.zyr.entity.Student;
import com.zyr.entity.Teacher;
import com.zyr.subscirber.ProgressSubscriber;
import com.zyr.teacher.R;
import com.zyr.teacher.db.Dao;
import com.zyr.ui.activity.HomeActivity;
import com.zyr.ui.adapter.BaseListRefreshAdapter;
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
                        fragment = createCurseManagerFragment();
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
    }

    private Fragment createStudentManagerFragment() {
        BaseListRefreshAdapter<Teacher> baseListRefreshAdapter = new BaseListRefreshAdapter<Teacher>(null, 1, null) {
            @Override
            public void requestData(SwipeRefreshLayout swipeRefreshLayout) {

            }
        };
        RefreshBaseFragment baseFragment = new RefreshBaseFragment<Student>();
        baseFragment.setAdapter(baseListRefreshAdapter);
        return baseFragment;
    }


    private Fragment createCurseManagerFragment() {
        BaseListRefreshAdapter<Teacher> baseListRefreshAdapter = new BaseListRefreshAdapter<Teacher>(TeacherHomeActivity.this, R.layout.item_teacher, null) {
            @Override
            public void requestData(SwipeRefreshLayout swipeRefreshLayout) {
                Observable
                        .create((Observable.OnSubscribe<List<Teacher>>) subscriber -> {
                            List<Teacher> teachers = dao.queryAllTeacher();
                            subscriber.onNext(teachers);
                            subscriber.onCompleted();
                        })
                        .compose(RxSchedulerHelper.io_main())
                        .subscribe(new ProgressSubscriber<List<Teacher>>(TeacherHomeActivity.this, true) {
                            @Override
                            public void onNext(List<Teacher> teachers) {
                                setData(teachers);
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
            }

        };
        RefreshBaseFragment baseFragment = new RefreshBaseFragment<Student>();
        baseFragment.setAdapter(baseListRefreshAdapter);
        return baseFragment;
    }

    private Fragment createMeFragment() {
        BaseListRefreshAdapter<Teacher> baseListRefreshAdapter = new BaseListRefreshAdapter<Teacher>(null, 1, null) {
            @Override
            public void requestData(SwipeRefreshLayout swipeRefreshLayout) {

            }
        };
        RefreshBaseFragment baseFragment = new RefreshBaseFragment<Student>();
        baseFragment.setAdapter(baseListRefreshAdapter);
        return baseFragment;
    }
}
