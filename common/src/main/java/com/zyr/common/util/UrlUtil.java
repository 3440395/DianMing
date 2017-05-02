package com.zyr.common.util;

import android.content.Context;

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

    public static String getUrl(String path) {
        StringBuffer sb = new StringBuffer();
        sb.append("http://").append(serverAddress).append(":8080/").append(path);
        return sb.toString();
    }

    public void updateIp() {
        serverAddress = NetUtil.getserverAddress(context);
    }

}
