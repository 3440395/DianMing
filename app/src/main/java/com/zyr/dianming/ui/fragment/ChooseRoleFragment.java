package com.zyr.dianming.ui.fragment;

import android.view.View;
import android.widget.Button;

import com.zyr.base.BaseFragment;
import com.zyr.common.Constant;
import com.zyr.common.R;
import com.zyr.student.ui.activity.StudentLoginActivity;
import com.zyr.teacher.ui.activity.TeacherLoginActivity;
import com.zyr.util.SharedPreferencesUtil;

/**
 * Created by X.Sation on 2017/5/3.
 */

public class ChooseRoleFragment extends BaseFragment {


    private Button student;
    private Button teacher;

    @Override
    protected void setLayoutRes() {
        layoutRes = R.layout.fragment_chooserole;
    }

    @Override
    protected void findViews(View v) {
        teacher = (Button) v.findViewById(R.id.teacher);
        student = (Button) v.findViewById(R.id.student);
    }

    @Override
    protected void setupViews(View v) {

    }

    @Override
    protected void setListener(View v) {
        teacher.setOnClickListener(v1 -> {
                    SharedPreferencesUtil.saveInt(getContext(), Constant.SP_KEY_CURRENT_ROLE, 0);
                    toActivity(TeacherLoginActivity.class);
                    getActivity().finish();
        }

        );
        student.setOnClickListener(v12 -> {
            SharedPreferencesUtil.saveInt(getContext(), Constant.SP_KEY_CURRENT_ROLE, 1);
            toActivity(StudentLoginActivity.class);
            getActivity().finish();
        }

        );
    }

    @Override
    protected void fetchData(View v) {

    }
}
