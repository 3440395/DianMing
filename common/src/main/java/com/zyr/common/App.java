package com.zyr.common;

import android.app.Application;
import android.os.Handler;


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

    }

    public void initSomething(){

    }

    public void mRunOnUiThread(Runnable runnable) {
        if (Thread.currentThread() != mUiThread) {
            handler.post(runnable);
        } else {
            runnable.run();
        }
    }

}
