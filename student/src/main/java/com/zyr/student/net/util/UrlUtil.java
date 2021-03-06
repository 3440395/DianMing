package com.zyr.student.net.util;

import android.content.Context;

import com.zyr.student.net.retrofit.Networks;

/**
 * Created by X.Sation on 2017/5/2.
 */

public class UrlUtil {
    private static String serverAddress = "";
    private static String ip = "";

    private static UrlUtil instance = new UrlUtil();
    private Context context;

    private UrlUtil() {

    }

    public static UrlUtil getInstance() {
        return instance;
    }

    public void init(Context context) {
        this.context = context;
        updateIp();
    }

    public String getUrl() {
        StringBuffer sb = new StringBuffer();
        if (serverAddress==null||serverAddress.isEmpty()) {
            serverAddress="localhost";
        }
        sb.append("http://").append(serverAddress).append(":8080/");
        return sb.toString();
    }

    /**
     * wifi 状态改变时更新ip，并且要重新初始化retrofit
     */
    public void updateIp() {
        if (serverAddress != NetUtil.getserverAddress(context)) {
            serverAddress=NetUtil.getIPAddress(context);
            Networks.getInstance().initRetrofit(getUrl());
        }
    }

}
