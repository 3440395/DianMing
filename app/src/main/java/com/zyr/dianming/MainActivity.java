package com.zyr.dianming;

import android.content.Context;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.zyr.common.net.CallServer;
import com.zyr.common.net.HttpListener;
import com.zyr.common.util.NetUtil;
import com.zyr.common.util.UrlUtil;
import com.zyr.dianming.app.Constant;
import com.zyr.teacher.CoreService;
import com.zyr.teacher.Helper;
import com.zyr.teacher.db.Dao;

import org.json.JSONObject;

import java.util.Calendar;

import static android.R.attr.x;
import static com.zyr.dianming.app.Constant.ip;


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
                boolean 薛凯 = dao.createCourse(1, "薛凯");
               Log.e("MainActivity","onClick"+薛凯);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean 薛凯 = dao.setPresident(1, "薛凯", 10);
                Log.e("MainActivity","onClick"+薛凯);
            }
        });
        update1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean 薛凯1 = dao.setPresident(1, "薛凯1", 10);
                Log.e("MainActivity","onClick"+薛凯1);

            }
        });
        update2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean 薛凯 = dao.createCourse(2, "薛凯");
                Log.e("MainActivity","onClick"+薛凯);

            }
        });

    }
}


