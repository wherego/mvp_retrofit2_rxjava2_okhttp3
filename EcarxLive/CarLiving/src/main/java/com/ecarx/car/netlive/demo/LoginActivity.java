package com.ecarx.car.netlive.demo;

import android.os.Bundle;
import android.view.View;

import com.ecarx.car.netlive.R;
import com.ecarx.car.netlive.base.BaseActivity;

/**
 * Created by Administrator on 2016/1/14.
 */
public class LoginActivity extends BaseActivity {

    @Override
    protected int onSetLayoutId() {
        return R.layout.act_test;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        // 发送请求在Acivityt中，不需要采用Acivity+frament的情况------情况1
        //初始化 api
        // initApi();


        // Acivity+frament 发送请求在fragment中，不需要采用mvp 模式的，需要始化api  ------情况2
//        initApi();
//        LoginFragment mLoginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
//        if (mLoginFragment == null) {
//            mLoginFragment = LoginFragment.newInstance();
//            addFragmentToActivity(mLoginFragment, R.id.contentFrame);
//        }


        //采用mvp 模式 在这里添加fragment 到activity 另外 创建 Presenter 不需要初始化api  ------情况3
        LoginFragment mLoginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (mLoginFragment == null) {
            mLoginFragment = LoginFragment.newInstance();
            addFragmentToActivity(mLoginFragment, R.id.contentFrame);
        }
        //创建 Presenter 如果不采用mvp 就不要创建
        mLoginFragment.createPresenter(new LoginPresenter(mLoginFragment));


//        //采用mvp 模式 只有acitivity要初始化api  ------情况4
//         initApi();
//        //创建 Presenter 如果不采用mvp 就不要创建
//        this.createPresenter(new LoginPresenter(this));   // 这里需要 loginacitivity 实现implements LoginContract.View  我这里没做大家自行练习
    }

    @Override
    public void bindEvent() {

    }

    @Override
    public void onClick(View view) {

    }
}
