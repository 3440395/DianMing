package com.zyr.student.net;

import android.content.Context;
import android.widget.Toast;

import com.zyr.student.net.util.NetUtil;
import com.zyr.student.net.util.UrlUtil;
import com.zyr.subscirber.ProgressCancelListener;
import com.zyr.subscirber.ProgressDialogHandler;

import rx.Subscriber;

/**
 * Created by X.Sation on 2017/5/3.
 */

public abstract class ProgressSubscriber<T> extends com.zyr.subscirber.ProgressSubscriber<T> {


    public ProgressSubscriber(Context context, boolean canCancel) {
        super(context, canCancel);
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        Toast.makeText(context,"网络错误，请连接教师端wifi热点再试",Toast.LENGTH_SHORT).show();
    }
}
