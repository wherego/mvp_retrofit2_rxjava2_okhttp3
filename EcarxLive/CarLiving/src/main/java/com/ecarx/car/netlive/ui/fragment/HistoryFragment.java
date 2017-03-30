package com.ecarx.car.netlive.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.ecarx.car.netlive.R;
import com.ecarx.car.netlive.base.BaseFragment;

/**
 * Created by ecarx on 2017/3/28.
 */

public class HistoryFragment extends BaseFragment {
    public static HistoryFragment newInstance() {
        Bundle args = new Bundle();
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.frag_history;
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
