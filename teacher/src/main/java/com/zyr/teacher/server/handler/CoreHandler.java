package com.zyr.teacher.server.handler;


import com.yanzhenjie.andserver.RequestHandler;
import com.zyr.common.App;
import com.zyr.teacher.Helper;
import com.zyr.teacher.server.RequestParser;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.util.Map;

/**
 * Created by xuekai on 2017/5/2.
 */

public class CoreHandler implements RequestHandler {

    private Helper helper;
    private String responseJson;

    public CoreHandler() {
        helper = new Helper(App.getInstance().getApplicationContext());
    }

    private static final String TAG = "CoreHandler";

    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
        Map<String, String> parse = RequestParser.parse(request);
        String action = parse.get("action");
        responseJson=helper.handOut(action,parse);
        StringEntity stringEntity = new StringEntity(responseJson, "utf-8");
        response.setEntity(stringEntity);
    }
}
