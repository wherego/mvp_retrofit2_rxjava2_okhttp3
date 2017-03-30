package com.ecarx.car.netlive.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.ecarx.car.netlive.R;
import com.ecarx.car.netlive.base.BaseFragment;
import com.ecarx.car.netlive.base.BaseMainFragment;

/**
 * Created by ecarx on 2017/3/28.
 */

public class MineFragment extends BaseFragment {

    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.frag_mine;
    }

    @Override
    public void initView(View mContentView, Bundle savedInstanceState) {

    }

    @Override
    public void bindEvent() {

    }

    @Override
    public void onClick(View v) {

    }
}
