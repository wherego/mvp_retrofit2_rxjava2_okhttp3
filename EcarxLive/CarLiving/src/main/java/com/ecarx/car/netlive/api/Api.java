package com.ecarx.car.netlive.api;

import com.ecarx.car.netlive.base.CarLiveApplication;

import org.reactivestreams.Publisher;

import java.io.File;
import java.io.IOException;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Sunflower on 2015/11/4.
 */
public class Api {

    /**
     * 服务器地址
     */
    // 请求公共部分
    private static final String BASE_URL = "http://wx.xchanger.cn";

    // 消息头
    private static final String HEADER_X_HB_Client_Type = "X-HB-Client-Type";
    private static final String FROM_ANDROID = "ayb-android";

    private static ApiService service;
    private static Retrofit retrofit;

    public static ApiService getService() {
        if (service == null) {
            service = getRetrofit().create(ApiService.class);
        }
        return service;
    }

    /**
     * 拦截器  给所有的请求添加消息头
     */
    private static Interceptor mInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
//                    .addHeader(HEADER_X_HB_Client_Type, FROM_ANDROID)
                    .build();
            return chain.proceed(request);
        }
    };

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            // log拦截器  打印所有的log
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置 请求的缓存
            File cacheFile = new File(CarLiveApplication.getInstance().getCacheDir(), "cache");
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 50); //50Mb

            OkHttpClient client = new OkHttpClient.Builder()
//                    .connectTimeout(15, TimeUnit.SECONDS)
//                    .addInterceptor(interceptor)
//                    .addInterceptor(mInterceptor)
                    .cache(cache)
                    .build();

            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * 对 Flowable<T> 做统一的处理，处理了线程调度、分割返回结果等操作组合了起来
     *
     * @param responseFlowable
     * @param <T>
     * @return
     */
    protected <T> Flowable<T> applySchedulers(Flowable<HttpResult<T>> responseFlowable) {
        return responseFlowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<HttpResult<T>, Publisher<T>>() {
                    @Override
                    public Publisher<T> apply(@NonNull HttpResult<T> tHttpResult) throws Exception {
                        return flatResponse(tHttpResult);
                    }
                });
    }

    /**
     * 对网络接口返回的Response进行分割操作 对于jasn 解析错误以及返回的 响应实体为空的情况
     *
     * @param response
     * @return
     */
    public <T> Flowable<T> flatResponse(final HttpResult<T> response) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<T> subscriber) throws Exception {
                if (response != null) {
                    if (!subscriber.isCancelled()) {
                        if (response.getCode() == 200)
                            subscriber.onNext(response.getData());
                        else
                            subscriber.onError(new APIException(response.getCode() + "", response.getMsg()));
                    }
                } else {
                    if (!subscriber.isCancelled()) {
                        subscriber.onError(new APIException("自定义异常类型", "解析json错误或者服务器返回空的json"));
                    }
                    return;
                }
                if (!subscriber.isCancelled()) {
                    if (response.getCode() == 200)
                        subscriber.onComplete();
                }
            }
        }, BackpressureStrategy.BUFFER);
    }

    /**
     *
     */
    public static class APIException extends Exception {
        public String code;
        public String message;

        public APIException(String code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }

    }
}
