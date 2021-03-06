package com.zyr.teacher;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.Server;
import com.yanzhenjie.andserver.website.AssetsWebsite;
import com.zyr.teacher.server.handler.CoreHandler;

/**
 * Created by xuekai on 2017/5/2.
 */

public class CoreService extends Service {

    private Server mServer;
    private AssetManager mAssetManager;

    @Override
    public void onCreate() {
        mAssetManager = getAssets();
        AndServer andServer = new AndServer.Build()
                .port(8080)
                .timeout(10 * 1000)
                .registerHandler("core", new CoreHandler())
                .website(new AssetsWebsite(mAssetManager, ""))
                .listener(mListener)
                .build();

        mServer = andServer.createServer();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * CoreService listener.
     */
    private Server.Listener mListener = new Server.Listener() {
        @Override
        public void onStarted() {
            Toast.makeText(CoreService.this, "服务端已启动", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStopped() {
            Toast.makeText(CoreService.this, "服务端已停止", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(Exception e) {
            Toast.makeText(CoreService.this, "服务端已启动", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startServer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopServer(); // Stop server.
        if (mAssetManager != null)
            mAssetManager.close();
    }

    /**
     * Start server.
     */
    private void startServer() {
        if (mServer != null) {
            if (!mServer.isRunning()) mServer.start();
        }
    }

    /**
     * Stop server.
     */

    private void stopServer() {
        if (mServer != null) {
            mServer.stop();
        }
    }


}


