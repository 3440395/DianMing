package com.zyr.student.retrofit.subscirber;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by X.Sation on 2017/5/3.
 */

public interface SubscriberListener<T> {
    void onNext(T t);
}
