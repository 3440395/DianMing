package com.zyr.dianming;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zyr.teacher.db.Dao;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Dao dao;

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
                boolean 数学 = dao.setCourseTime(1, 1);
                Log.e("MainActivity", "onClick" + 数学);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean 薛凯 = dao.setCourseTime(1, 1);
                Log.e("MainActivity", "onClick" + 薛凯);
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


