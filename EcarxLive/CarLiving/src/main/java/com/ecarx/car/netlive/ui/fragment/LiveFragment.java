package com.ecarx.car.netlive.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ecarx.car.netlive.R;
import com.ecarx.car.netlive.base.BaseFragment;
import com.ecarx.car.netlive.base.BaseMainFragment;

/**
 * Created by ecarx on 2017/3/28.
 */

public class LiveFragment extends BaseFragment {
    public static LiveFragment newInstance() {
        Bundle args = new Bundle();
        LiveFragment fragment = new LiveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.frag_live;
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
