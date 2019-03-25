package com.whamu2.wanandroid.mvp.presenter;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.mvp.contract.WechatChildCot;
import com.whamu2.wanandroid.mvp.model.bean.Articles;
import com.whamu2.wanandroid.mvp.model.bean.BaseResp;
import com.whamu2.wanandroid.mvp.model.bean.Pagination;
import com.whamu2.wanandroid.utils.observer.Callback;
import com.whamu2.wanandroid.utils.observer.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * @author whamu2
 * @date 2018/7/3
 */

@ActivityScope
public class WechatChildPresenter extends BasePresenter<WechatChildCot.Model, WechatChildCot.View> {

    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public WechatChildPresenter(WechatChildCot.Model model, WechatChildCot.View rootView) {
        super(model, rootView);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {

    }

    public void getListById(int id, int curPage) {
        mModel.queryListById(id, curPage)
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new CommonSubscriber<>(mErrorHandler, new Callback<BaseResp<Pagination>>() {
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
                    public void onNext(BaseResp<Pagination> resp) {
                        if (resp.isSuccess()) {
                            Pagination pagination = resp.getData();
                            List<Articles> datas = pagination.getDatas();
                            if (datas.size() > 0) {
                                mRootView.onDataDone(datas, pagination.getCurPage());
                            } else {
                                if (pagination.isOver()) {
                                    mRootView.showMessage(mRootView.getContext().getString(R.string.str_not_more_data));
                                }
                            }

                        } else {
                            mRootView.showMessage(resp.getErrorMsg());
                        }
                    }
                }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mErrorHandler = null;
    }
}
