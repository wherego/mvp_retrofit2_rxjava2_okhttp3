package com.ecarx.car.netlive.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ecarx.car.netlive.R;
import com.ecarx.car.netlive.base.BaseMainFragment;
import com.ecarx.car.netlive.ui.fragment.MusicFragment;

/**
 * Created by ecarx on 2017/3/28.
 */

public class MusicHomeFragment extends BaseMainFragment {
    public static MusicHomeFragment newInstance() {
        Bundle args = new Bundle();
        MusicHomeFragment fragment = new MusicHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.frag_home_music;
    }

    @Override
    public void initView(View mContentView, Bundle savedInstanceState) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_music_container, MusicFragment.newInstance());
        }
    }
}
