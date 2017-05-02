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
import com.zyr.dianming.app.Constant;
import com.zyr.teacher.CoreService;
import com.zyr.teacher.Helper;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button btn1;
    private Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Helper.getInstance().init(this);

        WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            System.out.println("=================");
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String IPAddress = intToIp(wifiInfo.getIpAddress());
        System.out.println("IPAddress-->>" + IPAddress);

        DhcpInfo dhcpinfo = wifiManager.getDhcpInfo();
        String serverAddress = intToIp(dhcpinfo.serverAddress);
        System.out.println("serverAddress-->>" + serverAddress);

        Toast.makeText(this,IPAddress+"   "+serverAddress,Toast.LENGTH_LONG).show();

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this, CoreService.class));
//                startActivity(new Intent(MainActivity.this, TestActivity.class));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request<JSONObject> loginRequest = NoHttp.createJsonObjectRequest(Constant.url_test, RequestMethod.POST);


//                loginRequest.add("p1", Calendar.getInstance().getTime().toLocaleString());
                loginRequest.add("你", "你好");
                loginRequest.add("你1", "你好1");
                loginRequest.add("你2", "你好2");
                loginRequest.add("你33", "你好3");

                CallServer.getRequestInstance().add(MainActivity.this, Constant.request_what_login, loginRequest, new HttpListener<JSONObject>() {
                    @Override
                    public void onSucceed(int what, Response<JSONObject> response) {
                        JSONObject jsonObject = response.get();
                        Log.e(TAG, "onSucceed" + jsonObject.toString());
                    }

                    @Override
                    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                        Log.e(TAG, "onFailed");
                    }
                }, true, true, "加载中");
            }
        });
    }

    public void setTextView(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btn2.setText(s);
                Log.e(TAG, "设置了");
            }
        });
    }


    private String intToIp(int paramInt)
    {
        return (paramInt & 0xFF) + "." + (0xFF & paramInt >> 8) + "." + (0xFF & paramInt >> 16) + "."
                + (0xFF & paramInt >> 24);
    }
}
