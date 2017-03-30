package com.ecarx.car.netlive.api;


import com.ecarx.car.netlive.demo.Login;

import java.util.Map;

import io.reactivex.Flowable;

/**
 * Api类的包装
 */
public class ApiWrapper extends Api {

    public Flowable<Login> getUerInfo(Map<String, String> params) {
        Flowable<HttpResult<Login>> datas = getService().getPersonalInfo("{ \"code\": 200,\"msg\": \"登录失败\",\"data\": { \"userName\": \"宗士为\",\"img\": \"http://afdadfadfijadfoaijfdp\"}}");
        return applySchedulers(datas);
    }
}
