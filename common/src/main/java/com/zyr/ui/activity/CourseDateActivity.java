package com.zyr.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;

import com.zyr.base.BaseActivity;
import com.zyr.common.R;
import com.zyr.entity.Course;
import com.zyr.entity.Student;
import com.zyr.ui.adapter.BaseListRefreshAdapter;
import com.zyr.ui.adapter.BaseRecycleAdapter;
import com.zyr.ui.adapter.BaseViewHolder;
import com.zyr.ui.fragment.RefreshBaseFragment;
import com.zyr.ui.view.MToolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by xuekai on 2017/5/7.
 */

public class CourseDateActivity extends BaseActivity {

    private ViewGroup root;
    private MToolbar toolbar;
    private FragmentManager fm;
    private Course course;
    private Student student;
    private int courseTime;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_course_date);
    }

    @Override
    protected void findViews() {
        toolbar = (MToolbar) findViewById(R.id.toolbar);
        root = (ViewGroup) findViewById(R.id.root);
    }

    @Override
    protected void setupViews(Bundle bundle) {
        course=bundle.getParcelable("course");
        student=bundle.getParcelable("student");
        courseTime=bundle.getInt("courseTime");
        setFragment();

    }

    private void setFragment() {
        RefreshBaseFragment<String> fragment = new RefreshBaseFragment<>();
        fragment.setCanSwipeRefresh(false);
        BaseListRefreshAdapter<String> baseListRefreshAdapter = new BaseListRefreshAdapter<String>(CourseDateActivity.this, R.layout.item_course_date, null) {

            @Override
            public String setEmptyMstContent() {
                return "";
            }

            @Override
            protected void convert(BaseViewHolder holder, String bean) {
                holder.setText(R.id.time,"上课时间："+bean);
            }

            @Override
            public void requestData(SwipeRefreshLayout swipeRefreshLayout) {
                List<String> stringList=new ArrayList<>();
                //星期几
                int day=courseTime%4+1;
                SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int week = cal.get(Calendar.WEEK_OF_YEAR);
                cal.clear();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.WEEK_OF_YEAR,week);
                cal.set(Calendar.DAY_OF_WEEK, day);

                for (int i = 0; i <10; i++) {
                    cal.set(Calendar.WEEK_OF_YEAR,week);
                    stringList.add(df.format(cal.getTime()));
                    week--;
                }
                setData(stringList);
            }
        };
        baseListRefreshAdapter.setOnItemClickListner(new BaseRecycleAdapter.OnItemClickListner<Course>() {
            @Override
            public void onItemClickListner(View v, Course o) {
            }
        });
        fragment.setAdapter(baseListRefreshAdapter);
        baseListRefreshAdapter.setOnItemClickListner(new BaseRecycleAdapter.OnItemClickListner<String>() {
            @Override
            public void onItemClickListner(View v, String o) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("course",course);
                bundle.putParcelable("student",student);
                bundle.putInt("courseTime",courseTime);
                bundle.putString("courseDate",o);
                toActivity(CheckInfoActivity.class,bundle);
            }
        });




        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.root, fragment).commit();
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void fetchData() {
        toolbar.setTitle("上课时间列表");

    }
}
