package com.whamu2.wanandroid.mvp.presenter;

import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.mvp.contract.FavoritesCot;
import com.whamu2.wanandroid.mvp.model.bean.Articles;
import com.whamu2.wanandroid.mvp.model.bean.BaseResp;
import com.whamu2.wanandroid.mvp.model.bean.Pagination;
import com.whamu2.wanandroid.utils.observer.Callback;
import com.whamu2.wanandroid.utils.observer.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * @author suming
 * @date 15:32 2019 May Mon
 * @des https://github.com/whamu2
 */
public class FavoritesPresenter extends BasePresenter<FavoritesCot.Model, FavoritesCot.View> {

    @Inject
    RxErrorHandler mErrorHandler;

    public FavoritesPresenter(FavoritesCot.Model model, FavoritesCot.View rootView) {
        super(model, rootView);
    }

    public void getData(int page) {
        mModel.queryFavoritesList(page)
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
                            Pagination page = resp.getData();
                            List<Articles> data = page.getDatas();
                            if (data.size() > 0) {
                                mRootView.onDataDone(data, page.getCurPage());
                            } else {
                                if (page.isOver()) {
                                    mRootView.showMessage(mRootView.getActivity().getString(R.string.str_not_more_data));
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
