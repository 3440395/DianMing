package com.zyr.student.retrofit.subscirber;

import android.content.Context;

import rx.Subscriber;

/**
 * Created by X.Sation on 2017/5/3.
 */

public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    private SubscriberListener subscriberListener;
    private ProgressDialogHandler mProgressDialogHandler;


    public ProgressSubscriber(Context context,SubscriberListener<T> subscriberListener) {
        this.subscriberListener = subscriberListener;
        mProgressDialogHandler = new ProgressDialogHandler(context,this, true);
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
        subscriberListener.onError(e);
    }

    @Override
    public void onNext(T t) {
        subscriberListener.onNext(t);
    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}
