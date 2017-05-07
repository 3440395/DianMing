package com.zyr.entity;

/**
 * Created by xuekai on 2017/5/6.
 */

public class BaseEntity<T> {
    //0失败 1成功
    private int resultCode;
    private T data;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "resultCode=" + resultCode +
                ", data=" + data +
                '}';
    }
}
