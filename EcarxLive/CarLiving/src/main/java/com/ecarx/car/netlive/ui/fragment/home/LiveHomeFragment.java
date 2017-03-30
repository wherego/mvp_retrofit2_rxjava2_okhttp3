package com.ecarx.car.netlive.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ecarx.car.netlive.R;
import com.ecarx.car.netlive.base.BaseMainFragment;
import com.ecarx.car.netlive.ui.fragment.LiveFragment;

/**
 * Created by ecarx on 2017/3/28.
 */

public class LiveHomeFragment extends BaseMainFragment {
    public static LiveHomeFragment newInstance() {
        Bundle args = new Bundle();
        LiveHomeFragment fragment = new LiveHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.frag_home_live;
    }

    @Override
    public void initView(View mContentView, Bundle savedInstanceState) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_live_container, LiveFragment.newInstance());
        }
    }
}
