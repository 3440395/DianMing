package com.zyr.ui.fragment;

import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xk on 2016/6/2 21:00.
 */
public abstract class FragmentFactory {

    public FragmentFactory() {
        fragments = new HashMap<Integer, Fragment>();
    }

    public int getSize() {
        return fragments.size();
    }

    private Map<Integer, Fragment> fragments;

    public Fragment getFragment(int position) {
        if (fragments.get(position) == null) {
            fragments.put(position, createFragment(position));
        }
        return fragments.get(position);
    }

    /**
     * 创建指定position对应的fragment
     *
     * @param position
     */
    public abstract Fragment createFragment(int position);
}
