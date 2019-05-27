package com.whamu2.wanandroid.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.whamu2.wanandroid.mvp.contract.FavoritesCot;
import com.whamu2.wanandroid.mvp.model.api.service.ApiService;
import com.whamu2.wanandroid.mvp.model.bean.BaseResp;
import com.whamu2.wanandroid.mvp.model.bean.Pagination;
import com.whamu2.wanandroid.utils.Transformer;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author whamu2
 * @date 2018/7/3
 */

@ActivityScope
public class FavoritesModel extends BaseModel implements FavoritesCot.Model {

    @Inject
    public FavoritesModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResp<Pagination>> queryFavoritesList(int page) {
        return mRepositoryManager.obtainCacheService(ApiService.class)
                .getCollectList(page)
                .compose(Transformer.cutoverSchedulers());
    }
}
