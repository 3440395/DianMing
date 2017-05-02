package com.zyr.teacher.handler;

import android.util.Log;

import com.yanzhenjie.andserver.RequestHandler;
import com.zyr.common.net.HttpRequestParser;
import com.zyr.teacher.Helper;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

/**
 * Created by xuekai on 2017/5/2.
 */

public class TestHandler implements RequestHandler {
    private static final String TAG = "TestHandler";

    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
        Map<String, String> params = HttpRequestParser.parse(request);
        String value="未设置";
        for (Map.Entry<String, String> stringStringEntry : params.entrySet()) {
            Log.e(TAG, "Key:" + stringStringEntry.getKey() + "  value:" + stringStringEntry.getValue());
            value=stringStringEntry.getValue();
        }
        Helper.getInstance().showToast(value);
        try {
            JSONObject jsonObject = new JSONObject("{msg:呵呵}");
            StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");
            response.setEntity(stringEntity);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
