package com.zyr.subscirber;

/**
 * Created by X.Sation on 2017/5/3.
 */

public interface SubscriberListener<T> {
    void onNext(T t);
}
