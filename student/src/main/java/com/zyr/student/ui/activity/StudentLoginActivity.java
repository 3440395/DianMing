package com.zyr.student.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.zyr.base.BaseActivity;
import com.zyr.entity.BaseEntity;
import com.zyr.entity.Student;
import com.zyr.student.R;
import com.zyr.student.net.ProgressSubscriber;
import com.zyr.student.net.retrofit.Networks;
import com.zyr.student.net.util.UrlUtil;
import com.zyr.ui.view.MToolbar;
import com.zyr.util.RxSchedulerHelper;
import com.zyr.util.ViewUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by X.Sation on 2017/5/3.
 */

public class StudentLoginActivity extends BaseActivity {
    private static final String TAG = "StudentLoginActivity";

    private Button addTeacher;
    private MToolbar toolbar;
    private EditText et_name;
    private EditText et_password;
    private Button btn_login;
    private Button btn_register;

    @Override
    protected void setLayout() {
        setContentView(R.layout.layout_login_student);
    }

    @Override
    protected void findViews() {
        toolbar = ViewUtils.findViewById(this, R.id.toolbar);
        et_name = ViewUtils.findViewById(this, R.id.et_name);
        et_password = ViewUtils.findViewById(this, R.id.et_password);
        btn_login = ViewUtils.findViewById(this, R.id.btn_login);
        btn_register = ViewUtils.findViewById(this, R.id.btn_register);
    }

    @Override
    protected void setupViews(Bundle bundle) {
        toolbar.setTitle("学生端入口");
    }

    @Override
    protected void setListener() {
        btn_login.setOnClickListener(v -> {
            registerOrLogin(new Login());
        });
        btn_register.setOnClickListener(v -> {
            registerOrLogin(new Register());
        });

    }

    private void registerOrLogin(Runnable runnable) {
        boolean result = false;
        String msg = "";
        if (!et_name.getText().toString().isEmpty() && !et_password.getText().toString().isEmpty()) {
            runnable.run();
        } else {
            result = false;
            msg = "请填入有效的用户名和密码";
        }
    }

    class Login implements Runnable {

        @Override
        public void run() {
            HashMap<String, String> params = new HashMap<>();
            params.put("action", "login");
            params.put("studentid", et_name.getText().toString());
            params.put("password", et_password.getText().toString());

            Networks.getInstance().getApiService()
                    .getStudent(params)
                    .compose(RxSchedulerHelper.io_main())
                    .subscribe(new ProgressSubscriber<BaseEntity<Student>>(StudentLoginActivity.this, true) {
                        @Override
                        public void onNext(BaseEntity<Student> studentBaseEntity) {
                            if (studentBaseEntity.getResultCode() == 1) {
                                Student data = studentBaseEntity.getData();
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("student", data);
                                bundle.putInt("role", 1);
                                toActivity(StudentHomeActivity.class, bundle);
                                finish();
                            } else {
                                toast("登录失败");
                            }
                        }
                    });
        }
    }

    private class Register implements Runnable {

        @Override
        public void run() {
            HashMap<String, String> params = new HashMap<>();
            params.put("action", "register");
            params.put("studentid", et_name.getText().toString());
            params.put("password", et_password.getText().toString());
            Networks.getInstance().getApiService()
                    .coreInterface(params)
                    .compose(RxSchedulerHelper.io_main())
                    .subscribe(new ProgressSubscriber<BaseEntity>(StudentLoginActivity.this, true) {
                        @Override
                        public void onNext(BaseEntity baseEntity) {
                            if (baseEntity.getResultCode() == 1) {
                                toast("注册成功");
                            } else {
                                toast("注册失败");
                            }
                        }
                    });

        }
    }

    @Override
    protected void fetchData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在学生登录页面的时候，获取地址
        UrlUtil.getInstance().init(getApplicationContext());
    }
}
