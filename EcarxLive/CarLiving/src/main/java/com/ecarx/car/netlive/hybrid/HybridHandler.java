package com.ecarx.car.netlive.hybrid;

import com.ecarx.car.netlive.demo.WebActivity;

/**
 *  处理交互事件的 接口 类
 */
public interface HybridHandler {

    /**
     * 处理事件对象的名称
     * @return
     */
    String getHandlerName();

    /**
     *  对应的实现类 处理对应的事件任务  返回true 带表处理了， false 则是没有处理
     * @param string
     * @return
     */
    boolean handerTask(WebActivity mActivity, String string);



}
