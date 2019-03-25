package com.whamu2.wanandroid.mvp.presenter;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.IntRange;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.whamu2.wanandroid.App;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.mvp.contract.TopArticleContract;
import com.whamu2.wanandroid.mvp.model.bean.Articles;
import com.whamu2.wanandroid.mvp.model.bean.BannerBean;
import com.whamu2.wanandroid.mvp.model.bean.BaseResp;
import com.whamu2.wanandroid.mvp.model.bean.Pagination;
import com.whamu2.wanandroid.utils.Transformer;
import com.whamu2.wanandroid.utils.observer.Callback;
import com.whamu2.wanandroid.utils.observer.CommonSubscriber;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.BiFunction;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @author whamu2
 * @date 2018/7/3
 */

@ActivityScope
public class TopArticlePresenter extends BasePresenter<TopArticleContract.Model, TopArticleContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public TopArticlePresenter(TopArticleContract.Model model, TopArticleContract.View rootView) {
        super(model, rootView);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {

    }

    /**
     * 轮播图
     *
     * @param update 是否主动去请求网络
     */
    public void getBanner(boolean update) {
        mModel.getBanner(update)
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResp<List<BannerBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResp<List<BannerBean>> resp) {
                        if (resp.isSuccess()) {
                            List<BannerBean> data = resp.getData();
                            mRootView.onBannerDone(data);

                        } else {
                            mRootView.showMessage(resp.getErrorMsg());
                        }
                    }
                });
    }

    public void getData(@IntRange(from = 0) int curPage) {
        Observable
                .zip(mModel.getTopArticles(), mModel.getHotArticles(curPage),
                        (listBaseResp, paginationBaseResp) -> {
                            if (curPage == 0) {
                                paginationBaseResp.getData().getDatas().addAll(0, listBaseResp.getData());
                            }
                            return paginationBaseResp;
                        })
                .compose(Transformer.cutoverSchedulers())
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
                                mRootView.onArticleDone(datas, pagination.getCurPage());
                            } else {
                                if (pagination.isOver()) {
                                    mRootView.showMessage(App.getApplication().getString(R.string.str_not_more_data));
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
