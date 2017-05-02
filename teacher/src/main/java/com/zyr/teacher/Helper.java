package com.zyr.teacher;

import android.content.Context;

/**
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
        ((TestActivity)context).setTextView(content);
    }
}
