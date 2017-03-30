package com.ecarx.car.netlive.demo;

import com.ecarx.car.netlive.base.BasePresenter;
import com.ecarx.car.netlive.base.BaseView;

/**
 * Created by baixiaokang on 16/4/29.
 */
public interface LoginContract {
    interface View extends BaseView {
        void loginSuccess();
    }

    interface Presenter extends BasePresenter {
        void login();
    }
}
