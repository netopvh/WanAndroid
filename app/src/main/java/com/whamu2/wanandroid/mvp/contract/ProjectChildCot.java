package com.whamu2.wanandroid.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.whamu2.wanandroid.mvp.model.bean.Articles;
import com.whamu2.wanandroid.mvp.model.bean.BaseResp;
import com.whamu2.wanandroid.mvp.model.bean.Pagination;

import java.util.List;

import io.reactivex.Observable;


public interface ProjectChildCot {

    interface View extends IView {
        Context getContext();

        void onDataDone(List<Articles> articles, int curPage);
    }

    interface Model extends IModel {
        Observable<BaseResp<Pagination>> queryListById(int cid, int page);
    }
}
