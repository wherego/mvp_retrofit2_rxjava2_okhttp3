package com.ecarx.car.netlive.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.ecarx.car.netlive.R;
import com.ecarx.car.netlive.base.BaseFragment;
import com.ecarx.car.netlive.base.BaseMainFragment;

/**
 * Created by ecarx on 2017/3/28.
 */

public class MusicFragment extends BaseFragment {

    public static MusicFragment newInstance() {
        Bundle args = new Bundle();
        MusicFragment fragment = new MusicFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int onSetLayoutId() {
        return R.layout.frag_music;
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
