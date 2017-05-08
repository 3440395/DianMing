package com.zyr.student.net.retrofit;


import com.zyr.student.net.retrofit.api.ApiService;
import com.zyr.student.net.util.UrlUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yiyi on 2016/12/27.
 */

public class Networks {
    private String TAG = "Networks";

    private int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private ApiService apiService;
    private static Networks networks;
    private  OkHttpClient httpClientBuilder;


    //构造方法私有
    private Networks() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClientBuilder = new OkHttpClient
                .Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();


        initRetrofit(UrlUtil.getInstance().getUrl());
    }

    /**
     * baseurl改变后需要重新初始化retrofit
     */
    public void initRetrofit(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public static Networks getInstance() {
        if (networks == null) {
            networks = new Networks();
        }
        return networks;
    }

    public ApiService getApiService() {
        return apiService;
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
