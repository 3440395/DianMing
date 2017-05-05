package com.zyr.common;

import java.text.SimpleDateFormat;


/**
 * 各种常量
 */
public class Constant {

    public static String[] tab_names_teacher = {"学生管理", "课程管理", "个人信息"};
    public static String[] tab_names_student = {"我的老师", "我的课程", "个人信息"};

    public static final SimpleDateFormat shortDayFormat = new SimpleDateFormat("MM-dd");
    public static final SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat secondFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    public static String ip = "localhost";


    //本机
    public static String baseurl = "http://" + ip + ":8080/";
//    public static final String baseurl = "http://192.168.1.116:8080/";
//    public static final String baseurl = "http://10.88.4.1:8080/";

    public static String url_test = baseurl + "test";
    public static String url_autologin = baseurl + "AutoLoginServlet";
    public static String url_register = baseurl + "RigisterServlet";
    public static String url_logout = baseurl + "LogoutServlet";
    public static String url_truck = baseurl + "TruckServlet";
    public static String url_trucksource = baseurl + "TruckSourceServlet";
    public static String url_upload_head = baseurl + "UploadHeadServlet";
    public static String url_update_userinfo = baseurl + "UpdateUserinfoServlet";
    public static String url_my_friend = baseurl + "FriendServlet";
//    public static String url_base_headimg = baseurl_address + "imghead/";


    //请求的what
    public static final int request_what_login = 0;
    public static final int reuqest_what_logout = 1;
    public static final int reuqest_what_autologin = 2;
    public static final int reuqest_what_register = 3;
    public static final int reuqest_what_addtruck = 4;
    public static final int reuqest_what_queryalltruck = 5;
    public static final int reuqest_what_addtrucksource = 6;
    public static final int reuqest_what_queryalltrucksource = 7;


    //sp key
    //程序当前的角色 0老师 1学生 -1需要选择
    public static final String SP_KEY_CURRENT_ROLE = "sp_key_current_role";


}
