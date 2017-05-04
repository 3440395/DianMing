package com.zyr.teacher.handler;

import android.util.Log;

import com.google.gson.Gson;
import com.yanzhenjie.andserver.RequestHandler;
import com.zyr.bean.Student;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.util.Map;

/**
 * 查询所有学生
 * Created by xuekai on 2017/5/2.
 */

public class StudentHandler implements RequestHandler {
    private static final String TAG = "StudentHandler";

    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
        Map<String, String> parse = RequestParser.parse(request);
        String name = parse.get("name");

        Student student = new Student();
        student.setName(name);
        Log.e(TAG,"handle"+name);
        Gson gson = new Gson();
        String json = gson.toJson(student);
        StringEntity stringEntity = new StringEntity(json, "utf-8");
        response.setEntity(stringEntity);
    }
}
