package com.whamu2.wanandroid.mvp.contract;

import android.support.v4.app.Fragment;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.whamu2.wanandroid.mvp.model.bean.Articles;
import com.whamu2.wanandroid.mvp.model.bean.BaseResp;
import com.whamu2.wanandroid.mvp.model.bean.Pagination;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author whamu2
 * @date 2018/7/3
 */
public interface SubclassContract {

    interface View extends IView {
        Fragment getFragment();

        void loadSuccess(List<Articles> articles, int curPage);
    }

    interface Model extends IModel {
        Observable<BaseResp<Pagination>> queryListById(int cid, int page);
    }
}
