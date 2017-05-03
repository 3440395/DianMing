package com.zyr.teacher.handler;

import com.yanzhenjie.andserver.RequestHandler;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * 用于网络检测
 * Created by xuekai on 2017/5/2.
 */

public class NetTestHandler implements RequestHandler {
    private static final String TAG = "NetTestHandler";

    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
        StringEntity stringEntity = new StringEntity("success", "utf-8");
        response.setEntity(stringEntity);
    }
}
