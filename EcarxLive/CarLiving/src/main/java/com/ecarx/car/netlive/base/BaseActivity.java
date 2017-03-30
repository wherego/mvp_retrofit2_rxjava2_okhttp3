package com.ecarx.car.netlive.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.Gson;
import com.ecarx.car.netlive.api.Api;
import com.ecarx.car.netlive.api.ApiWrapper;
import com.ecarx.car.netlive.api.SimpleMyCallBack;
import com.ecarx.car.netlive.common.ActivityPageManager;
import com.ecarx.car.netlive.demo.HttpExceptionBean;
import com.ecarx.car.netlive.utils.ToastUtils;
import com.ecarx.car.netlive.widget.dialog.DialogLoading;

import java.io.IOException;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.DisposableSubscriber;
import me.yokeyword.fragmentation.SupportActivity;
import okhttp3.ResponseBody;
import retrofit2.HttpException;


import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Acivity  基类
 */
public abstract class BaseActivity<T extends BasePresenter> extends SupportActivity implements View.OnClickListener {
    protected AppCompatActivity mContext;
    /**
     * 使用CompositeSubscription来持有所有的Subscriptions
     */
    protected CompositeDisposable mCompositeSubscription;
    /**
     * 加载对话框
     */
    protected DialogLoading loading;
    /**
     * 来自哪个 页面
     */
    protected String fromWhere;
    /**
     * 页面布局的 根view
     */
    protected View mContentView;
    /**
     * Api类的包装 对象
     */
    protected ApiWrapper mApiWrapper;


    public T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置不能横屏setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        mContext = this;
        //Activity管理
        ActivityPageManager.getInstance().addActivity(this);

        View view = LayoutInflater.from(this).inflate(onSetLayoutId(), null);
        setContentView(view);

        //初始化页面
        init(savedInstanceState);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        mContentView = view;

    }

    /**
     * 初始化页面
     *
     * @param savedInstanceState
     */
    public void init(Bundle savedInstanceState) {
        initFromWhere();
        ButterKnife.bind(this);
        findView();
        initView(savedInstanceState);
        loadData();
        bindEvent();
    }


    /**
     * 初始化 Api  更具需要初始化
     */
    public void initApi() {
        //创建 CompositeSubscription 对象 使用CompositeSubscription来持有所有的Subscriptions，然后在onDestroy()或者onDestroyView()里取消所有的订阅。
        mCompositeSubscription = new CompositeDisposable();
        // 构建 ApiWrapper 对象
        mApiWrapper = new ApiWrapper();
    }

    public ApiWrapper getApiWrapper() {
        if (mApiWrapper == null) {
            mApiWrapper = new ApiWrapper();
        }
        return mApiWrapper;
    }

    public CompositeDisposable getCompositeSubscription() {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeDisposable();
        }
        return mCompositeSubscription;
    }


    /**
     * 设置内容
     *
     * @return 返回xmlb布局文件（R.layout.xxx）
     */
    protected abstract int onSetLayoutId();


    /**
     * findViewById（）
     */
    protected void findView() {}

    /**
     * 初始化view
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);


    /**
     * 加载数据请求
     */
    protected void loadData() {}



    /**
     * 绑定事件
     */
    protected void bindEvent(){};


    /**
     * 创建相应的 presenter
     */
    public void createPresenter(T presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }

    }

    protected void initFromWhere() {
        if (null != getIntent().getExtras()) {
            if (getIntent().getExtras().containsKey("fromWhere")) {
                fromWhere = getIntent().getExtras().getString("fromWhere").toString();
            }
        }
    }

    public String getFromWhere() {
        return fromWhere;
    }

    /**
     * 创建观察者  这里对观察着 过滤一次，过滤出我们想要的信息，错误的信息toast
     *
     * @param onNext
     * @param <T>
     * @return
     */
    protected <T> DisposableSubscriber newMySubscriber(final SimpleMyCallBack onNext) {
        return new DisposableSubscriber<T>() {
            @Override
            public void onComplete() {
                hideLoadingDialog();
                onNext.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof Api.APIException) {
                    Api.APIException exception = (Api.APIException) e;
                    onNext.onError(exception);
                } else if (e instanceof HttpException) {
                    if (e instanceof HttpException) {
                        ResponseBody body = ((HttpException) e).response().errorBody();
                        try {
                            String json = body.string();
                            Gson gson = new Gson();
                            HttpExceptionBean mHttpExceptionBean = gson.fromJson(json, HttpExceptionBean.class);
                            if (mHttpExceptionBean != null && mHttpExceptionBean.getMessage() != null) {
                                ToastUtils.showShort(mHttpExceptionBean.getMessage());
                                onNext.onError(mHttpExceptionBean);
                            }
                        } catch (IOException IOe) {
                            IOe.printStackTrace();
                        }
                    }
                }
//                e.printStackTrace();
                hideLoadingDialog();
            }

            @Override
            public void onNext(T t) {
                if (!mCompositeSubscription.isDisposed()) {
                    onNext.onNext(t);
                }
            }

        };
    }

    /**
     * 将 Fragment添加到Acitvtiy
     *
     * @param fragment
     * @param frameId
     */
    protected void addFragmentToActivity(@NonNull Fragment fragment, int frameId) {
        checkNotNull(fragment);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }


    /**
     * 显示一个Toast信息
     */
    public void showToast(String content) {
        if (content != null) {
            ToastUtils.showShort(content);
        }
    }

    public void showLoadingDialog() {
        if (loading == null) {
            loading = new DialogLoading(this);
        }
        loading.show();
    }

    public void hideLoadingDialog() {
        if (loading != null) {
            loading.dismiss();
        }

    }

    /**
     * 跳转页面
     */
    public void openActivity(Class<?> pClass) {
        openActivity(pClass, null, -1);
    }

    public void openActivity(Class<?> pClass, Bundle pBundle) {
        openActivity(pClass, pBundle, -1);
    }

    protected void openActivity(Class<?> pClass, int requestCode) {
        openActivity(pClass, null, requestCode);
    }

    protected void openActivity(Class<?> pClass, Bundle pBundle, int requestCode) {
        Intent intent = new Intent(this, pClass);
        intent.putExtra("fromWhere", getClass().getSimpleName());
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        if (requestCode > 0)
            startActivityForResult(intent, requestCode);
        else
            startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Acitvity 释放子view资源
        ActivityPageManager.unbindReferences(mContentView);
        ActivityPageManager.getInstance().removeActivity(this);
        mContentView = null;
        //一旦调用了 CompositeSubscription.unsubscribe()，这个CompositeSubscription对象就不可用了,
        // 如果还想使用CompositeSubscription，就必须在创建一个新的对象了。
        if (mCompositeSubscription != null) {
            mCompositeSubscription.clear();
        }
        //解绑 presenter
        if (presenter != null) {
            presenter.unsubscribe();
        }
    }
}
