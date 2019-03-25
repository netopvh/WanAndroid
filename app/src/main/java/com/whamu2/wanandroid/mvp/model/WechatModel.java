package com.whamu2.wanandroid.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.whamu2.wanandroid.mvp.contract.WechatCot;
import com.whamu2.wanandroid.mvp.model.api.service.ApiService;
import com.whamu2.wanandroid.mvp.model.bean.BaseResp;
import com.whamu2.wanandroid.mvp.model.bean.WechatNumber;
import com.whamu2.wanandroid.utils.Transformer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class WechatModel extends BaseModel implements WechatCot.Model {

    @Inject
    public WechatModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Observable<BaseResp<List<WechatNumber>>> getNumberList() {
        return mRepositoryManager
                .obtainRetrofitService(ApiService.class)
                .getNumberList()
                .compose(Transformer.cutoverSchedulers());
    }
}
