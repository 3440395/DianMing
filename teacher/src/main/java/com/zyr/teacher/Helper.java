package com.zyr.teacher;

import android.content.Context;

/**
 * 服务端接收到消息后希望在这里做处理
 * Created by xuekai on 2017/5/2.
 */

public class Helper {
    private static Helper instance = new Helper();
    private Context context;
    private Helper(){
    }

    public static Helper getInstance(){
        return instance;
    }

    public void init(Context context){
        this.context = context;
    }

    public void showToast(String content){
    }
}
