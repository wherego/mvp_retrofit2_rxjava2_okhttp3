package com.ecarx.car.netlive.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ecarx.car.netlive.R;
import com.ecarx.car.netlive.base.BaseFragment;



/**
 * Created by anzhuo002 on 2016/7/5.
 */

public class LoginFragment extends BaseFragment<LoginContract.Presenter> implements LoginContract.View {

    private Button mButton;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.frg_test;
    }

    @Override
    public void initView(View mContentView, Bundle savedInstanceState) {
        mButton = (Button) this.mContentView.findViewById(R.id.denglu);
    }

    @Override
    public void bindEvent() {
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.denglu:
                presenter.login();
                break;
        }
    }

    @Override
    public void loginSuccess() {
        showToast("LoginFragment-----loginSuccess");
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

}
