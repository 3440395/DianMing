package com.zyr.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zyr.base.BaseFragment;
import com.zyr.common.Constant;
import com.zyr.common.R;
import com.zyr.ui.activity.HomeActivity;
import com.zyr.util.ViewUtils;


/**
 * Created by xk on 2016/6/4 10:40.
 */
public class HomeBottomTabFragment extends BaseFragment implements View.OnClickListener {
    private ImageView iv_tab_icon1, iv_tab_icon2, iv_tab_icon3, iv_tab_icon4;
    private RelativeLayout rl_tab1, rl_tab2, rl_tab3, rl_tab4;
    private TextView tv_tab_name1, tv_tab_name2, tv_tab_name3, tv_tab_name4;

    private int role = 0;


    @Override
    protected void setLayoutRes() {
        layoutRes = R.layout.layout_home_tab_bottom;
    }

    @Override
    protected void findViews(View v) {
        rl_tab1 = ViewUtils.findViewById(v, R.id.rl_tab1);
        rl_tab2 = ViewUtils.findViewById(v, R.id.rl_tab2);
        rl_tab3 = ViewUtils.findViewById(v, R.id.rl_tab3);
        iv_tab_icon1 = ViewUtils.findViewById(v, R.id.iv_tab_icon1);
        iv_tab_icon2 = ViewUtils.findViewById(v, R.id.iv_tab_icon2);
        iv_tab_icon3 = ViewUtils.findViewById(v, R.id.iv_tab_icon3);

        tv_tab_name1 = ViewUtils.findViewById(v, R.id.tv_tab_name1);
        tv_tab_name2 = ViewUtils.findViewById(v, R.id.tv_tab_name2);
        tv_tab_name3 = ViewUtils.findViewById(v, R.id.tv_tab_name3);


    }

    @Override
    protected void setupViews(View v) {
    }

    @Override
    protected void setListener(View v) {
        rl_tab1.setOnClickListener(this);
        rl_tab2.setOnClickListener(this);
        rl_tab3.setOnClickListener(this);
    }

    @Override
    protected void fetchData(View v) {

    }

    public void setTabName() {
        tv_tab_name1.setText(role == HomeActivity.ROLE_TEACHER ? Constant.tab_names_teacher[0] : Constant.tab_names_student[0]);
        tv_tab_name2.setText(role == HomeActivity.ROLE_TEACHER ? Constant.tab_names_teacher[1] : Constant.tab_names_student[1]);
        tv_tab_name3.setText(role == HomeActivity.ROLE_TEACHER ? Constant.tab_names_teacher[2] : Constant.tab_names_student[2]);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rl_tab1) {
            selectBottomTab(1);
        } else if (i == R.id.rl_tab2) {
            selectBottomTab(2);
        } else if (i == R.id.rl_tab3) {
            selectBottomTab(3);
        }
    }


    /**
     * 根据参数 设置底部tab的图标
     *
     * @author xk
     * @time 2016/6/2 19:50
     */
    public void selectBottomTab(int tabPosition) {
        ((HomeActivity) mActivity).selectContentFragment(tabPosition);

        if (tabPosition == 1) {
            iv_tab_icon1.setImageResource(role == HomeActivity.ROLE_TEACHER ? R.mipmap.tab_icon_student_select:R.mipmap.tab_icon_student_select);
            tv_tab_name1.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else {
            iv_tab_icon1.setImageResource(role == HomeActivity.ROLE_TEACHER ? R.mipmap.tab_icon_student:R.mipmap.tab_icon_student);
            tv_tab_name1.setTextColor(getResources().getColor(R.color.primary_text));
        }
        if (tabPosition == 2) {
            iv_tab_icon2.setImageResource(role == HomeActivity.ROLE_TEACHER ? R.mipmap.tab_icon_course_select:R.mipmap.tab_icon_course_select);
            tv_tab_name2.setTextColor(getResources().getColor(R.color.colorPrimary));

        } else {
            iv_tab_icon2.setImageResource(role == HomeActivity.ROLE_TEACHER ? R.mipmap.tab_icon_course:R.mipmap.tab_icon_course);
            tv_tab_name2.setTextColor(getResources().getColor(R.color.primary_text));

        }
        if (tabPosition == 3) {
            iv_tab_icon3.setImageResource(role == HomeActivity.ROLE_TEACHER ? R.mipmap.tab_icon_me_select:R.mipmap.tab_icon_me_select);
            tv_tab_name3.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else {
            iv_tab_icon3.setImageResource(role == HomeActivity.ROLE_TEACHER ? R.mipmap.tab_icon_me:R.mipmap.tab_icon_me);
            tv_tab_name3.setTextColor(getResources().getColor(R.color.primary_text));

        }
        ((HomeActivity) mActivity).setToolbar(tabPosition);

    }


    public void setRole(int role) {
        this.role = role;
    }
}
