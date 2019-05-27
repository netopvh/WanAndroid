package com.whamu2.wanandroid.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.whamu2.wanandroid.mvp.model.bean.Articles;
import com.whamu2.wanandroid.mvp.model.bean.BaseResp;
import com.whamu2.wanandroid.mvp.model.bean.Pagination;

import java.util.List;

import io.reactivex.Observable;


public interface FavoritesCot {

    interface View extends IView {
        Activity getActivity();

        void onDataDone(List<Articles> data, int curPage);
    }

    interface Model extends IModel {
        Observable<BaseResp<Pagination>> queryFavoritesList(int page);
    }
}
