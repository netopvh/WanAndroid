package com.whamu2.wanandroid.di.model;

import com.jess.arms.di.scope.ActivityScope;
import com.whamu2.wanandroid.mvp.contract.WechatCot;
import com.whamu2.wanandroid.mvp.model.WechatModel;

import dagger.Module;
import dagger.Provides;

/**
 * @author whamu2
 * @date 2018/6/26
 */

@Module
public class WechatModule {
    private WechatCot.View view;

    public WechatModule(WechatCot.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    WechatCot.View provideTreeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    WechatCot.Model provideTreeModel(WechatModel model) {
        return model;
    }

}
