package com.zyr.student.net.retrofit.api;


import com.zyr.entity.BaseEntity;
import com.zyr.entity.Course;
import com.zyr.entity.Student;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface ApiService {

    /**
     * 只需要返回true或者false
     * @return
     */
    @FormUrlEncoded
    @POST("core")
    Observable<BaseEntity> coreInterface(@FieldMap Map<String,String> fieldMap);

    /**
     *  返回BaseEntity<Student>
     * @return
     */
    @FormUrlEncoded
    @POST("core")
    Observable<BaseEntity<Student>> getStudent(@FieldMap Map<String,String> fieldMap);

    /**
     * 返回BaseEntity<List<Course>>
     * @return
     */
    @FormUrlEncoded
    @POST("core")
    Observable<BaseEntity<List<Course>>> getListCourses(@FieldMap Map<String,String> fieldMap);
}
