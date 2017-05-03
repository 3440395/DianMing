package com.zyr.common;

import android.app.Application;
import android.os.Handler;

import com.zyr.util.UrlUtil;


public class App extends Application {
    private Thread mUiThread = Thread.currentThread();
    private Handler handler = new Handler();

    private static App mApp;

    public static App getInstance() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
//        NoHttp.initialize(this);
//        Logger.setTag("NoHttp");
//        Logger.setDebug(true);// 开始NoHttp的调试模式, 这样就能看到请求过程和日志

        UrlUtil.getInstance().init(this);

    }


    public void mRunOnUiThread(Runnable runnable) {
        if (Thread.currentThread() != mUiThread) {
            handler.post(runnable);
        } else {
            runnable.run();
        }
    }
}
