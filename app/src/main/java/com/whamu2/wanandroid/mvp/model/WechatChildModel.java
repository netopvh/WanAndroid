package com.whamu2.wanandroid.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.whamu2.wanandroid.mvp.contract.WechatChildCot;
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
public class WechatChildModel extends BaseModel implements WechatChildCot.Model {

    @Inject
    public WechatChildModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResp<Pagination>> queryListById(int id, int page) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getNumberPageList(id, page)
                .compose(Transformer.cutoverSchedulers());
    }

}
