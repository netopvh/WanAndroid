package com.whamu2.wanandroid.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.whamu2.wanandroid.mvp.contract.TreeContract;
import com.whamu2.wanandroid.mvp.model.api.service.ApiService;
import com.whamu2.wanandroid.mvp.model.bean.BaseResp;
import com.whamu2.wanandroid.mvp.model.bean.Cycle;
import com.whamu2.wanandroid.utils.Transformer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author whamu2
 * @date 2018/6/26
 */

@ActivityScope
public class TreeModel extends BaseModel implements TreeContract.Model {

    @Inject
    public TreeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResp<List<Cycle>>> getTree(boolean update) {
        return mRepositoryManager
                .obtainRetrofitService(ApiService.class)
                .getTreeList()
                .compose(Transformer.cutoverSchedulers());
    }
}
