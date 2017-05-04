package com.zyr.student.retrofit;


import com.zyr.student.retrofit.api.ApiService;

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
    private String BASE_URL = "http://localhost:8080/";

    private int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private ApiService apiService;
    private static Networks networks;

    //构造方法私有
    private Networks() {


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClientBuilder = new OkHttpClient
                .Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .addInterceptor(logging)
                .build();


//        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
//                .create();//使用 gson coverter，统一日期请求格式


        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
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
