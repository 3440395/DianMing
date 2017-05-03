package com.zyr.student.retrofit;


import com.zyr.student.retrofit.api.ApiService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yiyi on 2016/12/27.
 */

public class Networks {

    public static final String BASE_URL = "http://news-at.zhihu.com/api/4/";

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private ApiService apiService;

    //构造方法私有
    private Networks() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient
                .Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public static ApiService getApiService() {
        return SingletonHolder.INSTANCE.apiService;
    }

    private static class SingletonHolder {
        private static final Networks INSTANCE = new Networks();
    }


//    /**
//     * 用于获取豆瓣电影Top250的数据
//     *
//     * @param subscriber 由调用者传过来的观察者对象
//     * @param start      起始位置
//     * @param count      获取长度
//     */
//    public void getTopMovie(Subscriber<MovieEntity> subscriber, int start, int count) {
//        movieService.getTopMovie(start, count)
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
//    }

}
