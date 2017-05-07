package com.zyr.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyr.base.BaseFragment;
import com.zyr.common.Constant;
import com.zyr.common.R;
import com.zyr.ui.view.OptionItemView;
import com.zyr.util.SharedPreferencesUtil;
import com.zyr.util.ViewUtils;

/**
 * Created by xuekai on 2017/5/6.
 */

public abstract class MeFragment<T> extends BaseFragment implements View.OnClickListener {
    protected OptionItemView ov_exit;
    protected OptionItemView ov_sex;
    protected OptionItemView ov_studentid;
    protected OptionItemView ov_phone;
    protected OptionItemView ov_reset;
    protected OptionItemView ov_password;
    protected ImageView iv_head;
    protected TextView tv_name;
    /**
     * 数据源
     */
    private T t;

    public void setData(T t) {
        this.t = t;
    }

    public void hideStudentIdItem() {
        ov_studentid.setVisibility(View.GONE);
    }

    public void showStudentIdItem() {
        ov_studentid.setVisibility(View.VISIBLE);
    }

    public void hidePhoneItem() {
        ov_phone.setVisibility(View.GONE);
    }

    public void showPhoneItem() {
        ov_phone.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setLayoutRes() {
        layoutRes = R.layout.fragment_me;
    }

    @Override
    protected void findViews(View v) {
        ov_exit = ViewUtils.findViewById(v, R.id.ov_exit);
        ov_sex = ViewUtils.findViewById(v, R.id.ov_sex);
        ov_studentid = ViewUtils.findViewById(v, R.id.ov_studentid);
        ov_phone = ViewUtils.findViewById(v, R.id.ov_phone);
        ov_reset = ViewUtils.findViewById(v, R.id.ov_reset);
        iv_head = ViewUtils.findViewById(v, R.id.iv_head);
        tv_name = ViewUtils.findViewById(v, R.id.tv_name);
        ov_password = ViewUtils.findViewById(v, R.id.ov_password);
    }


    @Override
    protected void setupViews(View v) {
        updateUI(t);
    }

    @Override
    protected void setListener(View v) {
        ov_exit.setOnClickListener(this);
        ov_sex.setOnClickListener(this);
        ov_studentid.setOnClickListener(this);
        ov_phone.setOnClickListener(this);
        ov_reset.setOnClickListener(this);
        ov_password.setOnClickListener(this);
        tv_name.setOnClickListener(this);
    }

    @Override
    protected void fetchData(View v) {
        updateUI(t);
    }

    public abstract void updateUI(T t);

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ov_exit) {
            toLoginActivity();
        }
        if (i == R.id.ov_sex) {
            setSex();
        }
        if (i == R.id.ov_studentid) {
            setStudentId();
        }
        if (i == R.id.ov_phone) {
            setPhone();
        }
        if (i == R.id.ov_reset) {
            SharedPreferencesUtil.saveInt(getContext(), Constant.SP_KEY_CURRENT_ROLE, -1);
            getActivity().finish();
        }
        if (i == R.id.ov_password) {
            updatePassword();
        }
        if (i == R.id.tv_name) {
            setName();
        }
    }

    public void setName() {

    }

    public void setPhone() {
    }

    public abstract void updatePassword();

    public void setStudentId() {

    }

    public abstract void setSex();

    public abstract void toLoginActivity();
}
