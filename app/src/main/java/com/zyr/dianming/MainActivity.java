package com.zyr.dianming;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zyr.net.NetUtil;
import com.zyr.teacher.CoreService;
import com.zyr.teacher.db.Dao;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Dao dao;


    public static void main(String[] args) {
        int[] str = {1, 2, 3};
        changeStr(str);
        System.out.println(str[0]);
    }

    private static void changeStr(int[] str) {
        str[0] = 2;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = new Dao(MainActivity.this);

        Button add = (Button) findViewById(R.id.add);
        Button update = (Button) findViewById(R.id.update);
        Button update1 = (Button) findViewById(R.id.update1);
        Button update2 = (Button) findViewById(R.id.update2);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean b = NetUtil.checkNet();
                        Log.e(TAG, "onClick" + b);
                    }
                }).start();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this, CoreService.class));
            }
        });
        update1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean 数学 = dao.setCourseTime(3, 1);
                Log.e("MainActivity", "onClick" + 数学);
            }
        });
        update2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean 数学 = dao.setCourseTime(1, 12);
                Log.e("MainActivity", "onClick" + 数学);
            }
        });

    }
}


