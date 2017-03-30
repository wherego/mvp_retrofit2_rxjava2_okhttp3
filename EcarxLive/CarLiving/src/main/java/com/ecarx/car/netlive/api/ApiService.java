package com.ecarx.car.netlive.api;


import com.ecarx.car.netlive.demo.Login;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by Sunflower on 2015/11/4.
 */
public interface ApiService {


    /**
     * 获取个人信息
     */
    @FormUrlEncoded
    @POST("test")
    Flowable<HttpResult<Login>> getPersonalInfo(@Field("callback") String callback);
}
