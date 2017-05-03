package com.zyr.util;

import android.content.Context;
import android.support.annotation.NonNull;


public class ViewUtils {

    public static int dip2px(@NonNull Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
