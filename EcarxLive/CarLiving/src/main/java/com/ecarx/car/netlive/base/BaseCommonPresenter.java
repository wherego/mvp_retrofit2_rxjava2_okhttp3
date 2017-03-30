package com.ecarx.car.netlive.base;

import com.ecarx.car.netlive.api.Api;
import com.ecarx.car.netlive.api.ApiWrapper;
import com.ecarx.car.netlive.api.SimpleMyCallBack;
import com.ecarx.car.netlive.demo.HttpExceptionBean;
import com.ecarx.car.netlive.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.DisposableSubscriber;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * Created by anzhuo002 on 2016/7/6.
 */

public  class BaseCommonPresenter<T extends BaseView> {
    /**
     * Api类的包装 对象
     */
    protected ApiWrapper mApiWrapper;
    /**
     * 使用CompositeSubscription来持有所有的Subscriptions
     */
    protected CompositeDisposable mCompositeSubscription;

    public T view;

    public BaseCommonPresenter(T view) {
        //创建 CompositeSubscription 对象 使用CompositeSubscription来持有所有的Subscriptions，然后在onDestroy()或者onDestroyView()里取消所有的订阅。
        mCompositeSubscription = new CompositeDisposable();
        // 构建 ApiWrapper 对象
        mApiWrapper = new ApiWrapper();
        this.view  = view;
    }

    /**
     * 创建观察者  这里对观察着 过滤一次，过滤出我们想要的信息，错误的信息toast
     *
     * @param onNext
     * @param <E>
     * @return
     */
    protected <E> DisposableSubscriber newMySubscriber(final SimpleMyCallBack onNext) {
        return new DisposableSubscriber <E>() {
            @Override
            public void onError(Throwable e) {
                if (e instanceof Api.APIException) {
                    Api.APIException exception = (Api.APIException) e;
                    if (view != null) {
                        onNext.onError(exception);
                    }
                } else if (e instanceof HttpException) {
                    if (e instanceof HttpException) {
                        ResponseBody body = ((HttpException) e).response().errorBody();
                        try {
                            String json = body.string();
                            Gson gson = new Gson();
                            HttpExceptionBean mHttpExceptionBean = gson.fromJson(json, HttpExceptionBean.class);
                            if (mHttpExceptionBean != null && mHttpExceptionBean.getMessage() != null) {
                                ToastUtils.showShort(mHttpExceptionBean.getMessage());
                                onNext.onError(mHttpExceptionBean);
                            }
                        } catch (IOException IOe) {
                            IOe.printStackTrace();
                        }
                    }
                }
//                e.printStackTrace();
                if (view != null) {
                    view.hideLoading();
                }
            }

            @Override
            public void onComplete() {
                if (view != null) {
                    view.hideLoading();
                }
                onNext.onCompleted();
            }

            @Override
            public void onNext(E t) {
                if (!mCompositeSubscription.isDisposed()) {
                    onNext.onNext(t);
                }
            }
        };
    }


    /**
     * 解绑 CompositeSubscription
     */
    public void unsubscribe() {
        if(mCompositeSubscription != null){
            mCompositeSubscription.clear();
        }
    }

}
