package com.zyr.subscirber;

import android.content.Context;
import android.widget.Toast;

import com.zyr.subscirber.ProgressCancelListener;
import com.zyr.subscirber.ProgressDialogHandler;

import rx.Subscriber;

/**
 * Created by X.Sation on 2017/5/3.
 */

public abstract class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    private ProgressDialogHandler mProgressDialogHandler;
    protected Context context;

    public ProgressSubscriber(Context context, boolean canCancel) {
        this.context = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, canCancel);
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onStart() {
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        e.printStackTrace();
    }

    @Override
    public abstract void onNext(T t);

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}
