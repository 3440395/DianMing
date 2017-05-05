package com.zyr.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.zyr.base.BaseActivity;
import com.zyr.common.Constant;
import com.zyr.common.R;
import com.zyr.ui.fragment.FragmentFactory;
import com.zyr.ui.fragment.HomeBottomTabFragment;
import com.zyr.ui.view.MToolbar;
import com.zyr.util.ViewUtils;

/**
 * Created by xuekai on 2017/5/5.
 */

public abstract class HomeActivity extends BaseActivity implements View.OnClickListener {
    private MToolbar toolbar;
    private FragmentFactory fragmentFactory;
    private int role;
    public static final int ROLE_TEACHER = 0;
    public static final int ROLE_STUDENT = 1;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void findViews() {
        toolbar = ViewUtils.findViewById(this, R.id.toolbar);
    }

    @Override
    protected void setupViews(Bundle bundle) {
        int role = bundle.getInt("role");
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void fetchData() {
        ((HomeBottomTabFragment) getSupportFragmentManager().findFragmentByTag("HomeBottomTabFragment")).setRole(role);
        ((HomeBottomTabFragment) getSupportFragmentManager().findFragmentByTag("HomeBottomTabFragment")).selectBottomTab(1);
    }


    @Override
    public void onClick(View v) {

    }


    /**
     * 根据参数设置内容fragment
     *
     * @author xk
     * @time 2016/6/4 11:25
     */
    public void selectContentFragment(int tabPosition) {
        FragmentManager fm = getSupportFragmentManager();
        if (fragmentFactory == null) {
            fragmentFactory = new FragmentFactory();
        }
        if (!fragmentFactory.getFragment(tabPosition,role).isAdded()) {
            fm.beginTransaction().add(R.id.home_content, fragmentFactory.getFragment(tabPosition,role), fragmentFactory.getFragment(tabPosition,role).getTag()).commit();
        }
        for (int i = 1; i < 5; i++) {
            if (i == tabPosition) {
                fm.beginTransaction().show(fragmentFactory.getFragment(i,role)).commit();
            } else {
//                Log.e("HomeActivity","隐藏"+i);

                fm.beginTransaction().hide(fragmentFactory.getFragment(i,role)).commit();
            }
        }
    }

    public void setToolbar(int position) {
        if (position == 3) {
            toolbar.setVisibility(View.GONE);
            return;
        } else {
            toolbar.setVisibility(View.VISIBLE);
        }
        switch (position) {
            case 1:
                toolbar.setLeftTextView(null);
                toolbar.setRightTextView("筛选");
                break;
            case 2:
                toolbar.setLeftTextView("关注路线");
                toolbar.setRightTextView(null);

                break;
            case 3:
                toolbar.setRightTextView("更多");
                toolbar.setLeftTextView(null);
                break;
        }
        toolbar.setTitle(role==1?Constant.tab_names_student[position - 1]:Constant.tab_names_teacher[position - 1]);
    }
}
