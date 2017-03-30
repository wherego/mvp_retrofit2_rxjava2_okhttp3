package com.ecarx.car.netlive.api;

/**
 * 发送请求后的回调接口
 */

interface MyCallBack<T>  {
   void onCompleted();
   void onError(Exception mHttpExceptionBean);
   void onNext(T t);
}
