package com.ecarx.car.netlive.demo;

import com.ecarx.car.netlive.api.Api;
import com.ecarx.car.netlive.api.SimpleMyCallBack;
import com.ecarx.car.netlive.base.BaseCommonPresenter;
import com.ecarx.car.netlive.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

/**
 * Created by baixiaokang on 16/4/29.
 */
public class LoginPresenter extends BaseCommonPresenter<LoginContract.View> implements LoginContract.Presenter {

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }


    @Override
    public void login() {
        Map<String, String> hashMap = new HashMap<String, String>();
        Flowable<Login> observable = mApiWrapper.getUerInfo(hashMap);
        Disposable subscription = observable.subscribeWith(newMySubscriber(new SimpleMyCallBack<Login>() {
            // 这个方法根据需要重写 之前已经toast了，如果toast了还要做其他的事情，就重写这个方法
            @Override
            public void onError(Exception mHttpExceptionBean) {
                super.onError(mHttpExceptionBean);
                if (mHttpExceptionBean instanceof Api.APIException)
                    ToastUtils.showShort(((Api.APIException) mHttpExceptionBean).message);
            }

            @Override
            public void onNext(Login mLogin) {
                ToastUtils.showShort("登录成功" + mLogin.getUserName() + mLogin.getImg());
                view.loginSuccess();
            }
        }));
        mCompositeSubscription.add(subscription);
    }
}
