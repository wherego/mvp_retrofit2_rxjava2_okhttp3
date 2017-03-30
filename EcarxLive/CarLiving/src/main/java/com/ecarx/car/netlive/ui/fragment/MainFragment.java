package com.ecarx.car.netlive.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.ecarx.car.netlive.R;
import com.ecarx.car.netlive.base.BaseFragment;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 说明：左侧菜单采用fragment中加载recyclerview
 *       是考虑到后期模块或功能的变动，为了减少对源代码的改动和方便扩展
 *
 * Created by YoKeyword on 16/2/4.
 */
public class MainFragment extends BaseFragment {
    public static final String TAG = MainFragment.class.getSimpleName();

    public static MainFragment newInstance() {
        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.main_fragment;
    }

    @Override
    public void initView(View mContentView, Bundle savedInstanceState) {

        ArrayList<String> listMenus = new ArrayList<>();
        listMenus.add(getString(R.string.main_menu_live));
        listMenus.add(getString(R.string.main_menu_music));
        listMenus.add(getString(R.string.main_menu_history));
        listMenus.add(getString(R.string.main_menu_setting));

        MenuListFragment menuListFragment = MenuListFragment.newInstance(listMenus);
        loadRootFragment(R.id.fl_list_menubar, menuListFragment);
        replaceLoadRootFragment(R.id.fl_content_container, ContentFragment.newInstance(getString(R.string.main_menu_live)), false);

    }

    @Override
    public void bindEvent() {

    }

    @Override
    public boolean onBackPressedSupport() {
        // ContentFragment是ShopFragment的栈顶子Fragment,会先调用ContentFragment的onBackPressedSupport方法
        return false;
    }

    /**
     * 替换加载 内容Fragment
     *
     * @param fragment
     */
    public void switchContentFragment(ContentFragment fragment) {
        SupportFragment contentFragment = findChildFragment(ContentFragment.class);
        if (contentFragment != null) {
            contentFragment.replaceFragment(fragment, false);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
