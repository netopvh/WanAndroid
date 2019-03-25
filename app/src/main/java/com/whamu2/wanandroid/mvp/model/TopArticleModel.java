package com.whamu2.wanandroid.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.whamu2.wanandroid.mvp.contract.TopArticleContract;
import com.whamu2.wanandroid.mvp.model.api.service.ApiService;
import com.whamu2.wanandroid.mvp.model.bean.Articles;
import com.whamu2.wanandroid.mvp.model.bean.BannerBean;
import com.whamu2.wanandroid.mvp.model.bean.BaseResp;
import com.whamu2.wanandroid.mvp.model.bean.Pagination;
import com.whamu2.wanandroid.utils.Transformer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author whamu2
 * @date 2018/7/3
 */
@ActivityScope
public class TopArticleModel extends BaseModel implements TopArticleContract.Model {

    @Inject
    public TopArticleModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResp<List<Articles>>> getTopArticles() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getTopArticles()
                .compose(Transformer.cutoverSchedulers());
    }

    @Override
    public Observable<BaseResp<List<BannerBean>>> getBanner(boolean update) {
        return mRepositoryManager
                .obtainRetrofitService(ApiService.class)
                .getBanner()
                .compose(Transformer.cutoverSchedulers());
    }

    @Override
    public Observable<BaseResp<Pagination>> getHotArticles(int page) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getHotArticles(page)
                .compose(Transformer.cutoverSchedulers());
    }
}
