package com.zyr.teacher.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.zyr.base.BaseActivity;
import com.zyr.teacher.R;
import com.zyr.teacher.db.Dao;
import com.zyr.ui.view.MToolbar;
import com.zyr.util.ViewUtils;

/**
 * Created by xuekai on 2017/5/4.
 */

public class TeacherRegisterActivity extends BaseActivity {

    private EditText password;
    private EditText name;
    private Button register;
    private Dao dao;

    @Override
    protected void setLayout() {
        setContentView(R.layout.layout_register);
    }

    @Override
    protected void findViews() {
        name = (EditText) findViewById(R.id.teacher_name);
        password = (EditText) findViewById(R.id.teacher_password);
        register = (Button) findViewById(R.id.register);
    }

    @Override
    protected void setupViews(Bundle bundle) {
        MToolbar toolbar = ViewUtils.findViewById(this, R.id.toolbar);
        toolbar.setTitle("注册老师信息");
    }


    @Override
    protected void setListener() {
        register.setOnClickListener(v -> {
            boolean result;
            String msg = "您已注册";
            if (!name.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                result = dao.registerTeacher(name.getText().toString(), password.getText().toString());
            } else {
                result = false;
                msg = "请填入有效的用户名和密码";
            }
            if (result) {
                finish();
            } else {
                toast(msg);
            }
        });
    }

    @Override
    protected void fetchData() {
        dao = new Dao(this);
    }
}
