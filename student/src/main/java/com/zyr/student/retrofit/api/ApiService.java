package com.zyr.student.retrofit.api;


import com.zyr.bean.Student;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface ApiService {
//    /**
//     * 获取启动界面图像
//     * @return
//     */
//    @GET("start-image/1080*1776")
//    Observable<SplashImgEntity> getSplashImg();
//
//    /**
//     * 获取android最新版本
//     * @return
//     */
//    @GET("version/android/{versionCode}")
//    Observable<SplashImgEntity> getLatestVersion(@Path("versionCode") String versionCode);
//
//
    /**
     * 获取最新文章列表
     * @return
     */
    @GET("news/latest")
    Observable<Student> getLatestDaily();


    /**
     * 获取学生
     * @return
     */
    @FormUrlEncoded
    @POST("queryStudent")
    Observable<Student> query(@Field("name") String name);
//
//    /**
//     * 获取以前的文章列表
//     * @return
//     */
//    @GET("news/before/{date}")
//    Observable<BeforeDailyEntity> getBeforeDaily(@Path("date") String date);
//
//    /**
//     * 获取相应文章内容
//     * @param storyId
//     * @return
//     */
//    @GET("news/{storyId}")
//    Observable<StoryContentEntity> getStoryContent(@Path("storyId") int storyId);
//
//    /**
//     * 获取相应文章的额外信息，如评论数量，获得的赞等
//     * @param storyId
//     * @return
//     */
//    @GET("story-extra/{storyId}")
//    Observable<StoryExtraEntity> getStoryExtra(@Path("storyId") int storyId);
}
