package com.whamu2.wanandroid.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v4.app.Fragment;
import android.support.v4.app.SupportActivity;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.whamu2.wanandroid.mvp.contract.UserContract;
import com.whamu2.wanandroid.mvp.model.bean.BaseResp;
import com.whamu2.wanandroid.mvp.model.bean.User;
import com.whamu2.wanandroid.utils.observer.Callback;
import com.whamu2.wanandroid.utils.observer.CommonSubscriber;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class UserPresenter extends BasePresenter<UserContract.Model, UserContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;
    @Inject
    Application mApplication;

    @Inject
    public UserPresenter(UserContract.Model model, UserContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 使用 2017 Google IO 发布的 Architecture Components 中的 Lifecycles 的新特性 (此特性已被加入 Support library)
     * 使 {@code Presenter} 可以与 {@link SupportActivity} 和 {@link Fragment} 的部分生命周期绑定
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {

    }

    /**
     * 注册
     *
     * @param username   用户名
     * @param password   用户密码
     * @param repassword 用户密码
     */
    public void register(String username, String password, String repassword) {
        mModel.register(username, password, repassword)
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResp<User>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResp<User> resp) {
                        if (resp.isSuccess()) {
                            mRootView.onRegisterComplete(resp.getData());
                        } else {
                            mRootView.showMessage(resp.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 去登录
     *
     * @param username 用户名
     * @param password 用户密码
     */
    public void goLogin(final String username, final String password) {
        mModel.login(username, password)
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new CommonSubscriber<>(mErrorHandler, new Callback<BaseResp<User>>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        mRootView.showLoading();
                    }

                    @Override
                    public void onCompleted() {
                        mRootView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                    }

                    @Override
                    public void onNext(BaseResp<User> resp) {
                        if (resp.isSuccess()) {
                            mRootView.onLoginComplete(resp.getData());
                        } else {
                            mRootView.showMessage(resp.getErrorMsg());
                        }
                    }
                }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
    }
}
