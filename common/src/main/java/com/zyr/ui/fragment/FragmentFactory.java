package com.zyr.ui.fragment;

import android.support.v4.app.Fragment;

import com.zyr.ui.activity.HomeActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xk on 2016/6/2 21:00.
 */
public class FragmentFactory {
    public FragmentFactory() {
        fragments = new HashMap<Integer, Fragment>();
    }

    private Map<Integer, Fragment> fragments;

    public Fragment getFragment(int position,int role) {
        if (fragments.get(position) == null) {
            boolean isTeacher=(role== HomeActivity.ROLE_TEACHER);

            Fragment fragment;
            switch (position) {
                case 1:
                    if (isTeacher) {

                    }
//                    fragment = new FindTruckFragment();
//                    fragments.put(1, fragment);
                    break;
                case 2:
                    if (isTeacher) {

                    }
//                    fragment = new FindCargoFragment();
//                    fragments.put(2, fragment);
                    break;
                case 3:
                    if (isTeacher) {

                    }
//                    fragment = new MessageFragment();
//                    fragments.put(3, fragment);
                    break;
            }
        }
        return fragments.get(position);
    }
}
