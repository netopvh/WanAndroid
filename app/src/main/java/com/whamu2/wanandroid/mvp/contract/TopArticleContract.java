package com.whamu2.wanandroid.mvp.contract;

import android.support.annotation.IntRange;
import android.support.v4.app.Fragment;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.whamu2.wanandroid.mvp.model.bean.Articles;
import com.whamu2.wanandroid.mvp.model.bean.BannerBean;
import com.whamu2.wanandroid.mvp.model.bean.BaseResp;
import com.whamu2.wanandroid.mvp.model.bean.Pagination;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author whamu2
 * @date 2018/7/4
 */
public interface TopArticleContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        Fragment getFragment();

        void onArticleDone(List<Articles> articles, int curPage);

        void onBannerDone(List<BannerBean> banners);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
    interface Model extends IModel {

        Observable<BaseResp<List<Articles>>> getTopArticles();

        Observable<BaseResp<List<BannerBean>>> getBanner(boolean update);

        Observable<BaseResp<Pagination>> getHotArticles(@IntRange(from = 0) int page);
    }
}
