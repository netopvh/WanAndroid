package com.whamu2.wanandroid.mvp.model;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.whamu2.wanandroid.mvp.contract.UserContract;
import com.whamu2.wanandroid.mvp.model.api.service.ApiService;
import com.whamu2.wanandroid.mvp.model.bean.BaseResp;
import com.whamu2.wanandroid.mvp.model.bean.User;
import com.whamu2.wanandroid.utils.Transformer;

import javax.inject.Inject;

import io.reactivex.Observable;
import timber.log.Timber;


@ActivityScope
public class UserModel extends BaseModel implements UserContract.Model {

    @Inject
    public UserModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
        Timber.d("Release Resource");
    }


    @Override
    public Observable<BaseResp<User>> register(String username, String password, String repassword) {
        return mRepositoryManager
                .obtainRetrofitService(ApiService.class)
                .register(username, password, repassword)
                .compose(Transformer.cutoverSchedulers());
    }

    @Override
    public Observable<BaseResp<User>> login(String username, String password) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .login(username, password)
                .compose(Transformer.cutoverSchedulers());
    }
}
