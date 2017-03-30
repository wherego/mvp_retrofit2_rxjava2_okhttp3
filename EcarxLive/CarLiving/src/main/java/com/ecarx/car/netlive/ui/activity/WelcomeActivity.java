package com.ecarx.car.netlive.ui.activity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.ecarx.car.netlive.R;
import com.ecarx.car.netlive.base.BaseActivity;
import com.ecarx.car.netlive.demo.LoginActivity;
import com.ecarx.car.netlive.utils.SystemTool;

public class WelcomeActivity extends BaseActivity {

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            openActivity(MainActivity.class);
            finish();
            return false;
        }
    });

    @Override
    protected int onSetLayoutId() {
        return R.layout.act_welcome;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        initApi();
        handler.sendEmptyMessageDelayed(1, 2000);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //获取部分默认尺寸
        SystemTool.getDefaultHeight(WelcomeActivity.this);
    }

    @Override
    public void bindEvent() {

    }

    @Override
    public void onClick(View view) {

    }

}
