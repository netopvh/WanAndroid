package com.whamu2.wanandroid.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.whamu2.wanandroid.mvp.contract.WechatCot;
import com.whamu2.wanandroid.mvp.model.bean.BaseResp;
import com.whamu2.wanandroid.mvp.model.bean.WechatNumber;
import com.whamu2.wanandroid.utils.observer.Callback;
import com.whamu2.wanandroid.utils.observer.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

public class WechatPresenter extends BasePresenter<WechatCot.Model, WechatCot.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;
    @Inject
    Application mApplication;

    @Inject
    public WechatPresenter(WechatCot.Model model, WechatCot.View rootView) {
        super(model, rootView);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
    }

    public void getNumberList() {
        mModel.getNumberList()
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new CommonSubscriber<>(mErrorHandler, new Callback<BaseResp<List<WechatNumber>>>() {
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
                    public void onNext(BaseResp<List<WechatNumber>> resp) {
                        if (resp.isSuccess()) {

                            mRootView.onNumberDone(resp.getData());
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
