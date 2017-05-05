package com.zyr.teacher.ui.activity;

import android.support.v4.app.Fragment;

import com.zyr.ui.activity.HomeActivity;
import com.zyr.ui.fragment.BaseListFragment;
import com.zyr.ui.fragment.FragmentFactory;

/**
 * 老师的首页
 * Created by X.Sation on 2017/5/5.
 */

public class TeacherHomeActivity extends HomeActivity {
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


    public static class StudentManagerFragment extends BaseListFragment {

        @Override
        public void requestData() {

        }
    }

    private Fragment createStudentManagerFragment() {

        return new StudentManagerFragment();
    }


    private Fragment createCurseManagerFragment() {
        return new StudentManagerFragment();
    }

    private Fragment createMeFragment() {
        return new StudentManagerFragment();
    }
}
