package com.zyr.teacher;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by xuekai on 2017/5/2.
 */

public class TestActivity extends Activity {
    private static final String TAG = "TestActivity";
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.getInstance().init(this);
        textView = new TextView(this);
        setContentView(textView);

        textView.setText("moren");
    }

    public void setTextView(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(s);
                Log.e(TAG,"设置了");
            }
        });
    }
}
