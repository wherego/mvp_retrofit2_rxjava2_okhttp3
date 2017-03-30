/*
    ShengDao Android Client, BaseApplication
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.ecarx.car.netlive.base;

import android.app.Application;
import android.content.Context;

import com.ecarx.car.netlive.common.ActivityPageManager;
import com.ecarx.car.netlive.utils.LogUtil;


/**
 * [系统Application类，设置全局变量以及初始化组件]
 **/
public class CarLiveApplication extends Application {
    private final String tag = CarLiveApplication.class.getSimpleName();
    private static CarLiveApplication instance;
    public static Context myContext;
    /*
     * 是否完成  整个项目
     */
    public static boolean isCompleteProject = false;
    public static final boolean ISDEBUG = false;


    public static CarLiveApplication getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        myContext = this;
        init();

    }

    /**
     * 初始化
     */
    private void init() {

        LogUtil.setDebug(!isCompleteProject);
        LogUtil.e(tag, "isDebug: " + !isCompleteProject);

//        CrashHandler.create(this);

//        JPushInterface.init(this); // 初始化JPush
//        JPushInterface.setDebugMode(true);  // 设置日志,发布时请关闭日志

    }


    /**
     * 退出应用
     */
    public void exit() {
        ActivityPageManager.getInstance().exit(this);
    }


}
