package com.ecarx.car.netlive.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ecarx.car.netlive.R;
import com.ecarx.car.netlive.base.BaseActivity;
import com.ecarx.car.netlive.base.BaseMainFragment;
import com.ecarx.car.netlive.custom.LeftMenuBar;
import com.ecarx.car.netlive.custom.LeftMenuBarTab;
import com.ecarx.car.netlive.ui.fragment.HistoryFragment;
import com.ecarx.car.netlive.ui.fragment.LiveFragment;
import com.ecarx.car.netlive.ui.fragment.MineFragment;
import com.ecarx.car.netlive.ui.fragment.MusicFragment;
import com.ecarx.car.netlive.ui.fragment.home.HistoryHomeFragment;
import com.ecarx.car.netlive.ui.fragment.home.LiveHomeFragment;
import com.ecarx.car.netlive.ui.fragment.home.MimeHomeFragment;
import com.ecarx.car.netlive.ui.fragment.home.MusicHomeFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation.helper.FragmentLifecycleCallbacks;

/**
 * 因项目架构采用的是1个activity和多模块fragment（方便后期扩展），那么就会出现fragmnet相关的bug
 * 比如：1、Fragment重叠异常   2、Fragment嵌套bug   3、多个Fragment同时出栈  4、 Fragment转场动画等bug
 * 因项目的时间周期问题，采用的是开源fragmentation来集成解决，如后期有需要可以自己解决
 * <p>
 * Created by shiwei.zong on 2017/3/28.
 */

public class MainActivity extends BaseActivity implements BaseMainFragment.OnBackToFirstListener {
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;
    @Bind(R.id.bottomBar)
    LeftMenuBar mLeftBar;

    private SupportFragment[] mFragments = new SupportFragment[4];

    @Override
    protected int onSetLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void findView() {
        super.findView();

        mLeftBar.addItem(new LeftMenuBarTab(this, R.drawable.ic_home_white_24dp, "直播"))
                .addItem(new LeftMenuBarTab(this, R.drawable.ic_discover_white_24dp, "音乐"))
                .addItem(new LeftMenuBarTab(this, R.drawable.ic_message_white_24dp, "历史"))
                .addItem(new LeftMenuBarTab(this, R.drawable.ic_account_circle_white_24dp, "设置"));

        mLeftBar.setOnTabSelectedListener(mOnTabSelectedListener);
    }

    public void initView(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mFragments[FIRST] = LiveHomeFragment.newInstance();
            mFragments[SECOND] = MusicHomeFragment.newInstance();
            mFragments[THIRD] = HistoryHomeFragment.newInstance();
            mFragments[FOURTH] = MimeHomeFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findFragment(LiveHomeFragment.class);
            mFragments[SECOND] = findFragment(MusicHomeFragment.class);
            mFragments[THIRD] = findFragment(HistoryHomeFragment.class);
            mFragments[FOURTH] = findFragment(MimeHomeFragment.class);
        }

        // 可以监听该Activity下的所有Fragment的18个 生命周期方法
        registerFragmentLifecycleCallbacks(new FragmentLifecycleCallbacks() {

            @Override
            public void onFragmentSupportVisible(SupportFragment fragment) {
                Log.i("MainActivity", "onFragmentSupportVisible--->" + fragment.getClass().getSimpleName());
            }
        });


    }

    LeftMenuBar.OnTabSelectedListener mOnTabSelectedListener = new LeftMenuBar.OnTabSelectedListener() {
        @Override
        public void onTabSelected(int position, int prePosition) {
            showHideFragment(mFragments[position], mFragments[prePosition]);
        }

        @Override
        public void onTabUnselected(int position) {

        }

        @Override
        public void onTabReselected(int position) {
            SupportFragment currentFragment = mFragments[position];
            int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();

            // 如果不在该类别Fragment的主页,则回到主页;
            if (count > 1) {
                if (currentFragment instanceof LiveHomeFragment) {
                    currentFragment.popToChild(LiveFragment.class, false);
                } else if (currentFragment instanceof MusicHomeFragment) {
                    currentFragment.popToChild(MusicFragment.class, false);
                } else if (currentFragment instanceof HistoryHomeFragment) {
                    currentFragment.popToChild(HistoryFragment.class, false);
                } else if (currentFragment instanceof MimeHomeFragment) {
                    currentFragment.popToChild(MineFragment.class, false);
                }
                return;
            }


            // 这里推荐使用EventBus来实现 -> 解耦
            if (count == 1) {
                // 在FirstPagerFragment中接收, 因为是嵌套的孙子Fragment 所以用EventBus比较方便
                // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                //EventBus.getDefault().post(new TabSelectedEvent(position));
            }
        }
    };


    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        return super.onCreateFragmentAnimator();
    }


    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public void onBackToFirstFragment() {
        mLeftBar.setCurrentItem(0);
    }

    /**
     * 这里暂没实现,忽略
     */
//    @Subscribe
//    public void onHiddenBottombarEvent(boolean hidden) {
//        if (hidden) {
//            mLeftBar.hide();
//        } else {
//            mLeftBar.show();
//        }
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
