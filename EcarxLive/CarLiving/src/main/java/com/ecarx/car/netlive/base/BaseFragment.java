package com.ecarx.car.netlive.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ecarx.car.netlive.api.ApiWrapper;
import com.ecarx.car.netlive.api.SimpleMyCallBack;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.DisposableSubscriber;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by anzhuo002 on 2016/7/5.
 */

public abstract class BaseFragment<T extends BasePresenter> extends SupportFragment implements View.OnClickListener {
    public BaseActivity mContext;
    public View mContentView = null;
    /**
     * 使用CompositeSubscription来持有所有的Subscriptions
     */
    public CompositeDisposable mCompositeSubscription;
    /**
     * Api类的包装 对象
     */
    public ApiWrapper mApiWrapper;

    public T presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getBaseActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(onSetLayoutId(), container, false);
            ButterKnife.bind(mContentView);
            findView();
            initView(mContentView, savedInstanceState);
            bindEvent();
            loadData();
        }
        return mContentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解绑 presenter
        if (presenter != null) {
            presenter.unsubscribe();
        }
    }

    /**
     * 创建相应的 presenter
     */
    public void createPresenter(T presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }

    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) this.getActivity();
    }

    /**
     * 初始化 Api  更具需要初始化
     */
    public void initApi() {
        mCompositeSubscription = mContext.getCompositeSubscription();
        mApiWrapper = mContext.getApiWrapper();
    }

    /**
     * 设置布局文件
     *
     * @return 返回布局文件资源Id
     */
    public abstract int onSetLayoutId();

    /**
     * findViewById（）
     */
    protected void findView() {
    }

    /**
     * 加载数据请求
     */
    protected void loadData() {
    }

    public abstract void initView(View mContentView, Bundle savedInstanceState);

    public abstract void bindEvent();

    public <T> DisposableSubscriber newMySubscriber(final SimpleMyCallBack onNext) {
        return mContext.newMySubscriber(onNext);
    }

    public void showToast(String content) {
        mContext.showToast(content);
    }

    public void showLoadingDialog() {
        mContext.showLoadingDialog();
    }

    public void hideLoadingDialog() {
        mContext.hideLoadingDialog();
    }

    public void openActivity(Class clazz) {
        mContext.openActivity(clazz);
    }

    public void openActivity(Class clazz, Bundle bundle) {
        mContext.openActivity(clazz, bundle);
    }

    public void openActivity(Class clazz, Bundle bundle, int flags) {
        mContext.openActivity(clazz, bundle, flags);
    }

}
